package com.mytest.pages;

import org.openqa.selenium.WebDriver;

import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSFindBy;

public class Preferences extends Page {


    @iOSFindBy(accessibility = "Preference")
    @AndroidFindBy(xpath = "//android.widget.TextView[@content-desc='3. Preference dependencies']")
    public MobileElement preferenceDependenciesButton;

    public Preferences(WebDriver driver) {
        super(driver);
    }

    public boolean tapPreferenceDependenciesButton(){

        return checkElemenClickableAndTap(preferenceDependenciesButton);
    }
}