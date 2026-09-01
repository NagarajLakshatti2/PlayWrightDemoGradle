package web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import java.util.Arrays;
import java.util.List;

public class BasePage {
    protected final Page page;

    protected BasePage(Page page) { this.page = page; }

    protected Locator resilientLocator(String... selectors) {
        List<String> candidates = Arrays.asList(selectors);
        for (String selector : candidates) {
            if (page.locator(selector).count() > 0) {
                return page.locator(selector);
            }
        }
        return page.locator(candidates.get(0));
    }

    protected void click(String... selectors) {
        Locator locator = resilientLocator(selectors);
        locator.click(new Locator.ClickOptions().setTimeout(10000));
    }

    protected void fill(String selector, String text) {
        resilientLocator(selector).fill(text);
    }

    protected String textOf(String selector) { return resilientLocator(selector).innerText(); }
    protected boolean isVisible(String selector) { return resilientLocator(selector).isVisible(); }

    protected void waitForVisible(String selector) {
        resilientLocator(selector).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
    }

    public String currentUrl() { return page.url(); }
}
