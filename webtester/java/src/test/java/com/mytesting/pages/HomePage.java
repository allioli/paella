package com.mytesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {
	
    private final String url = "https://us.letgo.com/en";
    
    @FindBy(how=How.CSS, using="[data-test='login']")
    private WebElement loginButton;   
	
    @FindBy(how=How.CSS, using="input[id*='Whatareyoulookingfor']")
    private WebElement searchInput; 
	
    @FindBy(how=How.CSS, using="button[class^='Header__clearSearch']")
    private WebElement clearSearchButton; 
	 
    @FindBy(how=How.CSS, using="[data-test='update-location-cancel']")
    private WebElement stayInUSLocationButton; 
   
	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	public void navigate() {
		
		 driver.get(url);
		 stayInUSLocationButton.click();
	}
	
	public void clickLoginButton() {
		
		actionBot.waitToBeDisplayedAndClick(loginButton);
	}
	
	public void clickClearSearchButton() {
		
		actionBot.waitToBeDisplayedAndClick(clearSearchButton);
	}
	
	public void setSearchText(String searchString) {
		
		actionBot.waitToBeDisplayedAndEnterText(searchInput, searchString + "\n");
	}
	
	public String getSearchInputText() {
		
		actionBot.waitToBeDisplayed(searchInput);
		return searchInput.getAttribute("value");	
	}
	
}