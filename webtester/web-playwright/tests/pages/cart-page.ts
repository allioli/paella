// @ts-check

import { type Page, type Locator, expect } from '@playwright/test';
import { ShopPrimaryHeader } from './shop-primary-header';

export class CartPage {
  private readonly page: Page;
  public  readonly primaryHeader: ShopPrimaryHeader;
  public  readonly cartItems: Locator;
  private readonly continueShoppingButton: Locator;
  private readonly checkoutButton: Locator;
   
  constructor(page: Page, primaryHeader: ShopPrimaryHeader) {
    this.page = page;
    this.primaryHeader = primaryHeader; 
    this.cartItems = this.page.getByTestId('inventory-item');
    this.continueShoppingButton = this.page.getByRole('button').and(this.page.getByTestId('continue-shopping'));
    this.checkoutButton = this.page.getByRole('button').and(this.page.getByTestId('checkout'));
  }

  async goto() {
    await this.page.goto('/cart.html');
  }

  // Wait for elements that uniquely identify this page to be present
  async isReady() {
    await expect(this.page).toHaveURL(new RegExp("^.*cart.html"));
    await expect(this.continueShoppingButton).toBeVisible();
    await expect(this.checkoutButton).toBeVisible();
    await this.primaryHeader.isReady();
  }

  async clickCheckoutButton() {
    await this.checkoutButton.click();
  }

  public getRemoveProductFromCartButtonLocator(productName: string) {
    const testId =  "remove-" + productName;
    return this.page.getByTestId(testId);
  }  
}
 