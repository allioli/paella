// @ts-check

import { type Page, type Locator, expect } from '@playwright/test';

export class ShopPrimaryHeader {
  private page: Page;
  private headerContainer: Locator;
  private burgerMenuButton: Locator;
  private shoppingCartLink: Locator;
  private logoutMenuOption: Locator;

  constructor(page: Page) {
    this.page = page;
    this.headerContainer = this.page.getByTestId('primary-header');
    this.burgerMenuButton = this.headerContainer.locator('#react-burger-menu-btn');
    this.shoppingCartLink = this.headerContainer.getByTestId('shopping-cart-link');
    this.logoutMenuOption = this.headerContainer.getByTestId('logout-sidebar-link');
  }

  async logout() {
    await this.burgerMenuButton.click();
    await this.logoutMenuOption.click();
  }

  async clickCartLink() {
    await this.shoppingCartLink.click();
  }
  
  
  
}