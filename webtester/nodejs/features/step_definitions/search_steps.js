const {defineSupportCode} = require('cucumber');
const homePage = require('..\\page_objects\\home');
const searchResults = require('..\\page_objects\\search_results');
const filterPanel = require('..\\page_objects\\filter_panel');

const chai = require('chai');
const expect = chai.expect;
const chaiAsPromised = require('chai-as-promised');
chai.use(chaiAsPromised);

defineSupportCode(function ({Given, When, Then, setDefaultTimeout}) {
    setDefaultTimeout(30 * 1000);

    When('I search for {string}', function (searchString) {
        return homePage.setSearchText(this, searchString);
    });

    When('I delete search query', function () {
        return homePage.clickClearSearchButton(this);
    });

    When('I select price range between {int} and {int}', function (minPrice, maxPrice) {

        filterPanel.setMinPrice(this, minPrice.toString());
        return filterPanel.setMaxPrice(this, maxPrice.toString());
    });

    When('I sort by price higher to lower', function () {
        return filterPanel.clickSortByPriceDescInputRadio(this);
    });

    When('I sort by price lower to higher', function () {
        return filterPanel.clickSortByPriceAscInputRadio(this);
    });

    When('I open the first item page', function () {
        return searchResults.clickFirstItemLink(this);
    });

    Then('The first item price is {string}', function (maxAllowedPriceString) {

        return Promise.all([
            expect(searchResults.getProductPrice(this)).to.eventually.equal(maxAllowedPriceString),
            searchResults.clickProductPageHomeButton(this)
        ]);


    });

    Then('I should see at least one {string} item', function (keyWord) {

        return Promise.all([
            expect(searchResults.getRelevantItemTitle(this, keyWord)).to.eventually.contain(keyWord)
        ]);
    });


    Then('I should not see a {string} item', function (unexpectedKeyWord) {
        return Promise.all([
            expect(searchResults.getFirstResultItemTitle(this)).to.eventually.not.contain(unexpectedKeyWord)
        ]);
    });

    Then('I should see an empty search box', function () {
        return Promise.all([
            expect(homePage.getSearchText(this)).to.eventually.equal('')
        ]);
    });

    Then('I should see no results found message', function () {
        return searchResults.waitForNoItemsFoundMessageVisible(this);
    });

    Then('I should see no results', function () {
        return searchResults.waitForEmptyResult(this);
    });

});