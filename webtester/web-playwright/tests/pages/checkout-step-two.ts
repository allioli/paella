// @ts-check

import { type Page, type Locator, expect } from '@playwright/test';
import { ShopPrimaryHeader } from './shop-primary-header';
import { clearAndFill } from '../helpers';

export class CheckoutStepTwoPage {
  private readonly page: Page;
  public  readonly primaryHeader: ShopPrimaryHeader;
  public  readonly checkoutItems: Locator;
  public  readonly paymentInfo: Locator;
  public  readonly shippingInfo: Locator;
  public  readonly totalAmount: Locator;
  private readonly finishButton: Locator;
  private readonly cancelButton: Locator;
   
  constructor(page: Page, primaryHeader: ShopPrimaryHeader) {
    this.page = page;
    this.primaryHeader = primaryHeader; 
    this.checkoutItems = this.page.getByTestId('inventory-item');
    this.finishButton = this.page.getByRole('button').and(this.page.getByTestId('finish'));
    this.cancelButton = this.page.getByRole('button').and(this.page.getByTestId('cancel'));
    this.paymentInfo = this.page.getByTestId('payment-info-value');
    this.shippingInfo = this.page.getByTestId('shipping-info-value');
    this.totalAmount = this.page.getByTestId('total-label');
  }

  async goto() {
    await this.page.goto('/cart.html');
  }

  // Wait for elements that uniquely identify this page to be present
  async isReady() {
    await expect(this.page).toHaveURL(new RegExp("^.*checkout-step-two.html"));
    await expect(this.finishButton).toBeVisible();
    await expect(this.paymentInfo).toBeVisible();
    await expect(this.shippingInfo).toBeVisible();
    await expect(this.totalAmount).toBeVisible();
    await expect(this.cancelButton).toBeVisible();
    await this.primaryHeader.isReady();
  }
}
 