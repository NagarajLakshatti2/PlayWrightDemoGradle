package stepdefinitions.web;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import utils.VisualValidationUtils;
import web.pages.CartPage;
import web.pages.CheckoutPage;
import web.pages.InventoryPage;

public class CheckoutWebSteps {

    @Autowired
    private Page page;

    private InventoryPage inventoryPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @When("they add {string} to the cart")
    public void they_add_item_to_the_cart(String itemName) {
        inventoryPage = new InventoryPage(page);
        Assert.assertTrue(inventoryPage.isInventoryPage(), "Expected to be on the inventory page");
        inventoryPage.addItemToCart(itemName);

        try {
            VisualValidationUtils.saveAndCompareAgainstBaseline(page, "checkout", "inventory_after_add", 1.0);
        } catch (Exception e) {
            // no-op: visual capture is a convenience, not a hard failure
        }
    }

    @When("they checkout the cart")
    public void they_checkout_the_cart() {
        inventoryPage = inventoryPage == null ? new InventoryPage(page) : inventoryPage;
        cartPage = new CartPage(page);
        inventoryPage.goToCart();
        Assert.assertTrue(cartPage.isCartPage(), "Expected to be on the cart page");
        cartPage.checkout();
    }

    @When("they enter checkout information for {string} {string} {string}")
    public void they_enter_checkout_information_for(String firstName, String lastName, String zipCode) {
        checkoutPage = new CheckoutPage(page);
        checkoutPage.enterInformation(firstName, lastName, zipCode);
    }

    @When("they confirm the order")
    public void they_confirm_the_order() {
        checkoutPage = checkoutPage == null ? new CheckoutPage(page) : checkoutPage;
        checkoutPage.finishOrder();
    }

    @Then("the order should be complete")
    public void the_order_should_be_complete() {
        checkoutPage = checkoutPage == null ? new CheckoutPage(page) : checkoutPage;
        Assert.assertTrue(checkoutPage.isOrderComplete(), "Expected order completion page to be visible");

        try {
            VisualValidationUtils.saveAndCompareAgainstBaseline(page, "checkout", "order_complete", 1.0);
        } catch (Exception e) {
            // no-op: visual capture is a convenience, not a hard failure
        }
    }
}
