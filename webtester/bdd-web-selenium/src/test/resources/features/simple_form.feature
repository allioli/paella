@forms
Feature: User interacts with a simple form

  Background:
    Given I have opened form page in sandbox
    And   I have dismissed Cookies dialog

  Scenario: Enter info and submit form
    When  I enter two numbers to the sum up form
    And   I click on Sum button
    Then  I should see the result of the sum