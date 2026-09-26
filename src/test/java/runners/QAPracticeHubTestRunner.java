package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(features = "src/test/resources/features/qapracticehub", glue = {
        "stepdefinitions.qapracticehub", "stepdefinitions.hooks", "stepdefinitions.web", "spring" },

        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        }, monochrome = true)

public class QAPracticeHubTestRunner extends AbstractTestNGCucumberTests {
}