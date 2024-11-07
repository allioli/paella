// @ts-check

import { test, expect } from './custom-fixtures/base-test';

test.afterEach(async ({ page }) => {
    await page.close();
  });

test('should show inventory after log in', async ({ pageWithUserLoggedIn, shopItemsPage }) => {
    await shopItemsPage.isReady();
    // This will not stop the test when failed
    await expect.soft(shopItemsPage.items).toHaveCount(6);
});

test('should log out from shop', async ({ pageWithUserLoggedIn, shopItemsPage, loginPage }) => {
    await shopItemsPage.primaryHeader.logout();
    await loginPage.isReady();
});
