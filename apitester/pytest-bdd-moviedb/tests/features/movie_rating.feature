Feature: MovieDB API Movie rating
  Movie ratings for the people.

  Scenario: Rate The GodFather
    Given I have logged in as a guest moviedb user
    When  I rate The GodFather with value "8.5"
    Then  A new record should appear in My Rated Movies with rating "8.5"

  Scenario: List My Rated Movies as guest user
    Given I have logged in as a guest moviedb user 
    When  I list My Rated Movies as guest user
    Then  Response status code is "401"
    And   Response message is "Authentication failed: You do not have permissions to access the service."   