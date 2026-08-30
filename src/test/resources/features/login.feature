Feature: Login

  Scenario Outline: User attempts to log in
    Given the user is on the login page
    When they log in with "<username>" and "<password>"
    Then the login should <result>

    Examples:
      | username        | password      | result  |
      | standard_user   | secret_sauce  | succeed |
      | invalid_user    | wrong_pass    | fail    |