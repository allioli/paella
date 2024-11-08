// @ts-check

import { test as base } from '@playwright/test';
import { LoginPage } from './pages/login-page';
import { ShopItemsPage } from './pages/shop-items-page';
import { ShopPrimaryHeader } from './pages/shop-primary-header';

// Extend basic test by providing a "login" fixture.
const test = base.extend<{ loginFixture: LoginPage}>({
  loginFixture: async ({ page }, use) => {
    const loginPage = new LoginPage(page);
    await loginPage.goto();
    await loginPage.login('standard_user', 'secret_sauce');
    await use(loginPage);
    await page.close();
  },
});

test.skip('should login to shop', async ({ page, loginFixture }) => {
    const primaryHeader = new ShopPrimaryHeader(page);
    const shopItemsPage = new ShopItemsPage(page, primaryHeader);
    await shopItemsPage.isReady();
});

test.skip('should log out from shop', async ({ page, loginFixture }) => {
    const primaryHeader = new ShopPrimaryHeader(page);
    const shopItemsPage = new ShopItemsPage(page, primaryHeader);
    await shopItemsPage.primaryHeader.logout();
    await loginFixture.isReady();
});
