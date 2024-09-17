package io.github.allioli.drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class MyDriverManager {
    private static final ThreadLocal<WebDriver> DRIVER = new ThreadLocal<>();

    public WebDriver createWebDriver() {
        System.out.println("Called createWebDriver");
        WebDriver driver = new ChromeDriver();
        driver.manage().deleteAllCookies();
        //driver.manage().window().maximize();
        DRIVER.set(driver);
        return driver;
    }

    public WebDriver getWebDriver() {
        if (null != DRIVER.get()) {
            return DRIVER.get();
        } else {
            return createWebDriver();
        }
    }

    public void quitWebDriver() {
        if (null != DRIVER.get()) {
            getWebDriver().quit();
            DRIVER.remove();
        }
    }
}
