package io.github.allioli.drivers;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class IOSDriverManager {
    private static final ThreadLocal<IOSDriver> DRIVER = new ThreadLocal<>();

    public IOSDriverManager createIOSDriver() {
        try {
            setDriver(new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), setCapabilities()));
            setupBrowserTimeouts();

        } catch (MalformedURLException e) {
            throw new Error("Error while creating Android Driver Session");
        }
        return this;
    }

    public IOSDriver getIOSDriver() {
        return IOSDriverManager.DRIVER.get();
    }

    public void quitDriver() {
        if (null != DRIVER.get()) {
            getIOSDriver().quit();
            DRIVER.remove();
        }
    }

    private DesiredCapabilities setCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        File appDir = new File("apps");
        File app = new File(appDir, "Runner.app");
        capabilities.setCapability("platformName", "ios");
        capabilities.setCapability("version", "17");
        capabilities.setCapability("deviceName", "iPhone 15 Pro");
        capabilities.setCapability("automationName", "flutter");
        capabilities.setCapability("udid", "12B6A935-923D-454E-BBB7-C7B4945B2F87");
        capabilities.setCapability("printPageSourceOnFindFailure", true);
        capabilities.setCapability("app", app.getAbsolutePath());
        return capabilities;
    }
    private void setDriver(final IOSDriver driver) {
        IOSDriverManager.DRIVER.set(driver);
    }

    private void setupBrowserTimeouts() {
        getIOSDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }
}
