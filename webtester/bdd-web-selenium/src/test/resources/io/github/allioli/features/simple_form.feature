@forms
Feature: User interacts with a simple form

  Background:
    Given I have opened form page in sandbox
    And   I have dismissed Cookies dialog
    And   I am in "Simple Form Demo" page

  Scenario: Enter text using a form
    When  I verify "Simple Form Demo" page
    And   I enter "bla bla" to the message form
    And   I click on Get checked value button
    Then  I should see the entered message as "bla bla"

  Scenario Outline: Add two numbers using a form
    When  I enter <number1> and <number2> to the sum up form
    And   I click on Sum button
    Then  I should see the result of the sum as <result>
    Examples:
    | number1 | number2  | result                             |
    | 4       |   5      |    9                               |
    | 0       |   2      |    2                               |
    | 2       |   -2     |    0                               |
