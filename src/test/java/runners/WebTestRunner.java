package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

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

    @Override
//    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}
