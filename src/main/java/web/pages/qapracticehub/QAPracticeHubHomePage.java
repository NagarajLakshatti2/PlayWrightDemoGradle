package web.pages.qapracticehub;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import utils.TestDataLoader;
import web.pages.BasePage;

import java.util.Map;

@Component
@Scope("cucumber-glue")
public class QAPracticeHubHomePage extends BasePage {

    private static final String TEST_DATA_FILE = "qapracticehub-test-data";
    private static Map<String, Object> testData;
    private static Map<String, Object> selectors;

    // Load test data and selectors from JSON
    static {
        testData = TestDataLoader.loadTestData(TEST_DATA_FILE);
        selectors = TestDataLoader.getMap(testData, "qapracticehub.selectors");
    }

    @Autowired
    public QAPracticeHubHomePage(Page page) {
        super(page);
    }

    public void navigateToInputsSection() {
        Map<String, Object> inputsSection = TestDataLoader.getMap(selectors, "navigation.inputsSection");
        String primary = TestDataLoader.getString(inputsSection, "primary");
        String fallback = TestDataLoader.getString(inputsSection, "fallback");
        click(primary, fallback);
    }

    public void enterTextInput(String text) {
        Map<String, Object> textInput = TestDataLoader.getMap(selectors, "textInput.field");
        String primary = TestDataLoader.getString(textInput, "primary");
        String fallback = TestDataLoader.getString(textInput, "fallback");
        fill(new String[] { primary, fallback }, text);
    }

    public String getTextInputValue() {
        Map<String, Object> textInput = TestDataLoader.getMap(selectors, "textInput.field");
        String primary = TestDataLoader.getString(textInput, "primary");
        String fallback = TestDataLoader.getString(textInput, "fallback");
        return resilientLocator(primary, fallback).inputValue();
    }

    public void clearTextInput() {
        Map<String, Object> textInput = TestDataLoader.getMap(selectors, "textInput.field");
        String primary = TestDataLoader.getString(textInput, "primary");
        String fallback = TestDataLoader.getString(textInput, "fallback");
        resilientLocator(primary, fallback).clear();
    }

    public void enterEmailInput(String email) {
        Map<String, Object> emailInput = TestDataLoader.getMap(selectors, "emailInput.field");
        String primary = TestDataLoader.getString(emailInput, "primary");
        String fallback = TestDataLoader.getString(emailInput, "fallback");
        fill(new String[] { primary, fallback }, email);
    }

    public String getEmailInputValue() {
        Map<String, Object> emailInput = TestDataLoader.getMap(selectors, "emailInput.field");
        String primary = TestDataLoader.getString(emailInput, "primary");
        String fallback = TestDataLoader.getString(emailInput, "fallback");
        return resilientLocator(primary, fallback).inputValue();
    }

    public boolean hasEmailValidationError() {
        Map<String, Object> validationError = TestDataLoader.getMap(selectors, "emailInput.validationError");
        String primary = TestDataLoader.getString(validationError, "primary");
        String fallback = TestDataLoader.getString(validationError, "fallback");
        return isVisible(primary, fallback);
    }

    public boolean isEmailInputInvalid() {
        // Check if the email input has validation attributes indicating invalid state
        try {
            Map<String, Object> emailInput = TestDataLoader.getMap(selectors, "emailInput.field");
            String primary = TestDataLoader.getString(emailInput, "primary");
            String fallback = TestDataLoader.getString(emailInput, "fallback");
            Locator emailInputLocator = resilientLocator(primary, fallback);
            // Check for common validation indicators
            String ariaInvalid = emailInputLocator.getAttribute("aria-invalid");
            String classAttr = emailInputLocator.getAttribute("class");

            boolean isAriaInvalid = "true".equals(ariaInvalid);
            boolean hasErrorClass = classAttr != null && (classAttr.contains("error") || classAttr.contains("invalid"));

            return isAriaInvalid || hasErrorClass;
        } catch (Exception e) {
            return false;
        }
    }
}