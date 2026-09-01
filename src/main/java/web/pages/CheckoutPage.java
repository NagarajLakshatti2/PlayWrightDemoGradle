package web.pages;

import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class CheckoutPage extends BasePage {

    private static final String FIRST_NAME_INPUT = "#first-name";
    private static final String LAST_NAME_INPUT = "#last-name";
    private static final String ZIP_INPUT = "#postal-code";
    private static final String CONTINUE_BUTTON = "#continue";
    private static final String FINISH_BUTTON = "#finish";
    private static final String COMPLETION_HEADER = ".complete-header";

    @Autowired
    public CheckoutPage(Page page) {
        super(page);
    }

    public void enterInformation(String firstName, String lastName, String zipCode) {
        fill(FIRST_NAME_INPUT, firstName);
        fill(LAST_NAME_INPUT, lastName);
        fill(ZIP_INPUT, zipCode);
        click(CONTINUE_BUTTON);
    }

    public void finishOrder() {
        click(FINISH_BUTTON);
    }

    public boolean isOrderComplete() {
        return page.locator(COMPLETION_HEADER).isVisible();
    }
}
