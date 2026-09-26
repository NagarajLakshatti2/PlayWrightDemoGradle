package stepdefinitions.qapracticehub;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitUntilState;
import io.cucumber.java.en.Given;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import web.pages.qapracticehub.QAPracticeHubHomePage;

public class NavigationSteps {

    private static final Logger log = LoggerFactory.getLogger(NavigationSteps.class);

    @Autowired
    private Page page;

    private QAPracticeHubHomePage qaPracticeHubHomePage;

    @Given("the user is on the qapracticehub homepage")
    public void the_user_is_on_the_qapracticehub_homepage() {
        log.info("Navigating to qapracticehub homepage: https://qapracticehub.com/");
        page.navigate("https://qapracticehub.com/",
                new Page.NavigateOptions().setWaitUntil(WaitUntilState.DOMCONTENTLOADED));
        qaPracticeHubHomePage = new QAPracticeHubHomePage(page);
    }

    @Given("the user navigates to the Inputs section")
    public void the_user_navigates_to_the_inputs_section() {
        log.info("Navigating to Inputs section");
        qaPracticeHubHomePage.navigateToInputsSection();
    }
}