import { test, expect } from './custom-fixtures/base-test';


test.afterEach(async ({ page }) => {
    await page.close();
  });

  test('should purchase a product from product page', async ({ productListPage, productPage, cartPage, checkoutStepOnePage, checkoutStepTwoPage, checkoutCompletePage }) => {
    // Given I am in products page
    await productListPage.goto();
    await productListPage.isReady();
  
    // When I click on the first product card
    await productListPage.clickFirstProductCardName();
  
    // And I add product to shopping cart
    await productPage.isReady();
    await productPage.clickAddToCartButton();

    // Then I should see the product in the cart page
    await productPage.primaryHeader.clickCartLink();
    await cartPage.isReady();
    await expect(cartPage.cartItems).toHaveCount(1);

    // When I proceed to checkout
    await cartPage.clickCheckoutButton();
    await checkoutStepOnePage.isReady();

    // And I fill in my details
    await checkoutStepOnePage.fillCheckoutInfo("Marge", "Simpson", "04323");
    await checkoutStepOnePage.clickContinueButton();

    // And I finalise the purchase
    await checkoutStepTwoPage.isReady();
    await expect(checkoutStepTwoPage.checkoutItems).toHaveCount(1);
    await checkoutStepTwoPage.clickFinishButton();

    // Then I should see Order confirmation page
    await checkoutCompletePage.isReady();
    
    // And I can go back to products page
    await checkoutCompletePage.clickBackHomeButton();
    await productListPage.isReady();
     
  });

  [
    { products: ["sauce-labs-fleece-jacket"], firstName: "Capn' calamity", lastName: "Romualdo", zipCode: "AR56", expected_checkout_total: "Total: $53.99" },
    { products: ["sauce-labs-bike-light", "sauce-labs-onesie"], firstName: "El user", lastName: "Hastings", zipCode: "25910", expected_checkout_total: "Total: $19.42" },
  ].forEach(({ products, firstName, lastName, zipCode,  expected_checkout_total }) => {
    test(`should see checkout with products ${products}`, async ({ productListPage, cartPage, checkoutStepOnePage, checkoutStepTwoPage }) => {
      // Given I am in products page
      await productListPage.goto();
      await productListPage.isReady();

      // When I add products to shopping cart
      await productListPage.addProductsToShoppingCart(products);

      // Then I should see added products in shopping cart page
      await productListPage.primaryHeader.clickCartLink();
      await cartPage.isReady();

      await expect(cartPage.cartItems).toHaveCount(products.length);
      for (let product of products) {
        await expect(cartPage.getRemoveProductFromCartButtonLocator(product)).toBeVisible();
      }

      // When I proceed to checkout
      await cartPage.clickCheckoutButton();
      await checkoutStepOnePage.isReady();

      // And I fill in my details
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
