package stepdefinitions;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import config.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import web.pages.LoginPage;
import utils.PlaywrightManager;
import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;

public class LoginSteps {

    private static final Logger log = LoggerFactory.getLogger(LoginSteps.class);
    private LoginPage loginPage;

    @Given("the user is on the login page")
    public void the_user_is_on_the_login_page() {
        log.info("Navigating to login page: {}", ConfigReader.baseUrl());
//        PlaywrightManager.getPage().navigate(ConfigReader.baseUrl());
        PlaywrightManager.getPage().navigate(
                ConfigReader.baseUrl(),
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED)
        );
        loginPage = new LoginPage(PlaywrightManager.getPage());
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
//        Thread.sleep(10000);
    }

    @Then("the login should fail")
    public void the_login_should_fail() throws InterruptedException {
        Assert.assertTrue(loginPage.hasErrorMessage(), "Expected an error message on failed login");
//        int i = 10;
//        int k = i/0;
    }
}
