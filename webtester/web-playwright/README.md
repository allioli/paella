# Overview

This is a Playwright project in Typescript.

It will run test cases against https://www.saucedemo.com on a selection of browsers.

Follow the steps [here](https://playwright.dev/docs/intro) to set up the project locally.

Results are visible on the local Playwright UI or reports page.


## Contents


| **Folder / File**                                                                                                          | **Contents**                                                   |
|----------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------|
| [tests](tests)                             | Test and setup spec files                           |
| [custom-fixtures](tests/custom-fixtures)                             | Reusable playwright fixtures with page objects, login auth                           |
| [helpers.ts](tests/helpers.ts)                             | Helper functions                          |
| [pages](tests/pages)                 | Page Object Model classes              |
| [tsconfig.json](tsconfig.json)                 | Typescript compile options               |
| [.auth](.auth)                 | Folder with JSON blob containing auth data after succesful login               |
| [.env](.env)                 | Env file with valid login credentials               |


## Dependencies

- Playwright Version 1.47.2
- Bundled Node version: v20.17.0

## How to run
### With Playwright GUI
`npx playwright test --ui`

### Headless test run with traces on
`npx playwright test  --trace on`

### Headless test run with specifig tagged tests
`npx playwright test --grep @view-products`

## Highlights

### Page Object model
Page Objects encapsulate element locator definition, user interaction, URLs and basic element assertions from test logic. We are composing POM objects with reusable classes that model common UI components, like `ShopPrimaryHeader`. Since almost all pages feature the same header, they re-use the same POM to keep the code DRY.

Page object member locators that are relevant for tests to assert on are kept public, to keep it simple and similar to Flat Page Object models when it makes sense.

### Reusable fixtures
Page Objects are initialised in reusable and composable fixtures, that are available to all tests. There is also an example of fixture that provides a "logged in" state to any test, in the setup stage.

#### Example [base-test.ts](ttests/custom-fixtures/base-test.ts)
```typescript
...
pageWithUserLoggedIn: async ({ page, loginPage }, use) => {
        // SetUp
        await loginPage.goto();
        await loginPage.login(process.env.USERNAME!, process.env.PASSWORD!);
        
        // Test
        await use(page);
      },

```


### Parameterized test case
There are examples of data-driven test cases in [test-view-products.spec.ts](tests/test-view-products.spec.ts) and [test-purchase-products.spec.ts](tests/test-purchase-products.spec.ts).

#### Example [test-view-products.spec.ts](tests/test-view-products.spec.ts)
```typescript
...
  [
    { sortCriteria: ProductListPage.SortingCriteria.priceASC, expected: ["$7.99", "$9.99", "$15.99", "$15.99", "$29.99", "$49.99"] },
    { sortCriteria: ProductListPage.SortingCriteria.priceDESC, expected: ["$49.99", "$29.99", "$15.99", "$15.99", "$9.99", "$7.99"] },
  ].forEach(({ sortCriteria, expected }) => {
    test(`should sort products by price according to ${sortCriteria}`, async ({ productListPage }) => {
      // Given I am in products page
      await productListPage.goto();
      await productListPage.isReady();

      // When I sort products by price
      await productListPage.sortProducts(sortCriteria);

      // Then I should see products sorted by price
      await expect(productListPage.productCardPrices).toHaveText(expected);
    });
  });

```

### Re-use auth state via project dependencies
There is a setup login project that acts as a dependency to the test projects that need the user authenticated. `login.setup.ts` is called once per test run. It will simulate a user logging in to the shop using the web form, and then store the auth state to a file. After that, the dependant projects can read that file in order to open the Shop Product List page directly as a logged in user. 


## Next steps
Move the auth re-use mechanisme from project config level to a reusable fixture. De-couple project configuration from auth setup by enhancing existing fixture `loggedInPage' with the logic to login as a user would only if there is no previously stored auth state.


## Sources
* [Playwright Getting Started](https://playwright.dev/docs/intro)
* [Playwright built-in Actionability](https://playwright.dev/docs/actionability)
* [Playwright best practices](https://playwright.dev/docs/best-practices)
* [Writing Assertions](https://playwright.dev/docs/test-assertions)
* [Storing login context](https://dev.to/playwright/a-better-global-setup-in-playwright-reusing-login-with-project-dependencies-14)