package com.mytesting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.mytesting.pages.FilterPanel;
import com.mytesting.pages.HomePage;
import com.mytesting.pages.SearchResults;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;


public class SearchStepDefinitions {
	
	public WebDriver driver;
    private HomePage homePage;
    private SearchResults searchResults;
    private FilterPanel filterPanel;
    
    public SearchStepDefinitions() {
        driver = Hooks.driver;
        homePage = PageFactory.initElements(driver, HomePage.class);
        searchResults = PageFactory.initElements(driver, SearchResults.class);
        filterPanel = PageFactory.initElements(driver, FilterPanel.class);
    }
    
    @When("^I search for (.*)$")
    public void i_search_for(String query) {
        
    	homePage.setSearchText(query);
    }
    
    @When("^I delete search query$")
    public void i_delete_search_query() {
        
    	homePage.clickClearSearchButton();
    }
    
    @When("^I select price range between (\\d+) and (\\d+)$")
    public void i_select_price_range(int minPrice, int maxPrice) {
    	
    	filterPanel.setMinPrice(Integer.toString(minPrice));
    	filterPanel.setMaxPrice(Integer.toString(maxPrice));
    }
    
    @When("^I sort by price higher to lower$")
    public void i_sort_by_price_higher_to_lower() {
    	
    	filterPanel.clickSortByPriceDescInputRadio();
    }
    
    @When("^I sort by price lower to higher$")
    public void i_sort_by_price_lower_to_higher() {
    	
    	filterPanel.clickSortByPriceAscInputRadio();
    }
    
    @Then("^The first item price is (.+)$")
    public void the_first_item_price_is(String expectedPriceString) {
    	
    	searchResults.clickFirstItemLink();
    	String itemPrice = searchResults.getItemPrice();
    	
    	assertEquals(expectedPriceString, itemPrice);
    	
    	driver.navigate().back();
    }
    
    
    
    @Then("^I should not see a (.+) item$")
    public void i_should_not_see_item(String unexpectedItemTitle) {
    	
    	short numItems = searchResults.getNumberOfItemsWithTitle(unexpectedItemTitle);
        
    	assertEquals("Number of Items with title '" + unexpectedItemTitle + "' (" + numItems + ") is not 0", 
    			0, numItems);
    }
    
    @Then("^I should see at least one (.+) item$")
    public void i_should_see_at_least_one_item(String expectedItemTitle) {
    	
    	short numItems = searchResults.getNumberOfItemsWithTitle(expectedItemTitle);
        
    	assertTrue("Number of Items with title '" + expectedItemTitle + "' (" + numItems + ") is less than 1", 
    			numItems > 0);
    }
    
    @Then("^I should see an empty search box$")
    public void i_should_see_an_empty_search_box() {
    	
    	 assertEquals("Search input value is not empty", "", homePage.getSearchInputText());
    }
    
    @Then("^I should see no results$")
    public void i_should_see_no_results() {
    	 
    	searchResults.waitForNoItemsFoundMessageVisible();
    	searchResults.waitForEmptyResultsContainer();
    }

}
