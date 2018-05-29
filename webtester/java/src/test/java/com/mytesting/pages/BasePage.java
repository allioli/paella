package com.mytesting.pages;

import org.openqa.selenium.WebDriver;

public class BasePage {
	
  protected WebDriver driver;
  protected ActionBot actionBot;
  protected AdvancedActionBot advancedActionBot;
  
  public BasePage(WebDriver driver) {
    this.driver = driver;
    this.actionBot = new ActionBot(driver);
    this.advancedActionBot = new AdvancedActionBot(driver);
  }
  
}
