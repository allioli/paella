package com.mytesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SignedInHomePage extends BasePage {
 
    @FindBy(how=How.XPATH, using="//div[contains(@class, 'avatar')]/..")
    private WebElement avatarButton; 
	
    @FindBy(how=How.CSS, using="[data-test='menu-logout']")
    private WebElement logoutMenuItem;
    
    @FindBy(how=How.CSS, using="a[class^=PushWarning__link]")
    private WebElement turnOnNotificationLink;
	
    @FindBy(how=How.XPATH, using="//div[contains(@class, 'Dialog__actions')]//span[text()='Log Out']/..")
    private WebElement logoutConfirmationButton; 
   
	public SignedInHomePage(WebDriver driver) {
		super(driver);
	}
	
	public void waitForAvatarButton() {
		
		actionBot.waitToBeDisplayed(avatarButton);
	}
	
	public void waitForPushNotificationLink() {
		
		actionBot.waitToBeDisplayed(turnOnNotificationLink);		
	}

	public void signOut() {
		 
		avatarButton.click();
		
		actionBot.waitToBeDisplayedAndClick(logoutMenuItem);
		
		actionBot.waitToBeDisplayedAndClick(logoutConfirmationButton);
		
	}
	
	


}