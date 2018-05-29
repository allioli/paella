
require('chromedriver')
const seleniumWebdriver = require('selenium-webdriver');
var utils = require('..\\support\\utils');
const {defineSupportCode} = require('cucumber');

function CustomWorld() {
    this.driver = new seleniumWebdriver.Builder()
        .forBrowser('chrome')
        .build();

    this.loginCredentials = new Map();

    this.getLoginEmail = function() {

        if(this.loginCredentials.has("email")) {
            return this.loginCredentials.get("email");
        }
        else {
            const email = utils.generateTestEmail();
            this.loginCredentials.set("email", email);
            return email;
        }
    };

    this.getLoginPassword = function() {

        if(this.loginCredentials.has("password")) {
            return this.loginCredentials.get("password");
        }
        else {
            const password = utils.generatePassword();
            this.loginCredentials.set("password", password);
            return password;
        }
    };

}

defineSupportCode(function({setWorldConstructor}) {
    setWorldConstructor(CustomWorld)
})