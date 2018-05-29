@search
Feature: Unregistered user item search
  As a non registered user of letgo
  I want to search for an item
  So that I can buy it or choose a fair price for an item I want to sell

  Background:
    Given I am on the letgo landing page

  Scenario Outline: Valid search query
    When I search for <query>
    Then I should see at least one "Monkey" item
    And  I should see at least one "Mug" item
    And  I should see at least one "Yellow" item

    Examples:
      | query                            |
      |  "yellow monkey mug"             |
      |  "YelloW MoNKey Mug"             |
      |  "  yellow     monkey    mug "   |


  Scenario: Empty search query
    When I search for "  "
    Then I should see an empty search box


  Scenario Outline: Valid search query returning no items
    When I search for <query>
    Then I should see no results
    And I should see no results found message


    Examples:
      | query            |
      | "[{].;*&#~~~~++" |
      | "krtek"          |
      | "987'3232"       |


  Scenario: Valid number search query
    When I search for "300"
    Then I should see at least one "300" item


  Scenario: Clean search query
    When I search for "geonaute"
    Then I should see at least one "Geonaute" item
    When I delete search query
    Then I should not see a "Geonaute" item


  @unstable
  Scenario: Search by price range
    When I select price range between 10 and 20
    And I sort by price higher to lower
    And I open the first item page
    Then The first item price is "$20"
    When I sort by price lower to higher
    Then The first item price is "$10"

  Scenario Outline: Search long query
    When I search for <query>
    Then I should see at least one "Octopus" item

    Examples:
      | query                                                                           |
      | "Wwerwer wREAVASDFwervsdf octopus alfmworijwelskmfdqpw abcdefghijklmnopqrsX"    |
      | " octopus UNION ALL SELECT * FROM users"                                        |


