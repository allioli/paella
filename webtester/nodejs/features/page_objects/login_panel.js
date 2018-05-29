const By = require('selenium-webdriver').By;
const until = require('selenium-webdriver').until;

var loginPanel = function () {

    const elements = {
        emailLoginButton: By.css('[data-test="login-email"]'),
        loginInputEmail: By.css('[data-test="login-input-email"]'),
        loginInputPassword: By.css('[data-test="login-input-password"]'),
        loginInputEmailPasswordSubmitEnabled: By.css('[data-test="login-email-submit"]:not([class*="Auth__button_disabled"])'),
        loginInputEmailPasswordSubmitDisabled: By.css('[data-test="login-email-submit"][class*="Auth__button_disabled"]'),
        signUpButton: By.css('button[class^="Auth__first-btn"]'),
        fullNameInput: By.css('input[name="name"][type="text"]')
    };

    loginPanel.prototype.clickEmailLoginButton = function (context) {
        context.driver.wait(until.elementLocated(elements.emailLoginButton));
        return context.driver.findElement(elements.emailLoginButton).click();
    };

    loginPanel.prototype.clickSignUpButton = function (context) {
        context.driver.wait(until.elementLocated(elements.signUpButton));
        return context.driver.findElement(elements.signUpButton).click();
    };

    loginPanel.prototype.setEmail = function (context, email) {
        context.driver.wait(until.elementLocated(elements.loginInputEmail));
        return context.driver.findElement(elements.loginInputEmail).sendKeys(email);
    };

    loginPanel.prototype.setPassword = function (context, password) {
        context.driver.wait(until.elementLocated(elements.loginInputPassword));
        return context.driver.findElement(elements.loginInputPassword).sendKeys(password);
    };

    loginPanel.prototype.clickEmailPasswordSubmitButton = function (context) {
        context.driver.wait(until.elementLocated(elements.loginInputEmailPasswordSubmitEnabled));
        return context.driver.findElement(elements.loginInputEmailPasswordSubmitEnabled).click();
    };

    loginPanel.prototype.setFullName = function (context, fullName) {
        context.driver.wait(until.elementLocated(elements.fullNameInput));
        return context.driver.findElement(elements.fullNameInput).sendKeys(fullName);
    };

    loginPanel.prototype.waitForManualCaptchaAndSubmit = function (context) {
        context.driver.wait(until.elementLocated(elements.loginInputEmailPasswordSubmitDisabled));
        context.driver.wait(until.elementLocated(elements.loginInputEmailPasswordSubmitEnabled));
        return context.driver.findElement(elements.loginInputEmailPasswordSubmitEnabled).click();
    };


};

module.exports = new loginPanel();