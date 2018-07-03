package com.mytest.pages;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

public class MainMenu extends Page {


    @iOSFindBy(accessibility = "Preference")
    @AndroidFindBy(accessibility = "Preference")
    public MobileElement preferenceButton;

    public MainMenu(WebDriver driver) {
        super(driver);
    }

    public boolean tapPreferenceButton(){

        return checkElemenClickableAndTap(preferenceButton);
    }
}