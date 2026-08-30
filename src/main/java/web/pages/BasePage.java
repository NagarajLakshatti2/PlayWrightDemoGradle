package web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;

public class BasePage {
    protected final Page page;

    protected BasePage(Page page) { this.page = page; }

    protected void click(String selector) {
//        page.locator(selector).click();
        page.locator(selector).click(
                new Locator.ClickOptions().setTimeout(10000)
        );
    }
    protected void fill(String selector, String text) { page.locator(selector).fill(text); }
    protected String textOf(String selector) { return page.locator(selector).innerText(); }
    protected boolean isVisible(String selector) { return page.locator(selector).isVisible(); }
    protected void waitForVisible(String selector) {
        page.locator(selector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }
    public String currentUrl() { return page.url(); }
}
