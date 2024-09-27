const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    baseUrl:'https://www.lambdatest.com/selenium-playground',
    setupNodeEvents(on, config) {
      // implement node event listeners here
    },
  },
});
