import { test, expect } from './custom-fixtures/base-test';

test.afterEach(async ({ page }) => {
    await page.close();
  });

test('should show inventory after log in', async ({ shopItemsPage }) => {
    await shopItemsPage.goto();
    // This will not stop the test when failed
    await expect.soft(shopItemsPage.items).toHaveCount(6);
});
