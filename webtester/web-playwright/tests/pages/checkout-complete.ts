// @ts-check

import { type Page, type Locator, expect } from '@playwright/test';
import { ShopPrimaryHeader } from './shop-primary-header';
import { clearAndFill } from '../helpers';

export class CheckoutComplete {
  private readonly page: Page;
  public  readonly primaryHeader: ShopPrimaryHeader;
  private readonly checkoutCompleteContainer: Locator;
  private readonly backHomeButton: Locator;
   
  constructor(page: Page, primaryHeader: ShopPrimaryHeader) {
    this.page = page;
    this.primaryHeader = primaryHeader; 
    this.checkoutCompleteContainer = this.page.getByTestId('checkout-complete-container');
    this.backHomeButton = this.page.getByRole('button').and(this.page.getByTestId('back-to-products'));
  }

  // Wait for elements that uniquely identify this page to be present
  async isReady() {
    await expect(this.page).toHaveURL(new RegExp("^.*checkout-complete.html"));
    await expect(this.checkoutCompleteContainer).toBeVisible();
    await expect(this.backHomeButton).toBeVisible();
    await this.primaryHeader.isReady();
  }

  async clickBackHomeButton() {
    await this.backHomeButton.click();
  }
}
 