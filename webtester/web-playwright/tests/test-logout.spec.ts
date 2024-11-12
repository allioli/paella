// @ts-check

import { test } from './custom-fixtures/base-test';

test.afterEach(async ({ page }) => {
    await page.close();
  });

test('should log out from shop', async ({ productListPage, loginPage }) => {
    // Given I am in products page
    await productListPage.goto();

    // When I logout
    await productListPage.primaryHeader.logout();
    
    // Then I should see the login page
    await loginPage.isReady();
});
