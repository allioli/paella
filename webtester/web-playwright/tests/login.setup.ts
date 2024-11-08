// @ts-check

import { test as setup } from './custom-fixtures/base-test';
import { STORAGE_STATE } from '../playwright.config';

setup('log in to shop', async ({ page, shopItemsPage, loginPage }) => {
    await loginPage.goto();
    await loginPage.login(process.env.USERNAME!, process.env.PASSWORD!);
    await shopItemsPage.isReady();
    await page.context().storageState({ path: STORAGE_STATE });
});
