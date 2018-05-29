package com.mytesting;

import com.mytesting.pages.AccountsPage;
import com.mytesting.pages.HomePage;
import com.mytesting.pages.PasswordRecoveryPage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

public class LoginTests  {

    private static WebDriver driver;
    
    private static final String validUserName = "xavi.blue81@gmail.com";
    private static final String validPassword = "managerforce";
    
    @BeforeMethod
    public void setUp() {
        System.out.println("Called openBrowser");
        driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
    }
    
    
    @AfterMethod
    public void tearDown() {
    	driver.quit();
    }
    
    
    @Test
    public void testLoginWithCorrectUsernameAndCorrectPassword() {
    	
    	 HomePage homePage = HomePage.visitPage(driver);
    	 homePage.enterLoginUserName(validUserName);
    	 homePage.clickNextButton();
    	 homePage.enterLoginPassword(validPassword);
    	 homePage.clickLoginSubmitButton();	 
    	 
    	 AccountsPage accountsPage = AccountsPage.visitPage(driver);
    	 accountsPage.waitForAccountsTitle();
    }
    
    @Test
    public void testLoginWithWrongUsernameAndCorrectPassword() {
    	
    	 HomePage page = HomePage.visitPage(driver);
    	 page.enterLoginUserName("wrong-value");
    	 page.clickNextButton();
    	 page.enterLoginPassword(validPassword);
    	 page.clickLoginSubmitButton();	
    	 page.waitForLoginErrorMessage();
    }
    
    @Test
    public void testLoginWithWrongUsernameAndWrongPassword() {
    	
    	 HomePage page = HomePage.visitPage(driver);
    	 page.enterLoginUserName("wrong-value");
    	 page.clickNextButton();
    	 page.enterLoginPassword("wrong-value");
    	 page.clickLoginSubmitButton();	
    	 page.waitForLoginErrorMessage();
    }
    
    @Test
    public void testLoginWithCorrectUsernameAndWrongPassword() {
    	
    	 HomePage page = HomePage.visitPage(driver);
    	 page.enterLoginUserName(validUserName);
    	 page.clickNextButton();
    	 page.enterLoginPassword("wrong-value");
    	 page.clickLoginSubmitButton();	
    	 page.waitForLoginErrorMessage();
    	
    }
    
    @Test
    public void testPasswordRecovery() {
    	
    	 HomePage homePage = HomePage.visitPage(driver);
    	 homePage.enterLoginUserName(validUserName);
    	 homePage.clickNextButton();
    	 homePage.enterLoginPassword("wrong-value");
    	 homePage.clickLoginSubmitButton();	
    	 homePage.waitForLoginErrorMessage();
    	 homePage.followForgotPasswordLink();
    	 homePage.enterLoginUserName(validUserName);
    	 
    	 PasswordRecoveryPage recoPage = PasswordRecoveryPage.visitPage(driver);
    	 recoPage.enterUserName(validUserName);
    	 recoPage.clickNextButton();
    	 
    }
    
    
    
     
    
        

     
}
