package stepdefinitions.hooks;

import ai.LlmGateway;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import config.ConfigReader;
import utils.FailureTriageUtils;
import utils.KnowledgeIndexUtils;
import utils.QaSummaryGenerator;
import utils.TestContext;

import java.io.IOException;
import java.util.Base64;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WebHooks {

    @Autowired
    private Page page;

    @Autowired
    private com.microsoft.playwright.BrowserContext browserContext;

    @Before
    public void setUp(Scenario scenario) {
        TestContext.setScenarioName(scenario.getName());

        String envLine = "Environment: " + ConfigReader.currentEnv().toUpperCase()
                + " | Browser: " + ConfigReader.get("browser", "chromium").toUpperCase()
                + " | Base URL: " + ConfigReader.baseUrl();
        ExtentCucumberAdapter.addTestStepLog(envLine);

        // No PlaywrightManager.initBrowser() — Spring already built a fresh
        // Playwright/Browser/Context/Page graph for this scenario via SpringTestConfig.
    }

    @After
    public void tearDown(Scenario scenario) throws IOException {
        byte[] screenshot = page.screenshot();
        scenario.attach(screenshot, "image/png", scenario.getName());

        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(
                "data:image/png;base64," + Base64.getEncoder().encodeToString(screenshot),
                scenario.getName()
        );

        if (scenario.isFailed()) {
            String area = FailureTriageUtils.classifyArea(scenario.getName(), scenario.getStatus().toString());
            String cause = FailureTriageUtils.classifyCause(scenario.getName(), scenario.getStatus().toString());
            String action = FailureTriageUtils.suggestAction(area);
            String screenshotPath = "test-output/visual/" + sanitizeFileName(scenario.getName());

            String triageSummary = FailureTriageUtils.buildSummary(
                    scenario.getName(),
                    scenario.getStatus().toString(),
                    screenshotPath
            );
            FailureTriageUtils.saveSummary(scenario.getName(), triageSummary);

            String query = scenario.getName() + " " + area + " " + cause;
            String projectContext = KnowledgeIndexUtils.findRelevantContext(query, 1200);

            String llmSummary = "AI is disabled. No remote model call was made.";
            try {
                if (ConfigReader.getBoolean("ai.enabled", false)) {
                    llmSummary = new LlmGateway().summarizeFailure(scenario.getName(), triageSummary + "\n\nProject context:\n" + projectContext);
                }
            } catch (Exception e) {
                llmSummary = "AI summarization unavailable: " + e.getMessage();
            }

            QaSummaryGenerator.generate(
                    scenario.getName(),
                    area,
                    cause,
                    action,
                    screenshotPath,
                    projectContext + "\n\n### AI summary\n\n" + llmSummary
            );

            Path tracePath = Paths.get(
                    "test-output",
                    ConfigReader.currentEnv(),
                    ConfigReader.get("browser", "chromium"),
                    "traces",
                    sanitizeFileName(scenario.getName()) + "-line-" + scenario.getLine() + ".zip"
            );
            Files.createDirectories(tracePath.getParent());
            browserContext.tracing().stop(
                    new com.microsoft.playwright.Tracing.StopOptions().setPath(tracePath)
            );
        } else {
            browserContext.tracing().stop();
        }

        TestContext.clear();
    }

    private String sanitizeFileName(String value) {
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
