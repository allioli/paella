// @ts-check

import { test, expect } from './custom-fixtures/base-test';

test.afterEach(async ({ page }) => {
    await page.close();
  });

test.skip('should show inventory after log in', async ({ pageWithUserLoggedIn, productListPage }) => {
    await productListPage.isReady();
    // This will not stop the test when failed
    await expect.soft(productListPage.productCards).toHaveCount(6);
});

test.skip('should log out from shop', async ({ pageWithUserLoggedIn, productListPage, loginPage }) => {
    await productListPage.primaryHeader.logout();
    await loginPage.isReady();
});
