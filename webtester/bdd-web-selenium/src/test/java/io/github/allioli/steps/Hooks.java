package io.github.allioli.steps;

import io.github.allioli.drivers.MyDriverManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.java.Scenario;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    public static MyDriverManager driverManager = new MyDriverManager();

    @After
    public void embedScreenshot(Scenario scenario) {
        WebDriver driver = driverManager.getWebDriver();
        if (scenario.isFailed()) {
            try {
                byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Current Page URL is " + driver.getCurrentUrl());
            } catch (WebDriverException somePlatformsDontSupportScreenshots) {
                System.err.println(somePlatformsDontSupportScreenshots.getMessage());
            }
        }
        driverManager.quitWebDriver();
    }
}
