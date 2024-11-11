// @ts-check

import { type Page, type Locator, expect } from '@playwright/test';
import { ShopPrimaryHeader } from './shop-primary-header';

export class ShopItemsPage {
  private readonly page: Page;
  public  readonly primaryHeader: ShopPrimaryHeader;
  public  readonly itemCards: Locator;
  public  readonly itemPrices: Locator;
  private readonly productSortContainer: Locator;

  constructor(page: Page, primaryHeader: ShopPrimaryHeader) {
    this.page = page;
    this.primaryHeader = primaryHeader; 
    this.productSortContainer = this.page.getByTestId('product-sort-container');
    this.itemCards = this.page.getByTestId('inventory-item');
    this.itemPrices = this.itemCards.getByTestId('inventory-item-price');
  }

  async goto() {
    await this.page.goto('/inventory.html');
  }

  // Wait for elements that uniquely identify this page to be present
  async isReady() {
    await expect(this.page).toHaveURL(new RegExp("^.*inventory.html"));
    await expect(this.productSortContainer).toBeVisible();
    await expect(this.itemCards.first()).toBeVisible();
  }

  async sortProducts(requiredSortingCriteria: ShopItemsPage.SortingCriteria) { 
    await this.productSortContainer.click();
    let resultString = ShopItemsPage.sortingCriteriaMap[requiredSortingCriteria]
    await this.productSortContainer.selectOption(resultString);
  }
}

export namespace ShopItemsPage {
  export const enum SortingCriteria {
    priceASC  = "priceASC",
    priceDESC = "priceDESC",
    nameASC   = "nameASC",
    nameDESC  = "nameDESC",
  }

  // To be used in class method "sortProducts", if the test logic is sending requiredSortingCriteria as string, instead of well-defined enum value
  // export type SortingCriteriaType = keyof typeof SortingCriteria;

  export type SortingCriteriaToSortContainerOptionMap =  Record<SortingCriteria, string>
  
  export const sortingCriteriaMap : SortingCriteriaToSortContainerOptionMap = {
    [SortingCriteria.priceASC]:  "lohi",
    [SortingCriteria.priceDESC]: "hilo",
    [SortingCriteria.nameASC]:   "az",
    [SortingCriteria.nameDESC]:  "za"
  };


}