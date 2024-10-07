# Overview

This is a Cypress project in Javascript.

It will run test cases against https://www.lambdatest.com/selenium-playground on a local Chrome browser.

Follow the steps [here](https://docs.cypress.io/guides/getting-started/installing-cypress) to set up the project locally.

Results are visible on the local Cypress runner.


## Contents


| **Folder / File**                                                                                                          | **Contents**                                                   |
|----------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------|
| [cypress/e2e](cypress/e2e)                             | Spec files with tests                           |
| [cypress/fixtures](cypress/fixtures)                             | Fixtures for parameterised tests                           |
| [cypress/pages](cypress/pages)                             | Page Objects files                          |
| [cypress.config.js](cypress.config.js)                 | Cypress config file               |


## Dependencies

- Cypress 13.15.0
- Bundled Node version: 18.17.1

## How to run
### With Cypress runner GUI
`npx cypress open`

### With console headless runner
`npx cypress run`

## Highlights

### Page Object model
Page Objects encapsulate element locator definition and URLs from test logic.

### Parameterized test case
There is an example of data-driven test cases in [simpleForm.cy.js)](cypress/e2e/simpleForm.cy.js). A [fixture file](cypress/fixtures/sums.json) defines different combination of inputs and expected results.

#### Example [simpleForm.cy.js)](cypress/e2e/simpleForm.cy.js))
```javascript
describe('User interacts with Simple Form', () => {
  const sum_fixtures = require('../fixtures/sums')

  ...

  sum_fixtures.forEach((sum_fixture) => {
    it(`Form adding ${sum_fixture.number1} and ${sum_fixture.number2}`, () => {
      // WHEN I enter <number1> and <number2> to the sum up form
      cy.get('#sum1').type(sum_fixture.number1)
      cy.get('#sum2').type(sum_fixture.number2)

      // AND I click on Sum button
      cy.get('#gettotal > button').click()

      // THEN I should see the result of the sum as <result>
      cy.get('#addmessage')
      .should('have.text', sum_fixture.result)

    })
  })
})
```



## Sources
* [Cypress Getting Started](https://docs.cypress.io/guides/getting-started/installing-cypress)
* [Cypress built-in Actionability](https://docs.cypress.io/guides/core-concepts/interacting-with-elements)
* [Cypress best practices](https://docs.cypress.io/guides/references/best-practices)
* [Writing Assertions](https://testgrid.io/blog/cypress-assertions/)