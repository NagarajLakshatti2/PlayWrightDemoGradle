package web.pages;

import com.microsoft.playwright.Page;

public class LoginPage extends BasePage{

    private static final String USERNAME_INPUT = "#user-name";
    private static final String PASSWORD_INPUT = "#password";
    private static final String SUBMIT_BUTTON = "#login-button";
    private static final String ERROR_BANNER = "[data-test='error']";

    public LoginPage(Page page) { super(page); }

    public void loginAs(String username, String password) {
        fill(USERNAME_INPUT, username);
        fill(PASSWORD_INPUT, password);
        click(SUBMIT_BUTTON);
    }

    public boolean hasErrorMessage() { return isVisible(ERROR_BANNER); }

    public boolean isLoggedIn() {
        return currentUrl().contains("inventory.html");
    }
}
