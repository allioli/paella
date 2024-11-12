// @ts-check

import { test as base, Page } from '@playwright/test';
import { LoginPage } from '../pages/login-page';
import { ShopPrimaryHeader } from '../pages/shop-primary-header';
import { ProductListPage } from '../pages/products-page';
import { ProductPage } from '../pages/product-page';
import { CartPage } from '../pages/cart-page';
import { CheckoutStepOnePage } from '../pages/checkout-step-one';
import { CheckoutStepTwoPage } from '../pages/checkout-step-two';
import { CheckoutComplete } from '../pages/checkout-complete';


type MyTestFixtures = {
    loginPage: LoginPage
    shopPrimaryHeader: ShopPrimaryHeader
    productListPage: ProductListPage
    productPage: ProductPage
    cartPage: CartPage
    checkoutStepOnePage: CheckoutStepOnePage
    checkoutStepTwoPage: CheckoutStepTwoPage
    checkoutCompletePage: CheckoutComplete
    pageWithUserLoggedIn: Page

}

export const test = base.extend<MyTestFixtures>({
    loginPage: async ({ page }, use) => {
      await use(new LoginPage(page));
    },
    shopPrimaryHeader: async ({ page }, use) => {
        await use(new ShopPrimaryHeader(page));
    },
    productListPage: async ({ page, shopPrimaryHeader}, use) => {
        await use(new ProductListPage(page, shopPrimaryHeader));
    },
    productPage: async ({ page, shopPrimaryHeader}, use) => {
        await use(new ProductPage(page, shopPrimaryHeader));
    },
    cartPage: async ({ page, shopPrimaryHeader}, use) => {
        await use(new CartPage(page, shopPrimaryHeader));
    },
    checkoutStepOnePage: async ({ page, shopPrimaryHeader}, use) => {
        await use(new CheckoutStepOnePage(page, shopPrimaryHeader));
    },
    checkoutStepTwoPage: async ({ page, shopPrimaryHeader}, use) => {
        await use(new CheckoutStepTwoPage(page, shopPrimaryHeader));
    },
    checkoutCompletePage: async ({ page, shopPrimaryHeader}, use) => {
        await use(new CheckoutComplete(page, shopPrimaryHeader));
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
