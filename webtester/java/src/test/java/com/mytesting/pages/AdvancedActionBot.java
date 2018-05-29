
package com.mytesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AdvancedActionBot extends ActionBot {

	public AdvancedActionBot(WebDriver driver) {
		super(driver);
	}

	public void waitToBeEnabledAndClick(WebElement element) {

		waitToBeEnabled(element);
		new Actions(driver).moveToElement(element).click().perform();
	}

	public void waitToBeEnabledAndEnterText(WebElement element, String text) {

		waitToBeEnabled(element);
		new Actions(driver).moveToElement(element).click().sendKeys(text).perform();
	}
	
}
