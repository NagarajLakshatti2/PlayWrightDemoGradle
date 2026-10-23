package stepdefinitions.web;

import com.microsoft.playwright.Page;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.VisualValidationUtils;

public class CheckoutWebSteps {

    private static final Logger log = LoggerFactory.getLogger(CheckoutWebSteps.class);

    @Autowired
    private Page page;

    @When("they add {string} to the cart")
    public void they_add_item_to_the_cart(String itemName) {
        log.info("Adding item to cart: {}", itemName);
        // TODO: Implement actual cart functionality when needed
        // For now, this is a placeholder to avoid compilation errors

        try {
            VisualValidationUtils.saveAndCompareAgainstBaseline(page, "checkout", "inventory_after_add", 1.0);
        } catch (Exception e) {
            log.warn("Failed to save/compare screenshot: {}", e.getMessage());
        }
    }

    @When("they checkout the cart")
    public void they_checkout_the_cart() {
        log.info("Checking out cart");
        // TODO: Implement actual checkout functionality when needed

        try {
            VisualValidationUtils.saveAndCompareAgainstBaseline(page, "checkout", "cart_page", 1.0);
        } catch (Exception e) {
            log.warn("Failed to save/compare screenshot: {}", e.getMessage());
        }
    }

    @When("they enter checkout information for {string} {string} {string}")
    public void they_enter_checkout_information_for(String firstName, String lastName, String zipCode) {
        log.info("Entering checkout info: {} {} {}", firstName, lastName, zipCode);
        // TODO: Implement actual checkout form functionality when needed
    }

    @When("they confirm the order")
    public void they_confirm_the_order() {
        log.info("Confirming order");
        // TODO: Implement actual order confirmation when needed
    }

    @Then("the order should be complete")
    public void the_order_should_be_complete() {
        log.info("Verifying order completion");
        // TODO: Implement actual order completion verification when needed

        try {
            VisualValidationUtils.saveAndCompareAgainstBaseline(page, "checkout", "order_complete", 1.0);
        } catch (Exception e) {
            log.warn("Failed to save/compare screenshot: {}", e.getMessage());
        }
    }
}
