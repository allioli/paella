import { test, expect } from './custom-fixtures/base-test';
import { ProductListPage } from './pages/products-page';

test.describe('View product tests', {
  tag: '@view-products',
}, () => {
  test.afterEach(async ({ page }) => {
      await page.close();
    });

  test('should show available products after log in', async ({ productListPage }) => {
      // Given I am in products page
      await productListPage.goto();
      await productListPage.isReady();

      // Then I should see 6 products
      await expect.soft(productListPage.productCards).toHaveCount(6);
  });

  [
    { sortCriteria: ProductListPage.SortingCriteria.priceASC, expected: ["$7.99", "$9.99", "$15.99", "$15.99", "$29.99", "$49.99"] },
    { sortCriteria: ProductListPage.SortingCriteria.priceDESC, expected: ["$49.99", "$29.99", "$15.99", "$15.99", "$9.99", "$7.99"] },
  ].forEach(({ sortCriteria, expected }) => {
    test(`should sort products by price according to ${sortCriteria}`, async ({ productListPage }) => {
      // Given I am in products page
      await productListPage.goto();
      await productListPage.isReady();

      // When I sort products by price
      await productListPage.sortProducts(sortCriteria);

      // Then I should see products sorted by price
      await expect(productListPage.productCardPrices).toHaveText(expected);
    });
  });

  test('should show product details and go back to products', async ({ productListPage, productPage }) => {
    // Given I am in products page
    await productListPage.goto();
    await productListPage.isReady();

    // When I click on the first product card
    await productListPage.clickFirstProductCardName();

    // Then I should see the product detail page
    await productPage.isReady();
    await expect(productPage.name).not.toBeEmpty();
    await expect(productPage.description).not.toBeEmpty();
    await expect(productPage.price).not.toBeEmpty();

    // And I can go back to products page
    await productPage.clickBackToProductsButton();
    await productListPage.isReady();
  });
});
