package com.mytest.pages;

import com.mytest.actions.IActionBot;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

public class Preferences extends Page {


    @iOSFindBy(accessibility = "3. Preference dependencies")
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='3. Preference dependencies']")
    public MobileElement preferenceDependenciesButton;

    @iOSFindBy(accessibility = "2. Launching preferences")
    @AndroidFindBy(accessibility = "2. Launching preferences")
    public MobileElement launchingPreferencesButton;

    public Preferences(WebDriver driver, IActionBot actionBot) {
        super(driver, actionBot);
    }

    public boolean validatePage(){

        if( actionBot.isElementClickable(preferenceDependenciesButton) &&
            actionBot.isElementClickable(launchingPreferencesButton))

            return true;

        return false;

    }

    public boolean tapPreferenceDependenciesButton(){

        return actionBot.waitElementClickableAndTap(preferenceDependenciesButton);
    }    
}