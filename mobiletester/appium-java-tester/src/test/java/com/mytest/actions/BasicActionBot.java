package com.mytest.actions;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileElement;

public class BasicActionBot implements IActionBot {

    private WebDriver driver;
    private final int defaultExplicitTimeoutInSeconds = 5;

    public BasicActionBot(WebDriver driver){
        this.driver = driver;
    }

    public boolean isElementClickable(MobileElement element) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean isElementVisible(MobileElement element) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
            wait.until(ExpectedConditions.visibilityOf(element));
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean waitElementClickableAndTap(MobileElement element) {

        if (!isElementClickable(element))
            return false;

        element.click();
        return true;
    }

    public boolean isElementGrayedOut(MobileElement element) {

        if (!isElementVisible(element))
            return false;

        return !element.isEnabled();
    }

    public boolean waitElementVisibleAndEnterText(MobileElement element, String text){

        if (!isElementVisible(element))
            return false;

        element.clear();
        element.sendKeys(text);

        return true;
    }
 
}