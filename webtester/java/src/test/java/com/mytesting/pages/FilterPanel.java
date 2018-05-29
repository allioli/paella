
package com.mytesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;


public class FilterPanel extends BasePage {
 
    @FindBy(how=How.CSS, using="input[name='min_price']")
    private WebElement minPriceInput; 

	@FindBy(how=How.CSS, using="input[name='max_price']")
	private WebElement maxPriceInput; 
	
    @FindBy(how=How.CSS, using="input[value='price_asc']")
    private WebElement sortByPriceAscInputRadio; 
    
    @FindBy(how=How.CSS, using="input[value='price_desc']")
    private WebElement sortByPriceDescInputRadio; 
    
	public FilterPanel(WebDriver driver) {
		super(driver);
	}
	
	public void setMaxPrice(String maxPrice) {
		
		advancedActionBot.waitToBeEnabledAndEnterText(maxPriceInput, maxPrice);
	}
	
	public void setMinPrice(String minPrice) {
		
		advancedActionBot.waitToBeEnabledAndEnterText(minPriceInput, minPrice);
	}
	
	public void clickSortByPriceAscInputRadio() {
		
		advancedActionBot.waitToBeEnabledAndClick(sortByPriceAscInputRadio);
	}
	
	public void clickSortByPriceDescInputRadio() {
		
		advancedActionBot.waitToBeEnabledAndClick(sortByPriceDescInputRadio);
	}
	
	


}