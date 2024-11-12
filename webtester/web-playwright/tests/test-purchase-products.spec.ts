import { test, expect } from './custom-fixtures/base-test';


test.afterEach(async ({ page }) => {
    await page.close();
  });

  [
    { products: ["sauce-labs-fleece-jacket"], firstName: "Capn' calamity", lastName: "Romualdo", zipCode: "AR56", expected_checkout_total: "Total: $53.99" },
    { products: ["sauce-labs-bike-light", "sauce-labs-onesie"], firstName: "El user", lastName: "Hastings", zipCode: "25910", expected_checkout_total: "Total: $19.42" },
  ].forEach(({ products, firstName, lastName, zipCode,  expected_checkout_total }) => {
    test(`should see checkout with products ${products}`, async ({ productsPage, cartPage, checkoutStepOnePage, checkoutStepTwoPage }) => {
      // Given I am in products page
      await productsPage.goto();
      await productsPage.isReady();

      // When I add products to shopping cart
      await productsPage.addProductsToShoppingCart(products);

      // Then I should see added products in shopping cart page
      await productsPage.primaryHeader.clickCartLink();
      await cartPage.isReady();

      await expect(cartPage.cartItems).toHaveCount(products.length);
      for (let product of products) {
        await expect(cartPage.getRemoveProductFromCartButtonLocator(product)).toBeVisible();
      }

      // When I proceed to checkout
      await cartPage.clickCheckoutButton();
      await checkoutStepOnePage.isReady()

      // And I fill in my details and continue
      await checkoutStepOnePage.fillCheckoutInfo(firstName, lastName, zipCode);
      await checkoutStepOnePage.clickContinueButton();

      // Then I should see the Checkout summary with the expected total amount
      await checkoutStepTwoPage.isReady();
      await expect(checkoutStepTwoPage.checkoutItems).toHaveCount(products.length);
      await expect(checkoutStepTwoPage.paymentInfo).not.toBeEmpty();
      await expect(checkoutStepTwoPage.shippingInfo).not.toBeEmpty();
      await expect(checkoutStepTwoPage.totalAmount).toHaveText(expected_checkout_total);

    });
  });
