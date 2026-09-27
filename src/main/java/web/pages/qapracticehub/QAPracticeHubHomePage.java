package web.pages.qapracticehub;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import web.pages.BasePage;

@Component
@Scope("cucumber-glue")
public class QAPracticeHubHomePage extends BasePage {

    // Selectors for navigation
    private static final String INPUTS_SECTION_LINK = "a:has-text('Inputs'), nav a:has-text('Inputs'), .nav-link:has-text('Inputs')";
    private static final String INPUTS_SECTION_FALLBACK = "button:has-text('Inputs'), .menu-item:has-text('Inputs')";

    // Selectors for text input
    private static final String TEXT_INPUT_FIELD = "[data-testid='text-input']";
    private static final String TEXT_INPUT_FALLBACK = "input[type='text'][id='text-input'], input[name='textInput']";

    // Selectors for email input
    private static final String EMAIL_INPUT_FIELD = "[data-testid='email-input']";
    private static final String EMAIL_INPUT_FALLBACK = "input[type='email'][id='email-input'], input[name='email']";

    // Selectors for validation
    private static final String VALIDATION_ERROR = "[data-testid='email-error'], .error-message, .validation-error";
    private static final String VALIDATION_ERROR_FALLBACK = "[class*='error']:visible, [class*='invalid']:visible";

    @Autowired
    public QAPracticeHubHomePage(Page page) {
        super(page);
    }

    public void navigateToInputsSection() {
        click(INPUTS_SECTION_LINK, INPUTS_SECTION_FALLBACK);
    }

    public void enterTextInput(String text) {
        fill(new String[] { TEXT_INPUT_FIELD, TEXT_INPUT_FALLBACK }, text);
    }

    public String getTextInputValue() {
        return resilientLocator(TEXT_INPUT_FIELD, TEXT_INPUT_FALLBACK).inputValue();
    }

    public void clearTextInput() {
        resilientLocator(TEXT_INPUT_FIELD, TEXT_INPUT_FALLBACK).clear();
    }

    public void enterEmailInput(String email) {
        fill(new String[] { EMAIL_INPUT_FIELD, EMAIL_INPUT_FALLBACK }, email);
    }

    public String getEmailInputValue() {
        return resilientLocator(EMAIL_INPUT_FIELD, EMAIL_INPUT_FALLBACK).inputValue();
    }

    public boolean hasEmailValidationError() {
        return isVisible(VALIDATION_ERROR, VALIDATION_ERROR_FALLBACK);
    }

    public boolean isEmailInputInvalid() {
        // Check if the email input has validation attributes indicating invalid state
        try {
            Locator emailInput = resilientLocator(EMAIL_INPUT_FIELD, EMAIL_INPUT_FALLBACK);
            // Check for common validation indicators
            String ariaInvalid = emailInput.getAttribute("aria-invalid");
            String classAttr = emailInput.getAttribute("class");

            boolean isAriaInvalid = "true".equals(ariaInvalid);
            boolean hasErrorClass = classAttr != null && (classAttr.contains("error") || classAttr.contains("invalid"));

            return isAriaInvalid || hasErrorClass;
        } catch (Exception e) {
            return false;
        }
    }
}