// @ts-check

import { test } from './custom-fixtures/base-test';

test.afterEach(async ({ page }) => {
    await page.close();
  });

test('should log out from shop', async ({ shopItemsPage, loginPage }) => {
    await shopItemsPage.goto();
    await shopItemsPage.primaryHeader.logout();
    await loginPage.isReady();
});
