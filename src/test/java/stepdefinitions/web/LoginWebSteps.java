package stepdefinitions.web;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import org.springframework.beans.factory.annotation.Autowired;
import config.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import utils.VisualValidationUtils;
import web.pages.LoginPage;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class LoginWebSteps {

    private static final Logger log = LoggerFactory.getLogger(LoginWebSteps.class);

    @Autowired
    private Page page;

    private LoginPage loginPage;

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        log.info("Navigating to login page: {}", ConfigReader.baseUrl());
        page.navigate(ConfigReader.baseUrl(),
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
        );
        loginPage = new LoginPage(page);

        try {
            VisualValidationUtils.saveAndCompareAgainstBaseline(page, "login", "landing", 1.0);
        } catch (Exception e) {
            log.warn("Failed to save/compare landing screenshot: {}", e.getMessage());
        }
    }

    @When("they log in with {string} and {string}")
    public void they_log_in_with(String username, String password) {
        log.info("Attempting login with username: {}", username);
        ExtentCucumberAdapter.addTestStepLog("Attempting login with username: " + username);

        loginPage.loginAs(username, password);

    }

    @Then("the login should succeed")
    public void the_login_should_succeed() throws InterruptedException {
        Assert.assertTrue(loginPage.isLoggedIn(), "Expected login to succeed");

        try {
            VisualValidationUtils.saveAndCompareAgainstBaseline(page, "login", "success", 1.0);
        } catch (Exception e) {
            log.warn("Failed to save/compare success screenshot: {}", e.getMessage());
        }
    }

    @Then("the login should fail")
    public void the_login_should_fail() throws InterruptedException {
        Assert.assertTrue(loginPage.hasErrorMessage(), "Expected an error message on failed login");
//        Assert.assertTrue(false, "Force AI summary generation");

        try {
            VisualValidationUtils.saveAndCompareAgainstBaseline(page, "login", "failure", 1.0);
        } catch (Exception e) {
            log.warn("Failed to save/compare failure screenshot: {}", e.getMessage());
        }
    }
}
