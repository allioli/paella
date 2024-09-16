package io.github.allioli.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.allioli.pages.SimpleFormDemoPage;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

import static java.lang.Integer.*;

public class SimpleFormStepDefinitions {

    private SimpleFormDemoPage simpleFormDemoPage;

    public SimpleFormStepDefinitions() {
        WebDriver driver = Hooks.driver;
        simpleFormDemoPage = new SimpleFormDemoPage(driver);
    }

    @Given("I have opened form page in sandbox")
    public void openSandboxSimpleFormPageStep() {
        simpleFormDemoPage.openPage();
    }

    @When("I enter {int} and {int} to the sum up form")
    public void enterTwoNumbersStep(int number1, int number2) {
        simpleFormDemoPage.await();
        simpleFormDemoPage.waitForDefaultElements();
        simpleFormDemoPage.enterSum1FieldValue(String.valueOf(number1))
                .enterSum2FieldValue(String.valueOf(number2));
    }

    @When("I enter {string} to the message form")
    public void enterMesssageText(String message) {
        simpleFormDemoPage.await();
        simpleFormDemoPage.enterMessageText(message);
    }

    @When("I click on Sum button")
    public void clickSumButtonStep() {
        simpleFormDemoPage.clickTwoFieldFormSubmitButton();
    }

    @When("I click on Get checked value button")
    public void clickGetCheckedValueButton() {
        simpleFormDemoPage.clickSingleInputFieldFormSubmitButton();
    }

    @Then("I should see the result of the sum as {int}")
    public void checkSumResultMessage(int expectedResult) {
        String actualResult = simpleFormDemoPage.getSumResult();
        Assert.assertEquals(expectedResult, Integer.parseInt(actualResult));
    }

    @Then("I should see the entered message as {string}")
    public void checkEnteredMessage(String expectedMessage) {
        String actualMessage = simpleFormDemoPage.getEnteredMessage();
        Assert.assertEquals(expectedMessage, actualMessage);
    }


}
