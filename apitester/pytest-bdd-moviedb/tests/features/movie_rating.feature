@movie_rating
Feature: MovieDB API Movie rating
  Movie ratings for the people.

  @smoke
  Scenario: Rate The GodFather
    Given I have logged in as a guest moviedb user
    When  I rate The GodFather with value "8.5"
    Then  A new record should appear in My Rated Movies with rating "8.5"

  Scenario: List My Rated Movies as guest user
    Given I have logged in as a guest moviedb user 
    When  I try to list My Rated Movies as guest user
    Then  Response status code is "401"
    And   Response status message is "Authentication failed: You do not have permissions to access the service."


  Scenario Outline: Test Rating incorrect values
    Given I have logged in as a guest moviedb user
    When  I try to rate a film with value "<rating>"
    Then  Response status code is "<status_code>"
    And   Response status message is "<status_message>"

    Examples: Several rating values 
     | rating       | status_code   | status_message                                            |
     | 0            | 400           | Value too low: Value must be greater than 0.0.            |
     | 5.2          | 400           | Value invalid: Value must be a multiple of 0.50.          |
     | 10.5         | 400           | Value too high: Value must be less than, or equal to 10.0.|
     | Poor         | 400           | Value too low: Value must be greater than 0.0.            |