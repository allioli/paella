// @ts-check

import { test, expect } from './custom-fixtures/base-test';

test.afterEach(async ({ page }) => {
    await page.close();
  });

test.skip('should show inventory after log in', async ({ pageWithUserLoggedIn, productsPage: shopItemsPage }) => {
    await shopItemsPage.isReady();
    // This will not stop the test when failed
    await expect.soft(shopItemsPage.productCards).toHaveCount(6);
});

test.skip('should log out from shop', async ({ pageWithUserLoggedIn, productsPage: shopItemsPage, loginPage }) => {
    await shopItemsPage.primaryHeader.logout();
    await loginPage.isReady();
});
