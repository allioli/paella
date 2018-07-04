package com.mytest.pages;

import com.mytest.actions.IActionBot;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

public class PreferenceDependencies extends Page {

    @iOSFindBy(id = "checkbox")
    @AndroidFindBy(id = "android:id/checkbox")
    public MobileElement wifiCheckbox;

    // Example of fragile xpath locator. Try to avoid non-unique identifiers in your apps
    @AndroidFindBy(xpath = "(//android.widget.RelativeLayout)[2]")
    public MobileElement wifiSettingsButton;

    @AndroidFindBy(id = "android:id/edit")
    public MobileElement wifiSettingsTextInput;

    @AndroidFindBy(id = "android:id/button1")
    public MobileElement wifiSettingsSubmitButton;

    public PreferenceDependencies(WebDriver driver, IActionBot actionBot) {
        super(driver, actionBot);
    }

    public boolean validatePage(){

        if( actionBot.isElementVisible(wifiCheckbox) &&
            actionBot.isElementGrayedOut(wifiSettingsButton))

            return true;

        return false;

    }

    public boolean checkWifiCheckbox(){
        return actionBot.waitElementClickableAndTap(wifiCheckbox);
    }

    public boolean tapWifiSettingsButton(){
        return actionBot.waitElementClickableAndTap(wifiSettingsButton);
    }
    
    public boolean enterWifiSettings(String text){
        return actionBot.waitElementVisibleAndEnterText(wifiSettingsTextInput, text);
    }

    public boolean tapWifiSettingsSubmitButton(){
        return actionBot.waitElementClickableAndTap(wifiSettingsSubmitButton);
    }

    public String getWifiSettings(){
        
        if (!actionBot.isElementVisible(wifiSettingsTextInput))
            return "";
        
        return wifiSettingsTextInput.getText();
    }
    
    

    
}