Feature: MovieDB API Movie rating
  Movie ratings for the people.

  Scenario: Rate The GodFather
    Given I have logged in as a moviedb user
    When  I rate The GodFather with value 8.5
    Then  A new record should appear in My Rated Movies with rating 8.5

  