package com.mytest.pages;

import com.mytest.actions.IActionBot;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

public class MainMenu extends Page {


    @iOSFindBy(accessibility = "Preference")
    @AndroidFindBy(accessibility = "Preference")
    public MobileElement preferenceButton;


    @iOSFindBy(accessibility = "Accessibility")
    @AndroidFindBy(accessibility = "Accessibility")
    public MobileElement accessibilityButton;

    @iOSFindBy(accessibility = "Animation")
    @AndroidFindBy(accessibility = "Animation")
    public MobileElement animationButton;

    @iOSFindBy(accessibility = "App")
    @AndroidFindBy(accessibility = "App")
    public MobileElement appButton;

    @iOSFindBy(accessibility = "Content")
    @AndroidFindBy(accessibility = "Content")
    public MobileElement contentButton;

    @iOSFindBy(accessibility = "Graphics")
    @AndroidFindBy(accessibility = "Graphics")
    public MobileElement graphicsButton;

    @iOSFindBy(accessibility = "Media")
    @AndroidFindBy(accessibility = "Media")
    public MobileElement mediaButton;

    @iOSFindBy(accessibility = "NFC")
    @AndroidFindBy(accessibility = "NFC")
    public MobileElement nfcButton;

    @iOSFindBy(accessibility = "OS")
    @AndroidFindBy(accessibility = "OS")
    public MobileElement osButton;

    @iOSFindBy(accessibility = "Text")
    @AndroidFindBy(accessibility = "Text")
    public MobileElement textButton;

    @iOSFindBy(accessibility = "Views")
    @AndroidFindBy(accessibility = "Views")
    public MobileElement viewsButton;

    public MainMenu(WebDriver driver, IActionBot actionBot) {
        super(driver, actionBot);
    }

    public boolean tapPreferenceButton(){

        return actionBot.waitElementClickableAndTap(preferenceButton);
    }

    public boolean validatePage(){

        if( actionBot.isElementClickable(viewsButton) && 
            actionBot.isElementClickable(textButton) &&
            actionBot.isElementClickable(osButton) &&
            actionBot.isElementClickable(nfcButton) && 
            actionBot.isElementClickable(mediaButton) &&
            actionBot.isElementClickable(graphicsButton) &&
            actionBot.isElementClickable(contentButton) &&
            actionBot.isElementClickable(appButton) &&
            actionBot.isElementClickable(animationButton) &&
            actionBot.isElementClickable(accessibilityButton) &&
            actionBot.isElementClickable(preferenceButton))

            return true;

        return false;

    }
}