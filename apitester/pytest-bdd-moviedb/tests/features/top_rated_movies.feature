@top_rated_movies
Feature: MovieDB API Top rated movies
  Top rated movie list.

  @smoke
  Scenario: Check top rated movie list
    Given I am a user in "US" region with language "en-US"
    When  I check Top Rated Movie list
    Then  Movies should be sorted by vote_average in descending order

  Scenario Outline: Test Region vs Language
    Given I am a user in "<region>" region with language "<language>"
    When  I check Top Rated Movie list
    Then  Top rated movie has property "title" like "<title>"
    And   Top rated movie has property "original_title" like "<original_title>"
    And   Top rated movie has property "release_date" like "<release_date>"
    And   Top rated movie has property "original_language" like "<original_language>"
    

    Examples: Top rated movie properties change across language, region
      | language  | region |  title                    | release_date | original_title             | original_language |
      | en-US     | US     |  The Shawshank Redemption | 1994-09-23   | The Shawshank Redemption   |  en               |
      | en-US     | ES     |  The Shawshank Redemption | 1995-02-24   | The Shawshank Redemption   |  en               |
      | es-SP     | ES     |  Cadena perpetua          | 1995-02-24   | The Shawshank Redemption   |  en               |
      | es-SP     | US     |  Cadena perpetua          | 1994-09-23   | The Shawshank Redemption   |  en               |
