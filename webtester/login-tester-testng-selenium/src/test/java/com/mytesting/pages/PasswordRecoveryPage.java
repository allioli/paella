
package com.mytesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PasswordRecoveryPage {

    private static final int defaultExplicitTimeoutInSeconds = 15;

    @FindBy(id = "recoverPasswordEmail")
    private WebElement userNameTextInput;
    
    @FindBy(id = "sendEmail")
    private WebElement nextButton;

    public WebDriver driver;
   
    public static PasswordRecoveryPage visitPage(WebDriver driver) {
    	PasswordRecoveryPage page = new PasswordRecoveryPage(driver);
        return page;
    }

    public PasswordRecoveryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void enterUserName(String userName) {
    	
    	WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> userNameTextInput.isDisplayed());
		
		userNameTextInput.sendKeys(userName);
    }
    
    public void clickNextButton() {
    	
    	WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> nextButton.isDisplayed());
		
		nextButton.click();
    }

}

