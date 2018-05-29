package com.mytesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private static final int defaultExplicitTimeoutInSeconds = 10;

    @FindBy(id = "loginConfig")
    private WebElement nextButton;

    @FindBy(id = "LoginControl_UserName")
    private WebElement loginUserNameTextInput;

    @FindBy(id = "LoginControl_Password")
    private WebElement loginPasswordTextInput;
    
    @FindBy(id = "LoginControl_LoginButton")
    private WebElement loginSubmitButton;
    
    @FindBy(id = "txtFailureMessage")
    private WebElement loginErrorMessage;
    
   // @FindBy(how=How.CSS, using="a[class^='helptext_forgot_password'")
    @FindBy(how=How.XPATH, using="//p[not(contains(@class,'forgot-password-mobile'))]/a[contains(@class,'helptext_forgot_password')]")
    private WebElement forgotPasswordLink; 

    public WebDriver driver;
    public static String url = "https://app.forcemanager.net";

    public static HomePage visitPage(WebDriver driver) {
        HomePage page = new HomePage(driver);
        page.visitPage();
        return page;
    }

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void visitPage() {
        this.driver.get(url);
    }

    public void enterLoginUserName(String userName) {
    	
    	WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> loginUserNameTextInput.isDisplayed());
		
		loginUserNameTextInput.sendKeys(userName);
    }
    
    public void enterLoginPassword(String pwd) {
    	
    	WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> loginPasswordTextInput.isDisplayed());
		
		loginPasswordTextInput.sendKeys(pwd);
    }
    
    public void clickLoginSubmitButton() {
    	
    	WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> loginSubmitButton.isDisplayed());
		
		loginSubmitButton.click();
    }
    
    public void waitForLoginErrorMessage() {
    	
    	WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> loginErrorMessage.isDisplayed());
    }
    
    public void clickNextButton() {
    	
    	WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> nextButton.isDisplayed());
		
		nextButton.click();
    }
    
    public void followForgotPasswordLink() {
    	
    	WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> forgotPasswordLink.isDisplayed());
		
		forgotPasswordLink.click();
    }

   

}
