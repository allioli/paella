package com.mytest.pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public abstract class Page {

    protected WebDriver driver;
    
    // Allows test to run on slow emulators
    protected int implicitTimeoutInSeconds = 10;

    protected int defaultExplicitTimeoutInSeconds = 5;

    public Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(implicitTimeoutInSeconds)),
            this);
    }

    protected boolean isElementVisibleAndEnabled(MobileElement element) {

        try {
            WebDriverWait wait = new WebDriverWait(driver, defaultExplicitTimeoutInSeconds);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            return element.isEnabled();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    protected boolean waitElementReadyAndTap(MobileElement element) {

        if (!isElementVisibleAndEnabled(element))
            return false;

        element.click();
        return true;
    }
 
}