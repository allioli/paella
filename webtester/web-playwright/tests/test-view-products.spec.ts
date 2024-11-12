import { test, expect } from './custom-fixtures/base-test';
import { ShopItemsPage } from './pages/shop-items-page';

test.afterEach(async ({ page }) => {
    await page.close();
  });

test('should show available products after log in', async ({ shopItemsPage }) => {
    await shopItemsPage.goto();
    await shopItemsPage.isReady();
    await expect.soft(shopItemsPage.productCards).toHaveCount(6);
});

[
  { sortCriteria: ShopItemsPage.SortingCriteria.priceASC, expected: ["$7.99", "$9.99", "$15.99", "$15.99", "$29.99", "$49.99"] },
  { sortCriteria: ShopItemsPage.SortingCriteria.priceDESC, expected: ["$49.99", "$29.99", "$15.99", "$15.99", "$9.99", "$7.99"] },
].forEach(({ sortCriteria, expected }) => {
  test(`should sort products by price according to ${sortCriteria}`, async ({ shopItemsPage }) => {
    await shopItemsPage.goto();
    await shopItemsPage.isReady();
    await shopItemsPage.sortProducts(sortCriteria);
    await expect(shopItemsPage.productCardPrices).toHaveText(expected);
  });
});

test('should show product details and go back to products', async ({ shopItemsPage, productPage }) => {
  await shopItemsPage.goto();
  await shopItemsPage.isReady();
  await shopItemsPage.clickFirstProductCardName();
  await productPage.isReady();
  
  await expect(productPage.name).not.toBeEmpty();
  await expect(productPage.description).not.toBeEmpty();
  await expect(productPage.price).not.toBeEmpty();

  await productPage.clickBackToProductsButton();
  await shopItemsPage.isReady();
});
