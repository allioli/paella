package io.github.allioli.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

import java.time.Duration;
import java.util.List;


public abstract class AbstractPage implements IBasePage {

    protected WebDriver driver;

    protected AbstractPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        // We could use AjaxElementLocatorFactory to lazy load *all* elements, polling for presence with a timeout.
        // However, in this example the explicit await() method is in charge of polling the *key* elements for visibility instead.
        // PageFactory.initElements(new AjaxElementLocatorFactory(driver, 10), this);
        this.driver = driver;
    }

    public void await() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOfAllElements(this.identifyingElements()));
    }

    // List of WebElements that uniquely identify this page
    abstract List<WebElement> identifyingElements();
}

