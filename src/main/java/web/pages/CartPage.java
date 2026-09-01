package web.pages;

import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("cucumber-glue")
public class CartPage extends BasePage {

    private static final String CHECKOUT_BUTTON = "#checkout";

    @Autowired
    public CartPage(Page page) {
        super(page);
    }

    public void checkout() {
        click(CHECKOUT_BUTTON);
    }

    public boolean isCartPage() {
        return page.url().contains("cart.html");
    }
}
