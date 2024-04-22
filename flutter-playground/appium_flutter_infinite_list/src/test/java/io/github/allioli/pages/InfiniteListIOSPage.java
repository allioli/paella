package io.github.allioli.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InfiniteListIOSPage {

    private final WebDriverWait wait;

    @iOSXCUITFindBy(xpath = "//*[@name='colour-item']/following-sibling::XCUIElementTypeStaticText")
    public List<WebElement> itemDescriptionElements;

    @iOSXCUITFindBy(xpath = "//*[@name='colour-item']")
    public WebElement itemInList;

    public InfiniteListIOSPage(final AppiumDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)),
                this);
    }

    public InfiniteListIOSPage waitForPagePresent() {
        wait.until(ExpectedConditions.visibilityOf(itemInList));
        return this;
    }

    public Boolean defaultElementsVisible() {
        return itemDescriptionElements.size() > 0;
    }

    public int getNumberOfItems() {
        return itemDescriptionElements.size();
    }

    public List<String> getVisibleItemDescriptions(int maxItems) {
        List<String> itemDescriptions = new ArrayList<>();
        for (int i = 0; i < maxItems; i++) {
            WebElement itemDescription = itemDescriptionElements.get(i);
            wait.until(ExpectedConditions.visibilityOf(itemDescription));
            String descriptionText = itemDescription.getText();
            itemDescriptions.add(descriptionText);
        }
        return itemDescriptions;
    }
}
