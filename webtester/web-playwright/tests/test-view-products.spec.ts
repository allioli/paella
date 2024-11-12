import { test, expect } from './custom-fixtures/base-test';
import { ProductsPage } from './pages/products-page';

test.afterEach(async ({ page }) => {
    await page.close();
  });

test('should show available products after log in', async ({ productsPage }) => {
    await productsPage.goto();
    await productsPage.isReady();
    await expect.soft(productsPage.productCards).toHaveCount(6);
});

[
  { sortCriteria: ProductsPage.SortingCriteria.priceASC, expected: ["$7.99", "$9.99", "$15.99", "$15.99", "$29.99", "$49.99"] },
  { sortCriteria: ProductsPage.SortingCriteria.priceDESC, expected: ["$49.99", "$29.99", "$15.99", "$15.99", "$9.99", "$7.99"] },
].forEach(({ sortCriteria, expected }) => {
  test(`should sort products by price according to ${sortCriteria}`, async ({ productsPage }) => {
    await productsPage.goto();
    await productsPage.isReady();
    await productsPage.sortProducts(sortCriteria);
    await expect(productsPage.productCardPrices).toHaveText(expected);
  });
});

test('should show product details and go back to products', async ({ productsPage, productPage }) => {
  await productsPage.goto();
  await productsPage.isReady();
  await productsPage.clickFirstProductCardName();
  await productPage.isReady();
  
  await expect(productPage.name).not.toBeEmpty();
  await expect(productPage.description).not.toBeEmpty();
  await expect(productPage.price).not.toBeEmpty();

  await productPage.clickBackToProductsButton();
  await productsPage.isReady();
});
