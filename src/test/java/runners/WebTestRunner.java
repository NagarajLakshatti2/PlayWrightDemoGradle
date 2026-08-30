package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features/web",
        glue = {"stepdefinitions.web", "stepdefinitions.hooks", "spring"},

        plugin = {
        "pretty",
        "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)

public class WebTestRunner extends AbstractTestNGCucumberTests {
}
