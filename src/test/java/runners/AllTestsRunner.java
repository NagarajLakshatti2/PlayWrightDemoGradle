package runners;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions.web", "stepdefinitions.hooks", "spring"},

        plugin = {
                "pretty",

                // Extent Report
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",

                // Custom live ReportPortal Cucumber reporter
                "listeners.ReportPortalCucumberReporter"
        },
        monochrome = true
)
public class AllTestsRunner extends AbstractTestNGCucumberTests {
}
