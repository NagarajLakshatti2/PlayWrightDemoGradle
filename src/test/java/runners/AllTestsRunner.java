package runners;


import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinitions.web", "stepdefinitions.hooks", "spring"},

        plugin = {
                "pretty",
                "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"
        },
        monochrome = true
)
public class AllTestsRunner extends AbstractTestNGCucumberTests {

        @Override
//        @DataProvider(parallel = true)
        public Object[][] scenarios() {
                return super.scenarios();
        }
}
