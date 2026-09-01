Feature: User login

  Scenario: Successful login with valid credentials
    Given the user is on the login page
    When they log in with "standard_user" and "secret_sauce"
    Then the login should succeed

  Scenario: Login fails for locked-out user
    Given the user is on the login page
    When they log in with "locked_out_user" and "secret_sauce"
    Then the login should fail

  Scenario: Login fails for invalid credentials
    Given the user is on the login page
    When they log in with "invalid_user" and "wrong_password"
    Then the login should fail
