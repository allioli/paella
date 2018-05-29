@account
Feature: User Account creation
  As a non registered user of letgo
  I want to create an user account
  So that I can sell stuff

  @manual
  Scenario: Account creation with email
    Given I am on the letgo landing page
    When I navigate to the account creation form
    And I enter email and password
    And I enter user full name "Test Tester"
    And I demonstrate I am not a robot
    Then I am logged in as a registered user with avatar icon "T"
    When I log out
    And I navigate to the login form
    And I enter email and password
    Then I am logged in as a registered user with avatar icon "T"


