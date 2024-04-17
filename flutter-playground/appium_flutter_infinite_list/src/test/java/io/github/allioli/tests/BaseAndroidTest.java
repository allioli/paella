package io.github.allioli.tests;

import io.appium.java_client.android.AndroidDriver;
import io.github.allioli.drivers.AndroidDriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class BaseAndroidTest {

    protected AndroidDriverManager androidDriverManager;

    @BeforeClass(alwaysRun = true)
    public void setup() {
        this.androidDriverManager = new AndroidDriverManager().createAndroidDriver();
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        this.androidDriverManager.quitDriver();
    }

    protected AndroidDriver getDriver() {
        return this.androidDriverManager.getAndroidDriver();
    }
}
