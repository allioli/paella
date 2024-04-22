package io.github.allioli.tests;

import io.github.ashwith.flutter.FlutterElement;
import io.github.ashwith.flutter.FlutterFinder;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

public class FlutterInfiniteListAndroidTests extends BaseAndroidTest {


    @Test
    public void testListItems() {
        FlutterFinder finder = new FlutterFinder(getDriver());
        
        getDriver().context("NATIVE_APP");
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(5));

        WebElement itemListTitle = getDriver().findElement(By.id("infinite-list-title"));
        wait.until(ExpectedConditions.visibilityOf(itemListTitle));


        List<WebElement> itemTiles = getDriver().findElements(By.id("colour-item"));
        Assert.assertTrue(itemTiles.size() >= 10);

        for (WebElement item : itemTiles) {
            wait.until(ExpectedConditions.visibilityOf(item));
            WebElement itemDescription = item.findElement(By.className("android.view.View"));
            String itemText = itemDescription.getAttribute("content-desc");
            System.out.println("New itemTile found with text: " + itemText);
            Assert.assertTrue(itemText.contains("€"));
        }

        getDriver().context("FLUTTER");
        //Locating list item title
        String listItemTitleKey = "item_title";
        WebElement listItemTitle = waitForElement(finder.byValueKey(listItemTitleKey));

        //Locating list item price
        String listItemPriceKey = "item_price";
        WebElement listItemPrice = waitForElement(finder.byValueKey(listItemPriceKey));

        //Locating list item
        Pattern listItemSemanticsLabelRegExp = Pattern.compile("colour-item");
        WebElement listItem = waitForElement(finder.bySemanticsLabel(listItemSemanticsLabelRegExp));

        //Scroll down to specific list item
        getDriver().executeScript("flutter:scrollUntilVisible", finder.byType("ListView"), new HashMap<String, Object>() {{
            put("item", finder.byText("Colour #21"));
            put("dyScroll", -100);
            put("alignment", 0); // alignment 0 top, alignment 0.5 middle, alignment 1 bottom
        }});

        // WebElement topItemTitle = finder.byValueKey(listItemTitleKey);
        // it's all about duplicate keys. If some widgets have same keys you get too many elements error..
        // Assert.assertEquals(topItemTitle.getText(), "Colour #21");
         
    }

    private WebElement waitForElement(FlutterElement finderLocator){
        return (WebElement) getDriver().executeScript("flutter:waitFor", finderLocator, 10000);
  }
}