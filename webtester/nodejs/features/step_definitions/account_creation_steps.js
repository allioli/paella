
const {defineSupportCode} = require('cucumber');
const homePage = require('..\\page_objects\\home');
const loginPanel = require('..\\page_objects\\login_panel');


defineSupportCode(function({Given, When, Then, setDefaultTimeout}) {
    setDefaultTimeout(30 * 1000);

    Given('I am on the letgo landing page', function() {
        this.driver.manage().window().maximize();
        return homePage.navigate(this);
    });

    When('I navigate to the account creation form', function () {
        homePage.clickLoginButton(this);
        return loginPanel.clickSignUpButton(this);
    });

    When('I enter email and password', function () {

        loginPanel.setEmail(this, this.getLoginEmail());
        loginPanel.setPassword(this, this.getLoginPassword());
        return loginPanel.clickEmailPasswordSubmitButton(this);
    });

    When('I enter user full name {string}', function (fullName) {
        return loginPanel.setFullName(this, fullName);
    });

    When('I demonstrate I am not a robot', function () {
        return loginPanel.waitForManualCaptchaAndSubmit(this);
    });

    When('I log out', function () {
        homePage.clickAvatarButton(this);
        homePage.clickLogoutMenuItem(this);
        return homePage.clickLogoutConfirmationButton(this);
    });

    When('I navigate to the login form', function () {
        homePage.clickLoginButton(this);
        return loginPanel.clickEmailLoginButton(this);
    });

    Then('I am logged in as a registered user with avatar icon {string}', function (avatarIconLetter) {
        homePage.waitForCorrectAvatarIcon(this, avatarIconLetter);
        return homePage.waitForPushNotificationLink(this);
    });

});