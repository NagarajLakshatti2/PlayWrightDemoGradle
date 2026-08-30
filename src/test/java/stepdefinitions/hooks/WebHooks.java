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

public class WebHooks {

    @Autowired
    private Page page;

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

        TestContext.clear();
        // No PlaywrightManager.closeBrowser() — Spring's cucumber-glue scope
        // automatically disposes Page/Context/Browser/Playwright beans when
        // this scenario's context is destroyed.
    }
}
