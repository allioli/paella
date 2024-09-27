# Overview

This is a Cypress project in Javascript.

It will run test cases against https://www.lambdatest.com/selenium-playground on a local Chrome browser.

Follow the steps [here](https://testgrid.io/blog/cypress-testing/) to set up the project locally.

Results are visible on the local Cypress runner.


## Contents


| **Folder / File**                                                                                                          | **Contents**                                                   |
|----------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------|
| [cypress/e2e](cypress/e2e)                             | Spec files with tests                           |
| [cypress/fixtures](cypress/fixtures)                             | Fixtures for parameterised tests                           |
| [cypress.config.js](cypress.config.js)                 | Cypress config file               |


## Dependencies

- Cypress 13.15.0
- Bundled Node version: 18.17.1

## How to run
### With Cypress runner GUI
`npx cypress open`

### With console headless runner
`npx cypress run`

## Sources
* [Cypress Testing](https://testgrid.io/blog/cypress-testing/)
* [Cypress built-in Actionability](https://docs.cypress.io/guides/core-concepts/interacting-with-elements)
* [Cypress best practices](https://docs.cypress.io/guides/references/best-practices)