package io.github.allioli.drivers;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AndroidDriverManager {

    private static final ThreadLocal<AndroidDriver> DRIVER = new ThreadLocal<>();
    
    public AndroidDriverManager createAndroidDriver() {
        try {
            setDriver(new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), setCapabilities()));
            setupBrowserTimeouts();

        } catch (MalformedURLException e) {
            throw new Error("Error while creating Android Driver Session");
        }
        return this;
    }

    public AndroidDriver getAndroidDriver() {
        return AndroidDriverManager.DRIVER.get();
    }

    public void quitDriver() {
        if (null != DRIVER.get()) {
            getAndroidDriver().quit();
            DRIVER.remove();
        }
    }

    private DesiredCapabilities setCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        File appDir = new File("apps");
        File app = new File(appDir, "app-debug.apk");
        capabilities.setCapability("platformName", "android");
        capabilities.setCapability("platformVersion", "14.0");
        capabilities.setCapability("deviceName", "pixel6-a14");
        capabilities.setCapability("automationName", "flutter");
        capabilities.setCapability("printPageSourceOnFindFailure", true);
        capabilities.setCapability("disableIdLocatorAutocompletion", true);
        capabilities.setCapability("app", app.getAbsolutePath());
        return capabilities;
    }
    private void setDriver(final AndroidDriver driver) {
        AndroidDriverManager.DRIVER.set(driver);
    }

    private void setupBrowserTimeouts() {
        getAndroidDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
