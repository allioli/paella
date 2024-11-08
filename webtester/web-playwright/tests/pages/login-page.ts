// @ts-check

import { type Page, type Locator, expect } from '@playwright/test';
import { clearAndFill } from '../helpers'

export class LoginPage {
  private readonly page: Page;
  private readonly inputUserName: Locator;
  private readonly inputPassword: Locator;
  private readonly loginButton: Locator;

  constructor(page: Page) {
    this.page = page;
    this.inputUserName = this.page.getByTestId('username');
    this.inputPassword = this.page.getByTestId('password');
    this.loginButton = this.page.getByTestId('login-button');

  }

  async goto() {
    await this.page.goto('/');
  }

  // Wait for elements that uniquely identify this page to be present
  async isReady() {
    return expect(this.loginButton).toBeVisible();
  }

  async login(userName: string, password: string) {
    await clearAndFill(this.inputUserName, userName);
    await clearAndFill(this.inputPassword, password);
    await this.loginButton.click();
  }
}