package stepdefinitions;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import config.ConfigReader;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.PlaywrightManager;
import utils.TestContext;

import java.io.IOException;
import java.util.Base64;


public class Hooks {
    private static boolean bannerLogged = false;
    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);

    @Before
    public void setUp(Scenario scenario) {
        TestContext.setScenarioName(scenario.getName());

        String envLine = "Environment: " + ConfigReader.currentEnv().toUpperCase()
                + " | Browser: " + ConfigReader.get("browser", "chromium").toUpperCase()
                + " | Base URL: " + ConfigReader.baseUrl()
                + " | API URL: " + ConfigReader.apiBaseUrl();

        // Console/file log — once per run is enough here
        if (!bannerLogged) {
            log.info("=== Running against environment: {} | browser={} | base.url={} | api.base.url={} ===",
                    ConfigReader.currentEnv().toUpperCase(),
                    ConfigReader.get("browser", "chromium").toUpperCase(),
                    ConfigReader.baseUrl(),
                    ConfigReader.apiBaseUrl());
            bannerLogged = true;
        }

        // Extent report — log it every scenario so each one shows its own environment context
        ExtentCucumberAdapter.addTestStepLog(envLine);

        PlaywrightManager.initBrowser();
    }

    @After
    public void tearDown(Scenario scenario) throws IOException {
        byte[] screenshot = PlaywrightManager.getPage().screenshot();
        scenario.attach(screenshot, "image/png", scenario.getName());

        ExtentCucumberAdapter.addTestStepScreenCaptureFromPath(
                "data:image/png;base64," + Base64.getEncoder().encodeToString(screenshot),
                scenario.getName()
        );

        TestContext.clear();
        PlaywrightManager.closeBrowser();
    }
}