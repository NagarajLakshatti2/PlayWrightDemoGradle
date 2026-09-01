package web.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class InventoryPage extends BasePage {

    private static final String INVENTORY_LIST = ".inventory_list";
    private static final String CART_BUTTON = ".shopping_cart_link";
    private static final String CART_BUTTON_FALLBACK = "a.shopping_cart_link";

    @Autowired
    public InventoryPage(Page page) {
        super(page);
    }

    public void addItemToCart(String itemName) {
        page.locator(".inventory_item")
                .filter(new Locator.FilterOptions().setHasText(itemName))
                .locator("button")
                .click();
    }

    public void goToCart() {
        click(CART_BUTTON, CART_BUTTON_FALLBACK);
    }

    public boolean isInventoryPage() {
        return page.url().contains("inventory.html");
    }

    public boolean isVisible() {
        return page.locator(INVENTORY_LIST).isVisible();
    }
}
