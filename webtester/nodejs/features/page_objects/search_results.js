const By = require('selenium-webdriver').By;
const until = require('selenium-webdriver').until;

var searchResults = function () {

    const elements = {
        itemTitleXpath: "//h4",
        itemTitleLocator: By.css("h4"),
        itemLinkLocator: By.xpath("//h4/../.."),
        noItemsFoundMessage: By.css('div[class^="ProductListContainer__noProducts"]'),
        noItemsContainer: By.css('div[class^="ProductListContainer__noContent"]'),
        productPriceLocator: By.xpath('//div[contains(@class,"Box product-page__user-price")]/h3/span'),
        productPageHomeButton: By.css('span[class^="product-page__backButton"]')
    };

    searchResults.prototype.getRelevantItemTitle = function (context, titleWord) {

        const itemLocator = By.xpath(elements.itemTitleXpath + "[contains(text(),'" + titleWord + "')]");
        context.driver.wait(until.elementLocated(itemLocator));
        return context.driver.findElement(itemLocator).getText();
    };

    searchResults.prototype.getFirstResultItemTitle = function (context) {
        context.driver.wait(until.elementLocated(elements.itemTitleLocator));
        return context.driver.findElement(elements.itemTitleLocator).getText();
    };

    searchResults.prototype.waitForNoItemsFoundMessageVisible = function (context) {

        context.driver.wait(until.elementLocated(elements.noItemsFoundMessage));
        const element = context.driver.findElement(elements.noItemsFoundMessage);
        return context.driver.wait(until.elementIsVisible(element));
    };

    searchResults.prototype.waitForEmptyResult = function (context) {

        return context.driver.wait(until.elementLocated(elements.noItemsContainer));
    };

    searchResults.prototype.clickFirstItemLink = function (context) {
        context.driver.wait(until.elementLocated(elements.itemLinkLocator));
        const element = context.driver.findElement(elements.itemLinkLocator);
        context.driver.wait(until.elementIsVisible(element));
        return element.click();
    };

    searchResults.prototype.getProductPrice = function (context) {
        context.driver.wait(until.elementLocated(elements.productPriceLocator));
        return context.driver.findElement(elements.productPriceLocator).getText();
    };

    searchResults.prototype.clickProductPageHomeButton = function (context) {
        context.driver.wait(until.elementLocated(elements.clickProductPageHomeButton));
        return context.driver.findElement(elements.clickProductPageHomeButton).click();
    };


};

module.exports = new searchResults();