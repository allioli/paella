// @ts-check

import { type Page, type Locator, expect } from '@playwright/test';
import { ShopPrimaryHeader } from './shop-primary-header';

export class ShopItemsPage {
  private readonly page: Page;
  public  readonly primaryHeader: ShopPrimaryHeader;
  public  readonly items: Locator;
  private readonly productSortContainer: Locator;

  constructor(page: Page, primaryHeader: ShopPrimaryHeader) {
    this.page = page;
    this.primaryHeader = primaryHeader; 
    this.productSortContainer = this.page.getByTestId('product-sort-container');
    this.items = this.page.getByTestId('inventory-item');
  }

  // Wait for elements that uniquely identify this page to be present
  async isReady() {
    await expect(this.page).toHaveURL(new RegExp("^.*inventory.html"));
    await expect(this.productSortContainer).toBeVisible();
    await expect(this.items.first()).toBeVisible();
  }
}