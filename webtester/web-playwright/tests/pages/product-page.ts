// @ts-check

import { type Page, type Locator, expect } from '@playwright/test';
import { ShopPrimaryHeader } from './shop-primary-header';

export class ProductPage {
  private readonly page: Page;
  private readonly backToProductsButton: Locator;
  public  readonly primaryHeader: ShopPrimaryHeader;
  public readonly name: Locator;
  public readonly description: Locator;
  public readonly price: Locator;
  private readonly addToCartButton: Locator;
  private readonly image: Locator;

  constructor(page: Page, primaryHeader: ShopPrimaryHeader) {
    this.page = page;
    this.primaryHeader = primaryHeader;
    this.backToProductsButton = this.page.getByRole('button').and(this.page.getByTestId('back-to-products'));
    this.name = this.page.getByTestId('inventory-item-name');
    this.description = this.page.getByTestId('inventory-item-desc');
    this.price = this.page.getByTestId('inventory-item-price');
    this.addToCartButton = this.page.getByRole('button').and(this.page.getByTestId('add-to-cart'));
    this.image = this.page.locator('img[data-test^="item-"]');
  }

  // Wait for elements that uniquely identify this page to be present
  async isReady() {
    await expect(this.page).toHaveURL(new RegExp("^.*inventory-item.html"));
    await expect(this.addToCartButton).toBeVisible();
    await this.primaryHeader.isReady();
    await expect(this.name).toBeVisible();
    await expect(this.price).toBeVisible();
    await expect(this.image).toBeVisible();
  }

  async clickBackToProductsButton() {
    await this.backToProductsButton.click();
  }
}