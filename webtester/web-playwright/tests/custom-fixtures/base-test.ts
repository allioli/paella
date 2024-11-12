// @ts-check

import { test as base, Page } from '@playwright/test';
import { LoginPage } from '../pages/login-page';
import { ShopPrimaryHeader } from '../pages/shop-primary-header';
import { ShopItemsPage } from '../pages/shop-items-page';
import { ProductPage } from '../pages/product-page';


type MyTestFixtures = {
    loginPage: LoginPage
    shopPrimaryHeader: ShopPrimaryHeader
    shopItemsPage: ShopItemsPage
    productPage: ProductPage
    pageWithUserLoggedIn: Page

}

export const test = base.extend<MyTestFixtures>({
    loginPage: async ({ page }, use) => {
      await use(new LoginPage(page));
    },
    shopPrimaryHeader: async ({ page }, use) => {
        await use(new ShopPrimaryHeader(page));
    },
    shopItemsPage: async ({ page, shopPrimaryHeader}, use) => {
        await use(new ShopItemsPage(page, shopPrimaryHeader));
    },
    productPage: async ({ page, shopPrimaryHeader}, use) => {
        await use(new ProductPage(page, shopPrimaryHeader));
    },
    pageWithUserLoggedIn: async ({ page, loginPage }, use) => {
        // SetUp
        await loginPage.goto();
        await loginPage.login(process.env.USERNAME!, process.env.PASSWORD!);
        
        // Test
        await use(page);
      },
})

export { expect } from '@playwright/test';
