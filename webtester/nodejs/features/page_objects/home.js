const By = require('selenium-webdriver').By;
const until = require('selenium-webdriver').until;


var homePage = function () {

    const elements = {
        loginButton: By.css('[data-test="login"]'),
        avatarIconXPath:  '//div[contains(@class, "Header__avatar")]',
        turnOnNotificationLink: By.css('a[class^=PushWarning__link]'),
        avatarButton: By.xpath('//div[contains(@class, "Header__avatar")]/..'),
        logoutMenuItem: By.css('[data-test="menu-logout"]'),
        logoutConfirmationButton: By.xpath('//div[contains(@class, "Dialog__actions")]//span[text()="Log Out"]/../..'),
        searchInput: By.css('input[id*="Whatareyoulookingfor"]'),
        clearSearchButton: By.css('button[class^="Header__clearSearch"]')

    };

    const url = 'https://us.letgo.com/en';

    homePage.prototype.navigate = function (context) {
        return context.driver.navigate().to(url);
    };

    homePage.prototype.clickLoginButton = function (context) {
        context.driver.wait(until.elementLocated(elements.loginButton));
        return context.driver.findElement(elements.loginButton).click();
    };

    homePage.prototype.waitForCorrectAvatarIcon = function (context, avatarIconLetter) {

        const correctAvatarIcon = By.xpath(elements.avatarIconXPath + '[text()="' + avatarIconLetter + '"]');
        return context.driver.wait(until.elementLocated(correctAvatarIcon));
    };

    homePage.prototype.waitForPushNotificationLink = function (context) {
        return context.driver.wait(until.elementLocated(elements.turnOnNotificationLink));
    };

    homePage.prototype.clickAvatarButton = function (context) {
        context.driver.wait(until.elementLocated(elements.avatarButton));
        return context.driver.findElement(elements.avatarButton).click();
    };

    homePage.prototype.clickLogoutMenuItem = function (context) {
        context.driver.wait(until.elementLocated(elements.logoutMenuItem));
        return context.driver.findElement(elements.logoutMenuItem).click();
    };

    homePage.prototype.clickLogoutConfirmationButton = function (context) {
        context.driver.wait(until.elementLocated(elements.logoutConfirmationButton));
        return context.driver.findElement(elements.logoutConfirmationButton).click();
    };

    homePage.prototype.setSearchText = function (context, searchString) {
        context.driver.wait(until.elementLocated(elements.searchInput));
        return context.driver.findElement(elements.searchInput).sendKeys(searchString + "\n");
    };

    homePage.prototype.getSearchText = function (context) {
        context.driver.wait(until.elementLocated(elements.searchInput));
        return context.driver.findElement(elements.searchInput).getAttribute("value");
    };

    homePage.prototype.clickClearSearchButton = function (context) {
        context.driver.wait(until.elementLocated(elements.clearSearchButton));
        return context.driver.findElement(elements.clearSearchButton).click();
    };




};

module.exports = new homePage();