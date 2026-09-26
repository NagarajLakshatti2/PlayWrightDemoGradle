Feature: Text Input Module

  Scenario: Verify Text Input Field Functionality
    Given the user is on the qapracticehub homepage
    And the user navigates to the Inputs section
    When the user enters "Hello World" in the text input field
    Then the text should be entered correctly
    When the user clears the text field
    Then the field should be empty

  Scenario: Verify Email Input Validation
    Given the user is on the qapracticehub homepage
    And the user navigates to the Inputs section
    When the user enters "test@example.com" in the email input field
    Then the email should be accepted
    When the user enters "invalid-email" in the email input field
    Then validation error should be shown
