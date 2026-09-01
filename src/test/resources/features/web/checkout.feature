Feature: Checkout flow

  Scenario: Place an order with a single product
    Given the user is on the login page
    And they log in with "standard_user" and "secret_sauce"
    When they add "Sauce Labs Backpack" to the cart
    And they checkout the cart
    And they enter checkout information for "John" "Doe" "12345"
    And they confirm the order
    Then the order should be complete
