package com.mytest.actions;

import io.appium.java_client.MobileElement;

public interface IActionBot {

    boolean isElementClickable(MobileElement element);

    boolean isElementVisible(MobileElement element);

    boolean isElementGrayedOut(MobileElement element);

    boolean waitElementClickableAndTap(MobileElement element);
    
    boolean waitElementVisibleAndEnterText(MobileElement element, String text);
}