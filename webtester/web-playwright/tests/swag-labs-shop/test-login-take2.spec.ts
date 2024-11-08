// @ts-check

import { test, expect } from '@playwright/test';
import { LoginPage } from './pages/login-page';


test.skip('test', async ({ page }) => {
  const loginPage = new LoginPage(page);
  await loginPage.goto();
  await loginPage.login('standard_user', 'secret_sauce');
});

 