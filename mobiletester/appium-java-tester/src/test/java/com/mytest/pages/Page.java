package com.mytest.pages;

import java.time.Duration;

import com.mytest.actions.IActionBot;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AppiumFieldDecorator;

public abstract class Page {

    protected WebDriver driver;

    protected IActionBot actionBot;
    
    // Allows test to run on slow emulators
    protected int implicitTimeoutInSeconds = 10;

    public Page(WebDriver driver, IActionBot actionBot) {
        this.driver = driver;
        this.actionBot = actionBot;
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(implicitTimeoutInSeconds)),
            this);
    }

    public abstract boolean validatePage();

}