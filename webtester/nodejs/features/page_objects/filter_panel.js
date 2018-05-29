
const By = require('selenium-webdriver').By;
const until = require('selenium-webdriver').until;

var filterPanel = function () {

    const elements = {
        minPriceInput: By.css('input[id^="minprice-Price"]'),
        maxPriceInput: By.css('input[id^="maxprice-Price"]'),
        sortByPriceAscInputRadio: By.xpath('//div[@class="price_asc"]/input'),
        sortByPriceDescInputRadio: By.xpath('//div[@class="price_desc"]/input')
    };

    filterPanel.prototype.setMinPrice = function (context, minPrice) {
        context.driver.wait(until.elementLocated(elements.minPriceInput));
        return context.driver.findElement(elements.minPriceInput).sendKeys(minPrice);
    };

    filterPanel.prototype.setMaxPrice = function (context, maxPrice) {
        context.driver.wait(until.elementLocated(elements.maxPriceInput));
        return context.driver.findElement(elements.maxPriceInput).sendKeys(maxPrice);
    };

    filterPanel.prototype.clickSortByPriceAscInputRadio = function (context) {
        context.driver.wait(until.elementLocated(elements.sortByPriceAscInputRadio));
        return context.driver.findElement(elements.sortByPriceAscInputRadio).click();
    };

    filterPanel.prototype.clickSortByPriceDescInputRadio = function (context) {
        context.driver.wait(until.elementLocated(elements.sortByPriceDescInputRadio));
        return context.driver.findElement(elements.sortByPriceDescInputRadio).click();
    };



};

module.exports = new filterPanel();