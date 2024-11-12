// @ts-check

import { type Page, type Locator, expect } from '@playwright/test';
import { ShopPrimaryHeader } from './shop-primary-header';

export class ProductsPage {
  private readonly page: Page;
  public  readonly primaryHeader: ShopPrimaryHeader;
  public  readonly productCards: Locator;
  public  readonly productCardPrices: Locator;
  public  readonly productCardNames: Locator;
  private readonly productSortContainer: Locator;

  constructor(page: Page, primaryHeader: ShopPrimaryHeader) {
    this.page = page;
    this.primaryHeader = primaryHeader; 
    this.productSortContainer = this.page.getByTestId('product-sort-container');
    this.productCards = this.page.getByTestId('inventory-item');
    this.productCardPrices = this.productCards.getByTestId('inventory-item-price');
    this.productCardNames = this.productCards.getByTestId('inventory-item-name');
  }

  async goto() {
    await this.page.goto('/inventory.html');
  }

  // Wait for elements that uniquely identify this page to be present
  async isReady() {
    await expect(this.page).toHaveURL(new RegExp("^.*inventory.html"));
    await expect(this.productSortContainer).toBeVisible();
    await this.primaryHeader.isReady();
    await expect(this.productCards.first()).toBeVisible();
  }

  async sortProducts(requiredSortingCriteria: ProductsPage.SortingCriteria) { 
    await this.productSortContainer.click();
    let optionString = ProductsPage.sortingCriteriaMap[requiredSortingCriteria]
    await this.productSortContainer.selectOption(optionString);
  }

  async clickFirstProductCardName() { 
    await this.productCardNames.first().click();
  }

  async addProductsToShoppingCart(productNames: string[]) {
    for(let productName of productNames){
      await this.addProductToShoppingCart(productName);
    }
  }

  async addProductToShoppingCart(productName: string) {
    const addToCardButton: Locator = this.getAddProductToCartButtonLocator(productName);
    await addToCardButton.click();
  }

  public getAddProductToCartButtonLocator(productName: string) {
    const testId =  "add-to-cart-" + productName;
    return this.page.getByTestId(testId);
  }
}

export namespace ProductsPage {
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