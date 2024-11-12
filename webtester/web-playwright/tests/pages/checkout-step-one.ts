// @ts-check

import { type Page, type Locator, expect } from '@playwright/test';
import { ShopPrimaryHeader } from './shop-primary-header';
import { clearAndFill } from '../helpers';

export class CheckoutStepOnePage {
  private readonly page: Page;
  public  readonly primaryHeader: ShopPrimaryHeader;
  private  readonly inputFirstName: Locator;
  private  readonly inputLastName: Locator;
  private  readonly inputZipCode: Locator;
  private readonly continueButton: Locator;
  private readonly cancelButton: Locator;
   
  constructor(page: Page, primaryHeader: ShopPrimaryHeader) {
    this.page = page;
    this.primaryHeader = primaryHeader; 
    this.inputFirstName = this.page.getByRole('textbox').and(this.page.getByTestId('firstName'));
    this.inputLastName = this.page.getByRole('textbox').and(this.page.getByTestId('lastName'));
    this.inputZipCode = this.page.getByRole('textbox').and(this.page.getByTestId('postalCode'));
    this.continueButton = this.page.getByRole('button').and(this.page.getByTestId('continue'));
    this.cancelButton = this.page.getByRole('button').and(this.page.getByTestId('cancel'));
  }

  async goto() {
    await this.page.goto('/cart.html');
  }

  // Wait for elements that uniquely identify this page to be present
  async isReady() {
    await expect(this.page).toHaveURL(new RegExp("^.*checkout-step-one.html"));
    await expect(this.inputFirstName).toBeVisible();
    await expect(this.inputLastName).toBeVisible();
    await expect(this.inputZipCode).toBeVisible();
    await expect(this.continueButton).toBeVisible();
    await expect(this.cancelButton).toBeVisible();
    await this.primaryHeader.isReady();
  }

  async clickContinueButton() {
    await this.continueButton.click();
  }

  async fillCheckoutInfo(firstName: string, lastName: string, zipCode: string) {
    await clearAndFill(this.inputFirstName, firstName);
    await clearAndFill(this.inputLastName, lastName);
    await clearAndFill(this.inputZipCode, zipCode);
  }

   
}
 