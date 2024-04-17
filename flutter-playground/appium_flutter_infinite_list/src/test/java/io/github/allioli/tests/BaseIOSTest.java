package io.github.allioli.tests;

import io.appium.java_client.ios.IOSDriver;
import io.github.allioli.drivers.IOSDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseIOSTest {

    protected IOSDriverManager IOSDriverManager;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        this.IOSDriverManager = new IOSDriverManager().createIOSDriver();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.IOSDriverManager.quitDriver();
    }

    protected IOSDriver getDriver() {
        return this.IOSDriverManager.getIOSDriver();
    }
}
