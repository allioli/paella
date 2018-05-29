package com.mytesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AccountsPage {

    private static final int defaultExplicitTimeoutInSeconds = 20;

    @FindBy(how=How.CSS, using="span[class='title_accounts']")
    private WebElement accountsTitle;

    public WebDriver driver;
   
    public static AccountsPage visitPage(WebDriver driver) {
        AccountsPage page = new AccountsPage(driver);
        return page;
    }

    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
    public void waitForAccountsTitle() {
    	
    	WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> this.accountsTitle.isDisplayed());
    }

}

