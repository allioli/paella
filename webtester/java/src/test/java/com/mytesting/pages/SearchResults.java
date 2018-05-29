package com.mytesting.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchResults extends BasePage {
 
    @FindBy(how=How.CSS, using="h4")
    private List<WebElement> allItems;

	@FindBy(how=How.XPATH, using="//h4/../..")
	private List<WebElement> allItemLinks;
	
    @FindBy(how=How.CSS, using="div[class^='ProductListContainer__noProducts']")
    private WebElement noItemsFoundMessage;
    
    @FindBy(how=How.CSS, using="div[class^='ProductListContainer__noContent']")
    private WebElement noItemsContainer;
	
    @FindBy(how=How.XPATH, using="//div[contains(@class,'Box product-page__user-price')]/h3/span")
    private WebElement productPrice; 
    
    @FindBy(how=How.CSS, using="span[class^='product-page__backButton']")
    private WebElement productPageHomeButton;
   
	public SearchResults(WebDriver driver) {
		super(driver);
	}
	
	public short getNumberOfItemsWithTitle(String title) {
		
		if(allItems.size() == 0)
			return 0;
		
		short numItemsWithTitle = 0;
		
		for( WebElement element: allItems) {
			
			actionBot.waitToBeDisplayed(element);
			
			if(element.getText().contains(title))
				numItemsWithTitle ++;		
		}
		
		return numItemsWithTitle;
	}
	
	public void clickFirstItemLink() {
		
		WebElement firstItemLink = allItemLinks.listIterator().next();
		advancedActionBot.waitToBeEnabledAndClick(firstItemLink);
	}
	
	public String getItemPrice() {
		
		actionBot.waitToBeEnabled(productPrice);
		return productPrice.getText();
		
	}
	
	public void waitForNoItemsFoundMessageVisible() {
		
		actionBot.waitToBeDisplayed(noItemsFoundMessage);
	}
	
	public void waitForEmptyResultsContainer() {
		
		actionBot.waitToBeDisplayed(noItemsContainer);
	}

}
