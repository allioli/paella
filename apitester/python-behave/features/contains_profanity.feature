
Feature: Purgomalum containsprofanity 
   
  Scenario Outline: Test containsprofanity method of purgomalum web service
    When I query containsprofanity with "<text>"
    Then the request succeeds
    And web service returns "<expected result>" because "<error_explanation>"

   Examples: Texts without profanity
     | text                   | expected result | error_explanation                                          |
     | Welcome to Scunthorpe! | false           | Scunthorpe is a legit city name and should be white listed |
     | Right as f#ck          | false           | f#ck is not expected to be filtered from text              |

 