
@account
Feature: User Account creation
  As a non registered user of letgo
  I want to create an user account
  So that I can sell stuff



  @manual
  Scenario: Account creation with email
    Given I open letgo home page
    When I create a user account with email and password
    Then I am signed in as a registered user
    When I sign out
    And I sign in with email and password
    Then I am signed in as a registered user
    

  