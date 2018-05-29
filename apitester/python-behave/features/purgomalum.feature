
Feature: Purgomalum web service
   
  Scenario Outline: Test purgomalum basic filtering
    Given I create purgomalum query with "<text>"
    When I perform request to purgomalum
    Then the request succeeds
    And web service returns "<expected result>" because "<error_explanation>"

   Examples: Texts without profanity 
     | text                                                  | expected result                                        | error_explanation                                      |
     | You are an embarrassment                              | You are an embarrassment                               | Embarrassment should be in white list                  |
     | That's good, he repeated and began to smile, I'm out! | That's good, he repeated and began to smile, I'm out!  | Quotes in original text replaced by special characters |
     
   Examples: Texts with profanity 
     | text                                                  | expected result                                        | error_explanation                                      |
     | Is it Fuck or ffffffuuuuuuckkkkkkkkk... or fFUCK?     | Is it **** or ****... or ****?                         | One or more offending words not replaced|
     | Hello $sssssshithe@d$$$$$, yes, you, $$hith3@dddd$    | Hello *********, yes, you, *********                   | One or more offending words not replaced|
     | Welcome class of 2018 to Dick Head                    | Welcome class of 2018 to ********                      | Profanity word separated by spaces not identified|
     | Right as f#ck                                         | Right as f#ck                                          | "Missing characters from original text in result|


  Scenario Outline: Test purgomalum fill text parameter
    Given I create purgomalum query with "<text>"
    And I add parameter "fill_text" with value "<fill_text>"
    When I perform request to purgomalum
    Then the request succeeds
    And web service returns "<expected result>" because "<error_explanation>"

   Examples: Texts with profanity
     | text                                                        | fill_text             | expected result                                        | error_explanation                                      |
     | Good... he repeated and began to snigger, I am a bastard    |  nigger               | Good... he repeated and began to snigger, I am a nigger| Snigger should be white listed. Values for fill_text should also be filtered|
     | Shut your mug you bloody cunt                               | %%%%%%%%%%%%%%%%%%%%  | Invalid User Replacement Text                          | Invalid User Replacement text not reported |
     | Shut your mug you bloody cunt                               | bbbbbbbbbbbbbbbbbbbbb | User Replacement Text Exceeds Limit of 20 Characters.  | Error message does not report limit of 20 characters |
    

  Scenario Outline: Test purgomalum add parameter
    Given I create purgomalum query with "<text>"
    And I add parameter "add" with value "<add>"
    When I perform request to purgomalum
    Then the request succeeds
    And web service returns "<expected result>" because "<error_explanation>"

   Examples: Texts with user-defined profanity
     | text                                                        | add        | expected result                           | error_explanation                                      |
     | Welcome class of 222.0.1.8888 to AssVille!                  |  2018,ill  | Welcome class of **** to ***V***e!        | Added profanity word separated by dots not identified  |
     | Who said clowns are creepy?                                 |  clowns,#  | Invalid Characters in User Black List     | Expected error because invalid character hash in add parameter  |
     | the last time you look like a smart yass?                   |  yass      | the last time you look like a smart y***? | 'ass' expected to be filtered from text before 'yass', since 'yass' was added by user  |
 
 Scenario Outline: Test purgomalum add limits
    Given I create purgomalum query with "<text>"
    And I add parameter "add" with value "<add>"
    And I add parameter "fill_text" with value "<fill_text>"
    When I perform request to purgomalum
    Then the request succeeds
    And web service returns "<expected result>" because "<error_explanation>"

   Examples: Texts with long words
     | text                                                        | fill_text |  add   | expected result                           | error_explanation                                      |
     | Super ikGW564MoHBflQmRn9QnNk2EEL9pfVel2Y3wE1WzkiBQ4pHCJoJesKbNGGI9vVwF4KOmEoaFd9o8QHzg7MnwtXP2yPPLD9OA5CqNtMs2RamM2tyJy08rFF4QYtBi0rSFadZzt2EO9LOJClo7i6m1H75aDWDm0GlfCyt0U3lmX2xr0uDfkD8eECDlHhMw9BF5bEovxgiZ | ! | Super ikGW564MoHBflQmRn9QnNk2EEL9pfVel2Y3wE1WzkiBQ4pHCJoJesKbNGGI9vVwF4KOmEoaFd9o8QHzg7MnwtXP2yPPLD9OA5CqNtMs2RamM2tyJy08rFF4QYtBi0rSFadZzt2EO9LOJClo7i6m1H75aDWDm0GlfCyt0U3lmX2xr0uDfkD8eECDlHhMw9BF5bEovxgiZ | Super ! | Offending word with size 200 not filtered correctly from text  |
     | Super ikGW564MoHBflQmRn9QnNk2EEL9pfVel2Y3wE1WzkiBQ4pHCJoJesKbNGGI9vVwF4KOmEoaFd9o8QHzg7MnwtXP2yPPLD9OA5CqNtMs2RamM2tyJy08rFF4QYtBi0rSFadZzt2EO9LOJClo7i6m1H75aDWDm0GlfCyt0U3lmX2xr0uDfkD8eECDlHhMw9BF5bEovxgiZA | ! | Super ikGW564MoHBflQmRn9QnNk2EEL9pfVel2Y3wE1WzkiBQ4pHCJoJesKbNGGI9vVwF4KOmEoaFd9o8QHzg7MnwtXP2yPPLD9OA5CqNtMs2RamM2tyJy08rFF4QYtBi0rSFadZzt2EO9LOJClo7i6m1H75aDWDm0GlfCyt0U3lmX2xr0uDfkD8eECDlHhMw9BF5bEovxgiZA | User Black List Exceeds Limit of 200 Characters. | Error message does not report limit of 200 characters for add parameter breached  |
     
 Scenario Outline: Test purgomalum fill_char
    Given I create purgomalum query with "<text>"
    And I add parameter "add" with value "<add>"
    And I add parameter "fill_char" with value "<fill_char>"
    When I perform request to purgomalum 
    Then the request succeeds
    And web service returns "<expected result>" because "<error_explanation>"

   Examples: Texts with user-defined profanity
     | text     | fill_char |  add     | expected result                                 | error_explanation                                      |
     | Stay put | -         | put      | Stay ---                                        | Unexpected error for fill_text parameter with correct value  |
     | Stay put | @         | put      | Invalid User Replacement Characters             | Missing error about invalid user replacement character  |
    