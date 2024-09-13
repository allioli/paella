package io.github.allioli.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class SimpleFormDemoPage extends BasePage {

    @FindBy(how=How.ID, using="user-message")
    private WebElement singleInputFormMessageInput;

    @FindBy(how=How.ID, using="showInput")
    private WebElement singleInputFieldFormSubmitButton;

    @FindBy(how=How.ID, using="sum1")
    private WebElement twoFieldFormSumInput1;

    @FindBy(how=How.ID, using="sum2")
    private WebElement twoFieldFormSumInput2;

    @FindBy(how=How.CSS, using="#gettotal > button")
    private WebElement twoFieldFormSubmitButton;

    @FindBy(how=How.ID, using="addmessage")
    private WebElement twoFieldFormSumResult;

    private static final String URL = "https://www.lambdatest.com/selenium-playground/simple-form-demo";

    public SimpleFormDemoPage(WebDriver driver) {
        super(driver);
    }

    public SimpleFormDemoPage openPage() {
        driver.get(URL);
        return this;
    }

    @Override
    protected List<WebElement> identifyingElements() {
        return List.of(singleInputFormMessageInput);
    }


    @Override
    public void waitForDefaultElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(singleInputFormMessageInput, twoFieldFormSumInput1, twoFieldFormSumInput2));
        wait.until(ExpectedConditions.elementToBeClickable(singleInputFieldFormSubmitButton));
        wait.until(ExpectedConditions.elementToBeClickable(twoFieldFormSubmitButton));
    }

    public SimpleFormDemoPage enterSum1FieldValue(String value) {
        twoFieldFormSumInput1.clear();
        twoFieldFormSumInput1.sendKeys(value);
        return this;
    }

    public SimpleFormDemoPage enterSum2FieldValue(String value) {
        twoFieldFormSumInput2.clear();
        twoFieldFormSumInput2.sendKeys(value);
        return this;
    }

    public SimpleFormDemoPage clickTwoFieldFormSubmitButton() {
        twoFieldFormSubmitButton.click();
        return this;
    }

    public String getSumResult() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(twoFieldFormSumResult));
        return twoFieldFormSumResult.getText();
    }
}
