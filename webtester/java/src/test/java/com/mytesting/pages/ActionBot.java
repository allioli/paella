package com.mytesting.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ActionBot {

	protected final WebDriver driver;
	protected final int defaultExplicitTimeoutInSeconds = 10;

	public ActionBot(WebDriver driver) {
		this.driver = driver;
	}

	public void waitToBeDisplayed(WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> element.isDisplayed());
	}

	public void waitToBeEnabled(WebElement element) {

		WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
		wait.until(d -> element.isEnabled());
	}

	public void waitToBeDisplayedAndClick(WebElement element) {

		waitToBeDisplayed(element);
		element.click();
	}

	public void waitToBeEnabledAndClick(WebElement element) {

		waitToBeEnabled(element);
		element.click();
	}
	
	protected void waitToBeDisplayedAndEnterText(WebElement element, String text) {

		waitToBeDisplayed(element);
		element.sendKeys(text);
	}


	public void waitToBeEnabledAndEnterText(WebElement element, String text) {

		waitToBeEnabled(element);
		element.sendKeys(text);
	}
	
}
