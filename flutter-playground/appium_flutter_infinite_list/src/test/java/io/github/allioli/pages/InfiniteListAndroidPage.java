package io.github.allioli.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class InfiniteListAndroidPage {

    private final WebDriverWait wait;

    @AndroidFindBy(id = "infinite-list-title")
    public WebElement headerTitle;

    @AndroidFindBy(xpath = "//*[@resource-id='colour-item']/android.view.View")
    public List<WebElement> itemDescriptionElements;

    public InfiniteListAndroidPage(final AppiumDriver driver) {
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(5)),
                this);
    }

    public InfiniteListAndroidPage waitForPagePresent() {
        wait.until(ExpectedConditions.visibilityOf(headerTitle));
        return this;
    }

    public Boolean defaultElementsVisible() {
        return (headerTitle.isDisplayed() && itemDescriptionElements.size() > 0);
    }

    public int getNumberOfItems() {
        return itemDescriptionElements.size();
    }

    public List<String> getVisibleItemDescriptions(int maxItems) {
        List<String> itemDescriptions = new ArrayList<>();
        for (int i = 0; i < maxItems; i++) {
            WebElement itemDescription = itemDescriptionElements.get(i);
            wait.until(ExpectedConditions.visibilityOf(itemDescription));
            String descriptionText = itemDescription.getAttribute("content-desc");
            itemDescriptions.add(descriptionText);
        }
        return itemDescriptions;
    }
}
