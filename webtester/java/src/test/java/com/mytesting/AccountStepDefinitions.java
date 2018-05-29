package com.mytesting;

import com.google.inject.Inject;
import com.mytesting.pages.HomePage;
import com.mytesting.pages.LoginPanel;
import com.mytesting.pages.SignedInHomePage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class AccountStepDefinitions {

    public WebDriver driver;
    private HomePage homePage;
    private LoginPanel loginPanel;
    private SignedInHomePage signedInHomePage;
    
    @Inject
    private World world;
    
    public AccountStepDefinitions() {
        driver = Hooks.driver;
        homePage = PageFactory.initElements(driver, HomePage.class);
        loginPanel = PageFactory.initElements(driver, LoginPanel.class);
        signedInHomePage = PageFactory.initElements(driver, SignedInHomePage.class);
    }

    @Given("^I open letgo home page$")
    public void i_open_letgo_landing_page() {
        
    	homePage.navigate();
    }
    
    @When("^I create a user account with email and password$")
    public void i_create_user_account_with_email_password() {
    	
    	homePage.clickLoginButton();
    	loginPanel.clickSignUpButton();
    	loginPanel.enterEmail(world.getLoginEmail());
    	loginPanel.enterPassword(world.getLoginPassword());
    	loginPanel.clickEmailPasswordSubmitButton();
    	loginPanel.enterFullName("John Doe");
    	loginPanel.waitForManualCaptchaAndSubmit();
    	
    }
    
    @When("^I sign in with email and password$")
    public void i_sign_in_with_email_password() {
    	
    	homePage.clickLoginButton();
    	loginPanel.clickLoginEmailButton();
    	loginPanel.enterEmail(world.getLoginEmail());
    	loginPanel.enterPassword(world.getLoginPassword());
    	loginPanel.clickEmailPasswordSubmitButton();
    	
    }
    
    @When("^I sign out$")
    public void i_sign_out() {
    	
    	signedInHomePage.signOut();	
    }
    
    @Then("^I am signed in as a registered user$")
    public void i_am_signed_in_as_a_registeread_user() {
    	
    	signedInHomePage.waitForAvatarButton();
    	signedInHomePage.waitForPushNotificationLink();
    }
    
   
    
     
}