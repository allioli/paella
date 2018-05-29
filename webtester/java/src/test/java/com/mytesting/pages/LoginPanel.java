package com.mytesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPanel extends BasePage {
	
    @FindBy(how=How.CSS, using="[data-test='login-email']")
    private WebElement loginEmailButton;   
	
	@FindBy(how=How.CSS, using="[data-test='login-input-email']")
	private WebElement loginInputEmail;
	
	@FindBy(how=How.CSS, using="[data-test='login-input-password']")
	private WebElement loginInputPassword;
	
    @FindBy(how=How.CSS, using="[data-test='login-email-submit']:not([class*='Auth__button_disabled'])")
    private WebElement loginInputEmailPasswordSubmitEnabled; 
	 
    @FindBy(how=How.CSS, using="[data-test='login-email-submit'][class*='Auth__button_disabled']")
    private WebElement loginInputEmailPasswordSubmitDisabled;  
    
    @FindBy(how=How.CSS, using="button[class^='Auth__first-btn'")
    private WebElement signUpButton; 
    
    @FindBy(how=How.CSS, using="input[name='name'][type='text']")
    private WebElement fullNameInput; 
   
	public LoginPanel(WebDriver driver) {
		super(driver);
	}
	
	public void clickLoginEmailButton() {
		
		actionBot.waitToBeDisplayedAndClick(loginEmailButton);
	}
	
	public void clickSignUpButton() {
		
		actionBot.waitToBeDisplayedAndClick(signUpButton);
	}
	
	public void clickEmailPasswordSubmitButton() {
		
		actionBot.waitToBeDisplayedAndClick(loginInputEmailPasswordSubmitEnabled);
	}
	
	public void enterEmail(String email) {
		
		actionBot.waitToBeDisplayedAndEnterText(loginInputEmail, email);
	}

	public void enterPassword(String password) {
		
		actionBot.waitToBeDisplayedAndEnterText(loginInputPassword, password);
	}
	
	public void enterFullName(String fullName) {
		
		actionBot.waitToBeDisplayedAndEnterText(fullNameInput, fullName);
	}
	
	public void waitForManualCaptchaAndSubmit() {
		
		actionBot.waitToBeDisplayed(loginInputEmailPasswordSubmitDisabled);
		actionBot.waitToBeDisplayedAndClick(loginInputEmailPasswordSubmitEnabled);	
	}
}