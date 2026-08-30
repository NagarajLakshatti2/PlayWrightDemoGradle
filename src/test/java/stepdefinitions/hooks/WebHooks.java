package stepdefinitions.hooks;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.springframework.beans.factory.annotation.Autowired;
import config.ConfigReader;
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
        // No PlaywrightManager.closeBrowser() — Spring's cucumber-glue scope
        // automatically disposes Page/Context/Browser/Playwright beans when
        // this scenario's context is destroyed.
    }

    private String sanitizeFileName(String value) {
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
