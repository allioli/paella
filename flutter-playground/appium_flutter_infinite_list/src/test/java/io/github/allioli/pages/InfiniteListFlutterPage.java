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
    private final String itemIdentifier = "colour-item";
    private final String headerTitleIdentifier = "infinite-list-title";
    private final WebElement itemLocator;
    private final WebElement headerTitleLocator;

    public InfiniteListFlutterPage(final AppiumDriver driver) {
        this.finder = new FlutterFinder(driver);
        this.driver = driver;

        // Initialise page element locators
        Pattern listItemSemanticsLabelRegExp = Pattern.compile(itemIdentifier);
        itemLocator = finder.bySemanticsLabel(listItemSemanticsLabelRegExp);

        Pattern headerTitleSemanticsLabelRegExp = Pattern.compile(headerTitleIdentifier);
        headerTitleLocator = finder.bySemanticsLabel(headerTitleSemanticsLabelRegExp);
    }

    public InfiniteListFlutterPage waitForPagePresent() {
        waitForElement(itemLocator);
        return this;
    }
    public void defaultElementsVisible() {
        waitForElement(itemLocator);
        waitForElement(headerTitleLocator);
    }

    public void scrollToItemWithDescription(String description) {
        driver.executeScript("flutter:scrollUntilVisible", finder.byType("ListView"), new HashMap<String, Object>() {{
            put("item", finder.byText(description));
            put("dyScroll", -100);
            put("alignment", 0);
        }});
    }

    private WebElement waitForElement(WebElement finderLocator){
        return (WebElement) driver.executeScript("flutter:waitFor", finderLocator, 10000);
    }
}
