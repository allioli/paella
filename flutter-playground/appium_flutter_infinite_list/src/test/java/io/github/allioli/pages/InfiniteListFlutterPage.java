package io.github.allioli.pages;

import io.appium.java_client.AppiumDriver;
import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.HashMap;
import java.util.regex.Pattern;

public class InfiniteListFlutterPage {

    private final FlutterFinder finder;
    private final AppiumDriver driver;
    private final String itemTitleKey = "item-title";
    private final String itemPriceKey = "item-price";
    private final String itemIdentifier = "colour-item";
    private final FlutterElement itemTitleLocator;
    private final FlutterElement itemPriceLocator;
    private final FlutterElement itemLocator;

    public InfiniteListFlutterPage(final AppiumDriver driver) {
        this.finder = new FlutterFinder(driver);
        this.driver = driver;

        // Initialise page element locators
        itemTitleLocator = finder.byValueKey(itemTitleKey);
        itemPriceLocator = finder.byValueKey(itemPriceKey);

        Pattern listItemSemanticsLabelRegExp = Pattern.compile(itemIdentifier);
        itemLocator = finder.bySemanticsLabel(listItemSemanticsLabelRegExp);
    }

    public InfiniteListFlutterPage waitForPagePresent() {
        waitForElement(itemLocator);
        return this;
    }
    public void defaultElementsVisible() {
        waitForElement(itemLocator);
        waitForElement(itemPriceLocator);
        waitForElement(itemTitleLocator);
    }

    public void scrollToItemWithDescription(String description) {
        driver.executeScript("flutter:scrollUntilVisible", finder.byType("ListView"), new HashMap<String, Object>() {{
            put("item", finder.byText(description));
            put("dyScroll", -100);
            put("alignment", 0);
        }});
    }

    private WebElement waitForElement(FlutterElement finderLocator){
        return (WebElement) driver.executeScript("flutter:waitFor", finderLocator, 10000);
    }
}
