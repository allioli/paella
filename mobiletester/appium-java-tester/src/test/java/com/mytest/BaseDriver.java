package com.mytest;

import java.io.File;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServerHasNotBeenStartedLocallyException;


public class BaseDriver {
    public AppiumDriver<MobileElement> driver;
    private static AppiumDriverLocalService service;

    protected void startSessionWithAndroidCapabilities() throws Exception {

        File appDir = new File("apps");
        File app = new File(appDir, "ApiDemos-debug.apk");
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformVersion", "8.0.0");
        capabilities.setCapability("deviceName", "A3-XAVI");
        capabilities.setCapability("app", app.getAbsolutePath());
        driver = new AndroidDriver<>(new URL("http://127.0.0.1:4723/wd/hub"), capabilities);
    }

    protected void startLocalApiumServer() throws Exception {

        service = AppiumDriverLocalService.buildDefaultService();
        service.start();

        if (service == null || !service.isRunning()) {
            throw new AppiumServerHasNotBeenStartedLocallyException(
                    "An appium server node is not started!");
        }
    }

    @Before
    public void setUp() throws Exception {

        startLocalApiumServer();
        startSessionWithAndroidCapabilities();     
    }

    @After
    public void tearDown() throws Exception {
        if (driver != null) {
            driver.quit();
        }
        if (service != null) {
            service.stop();
        }
    }

}
