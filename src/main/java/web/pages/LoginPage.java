package web.pages;

import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class LoginPage extends BasePage{

    private static final String USERNAME_INPUT = "#user-name";
    private static final String PASSWORD_INPUT = "#password";
    private static final String SUBMIT_BUTTON = "#login-button";
    private static final String ERROR_BANNER = "[data-test='error']";
    private static final String LOGIN_BUTTON_FALLBACK = "input[type='submit']";

    @Autowired
    public LoginPage(Page page) { super(page); }

    public void loginAs(String username, String password) {
        fill(USERNAME_INPUT, username);
        fill(PASSWORD_INPUT, password);
        click(SUBMIT_BUTTON, LOGIN_BUTTON_FALLBACK);
    }

    public boolean hasErrorMessage() { return isVisible(ERROR_BANNER); }

    public boolean isLoggedIn() {
        return currentUrl().contains("inventory.html");
    }
}
