package io.github.allioli.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.allioli.pages.SimpleFormDemoPage;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class SimpleFormStepDefinitions {

    private SimpleFormDemoPage simpleFormDemoPage;

    public SimpleFormStepDefinitions() {
        WebDriver driver = Hooks.driver;
        simpleFormDemoPage = new SimpleFormDemoPage(driver);
    }

    @Given("^I have opened form page in sandbox$")
    public void openSandboxSimpleFormPageStep() {
        simpleFormDemoPage.openPage();
    }

    @When("^I enter two numbers to the sum up form$")
    public void enterTwoNumbersStep() {
        simpleFormDemoPage.await();
        simpleFormDemoPage.waitForDefaultElements();
        simpleFormDemoPage.enterSum1FieldValue("2")
                .enterSum2FieldValue("7");
    }

    @When("^I click on Sum button$")
    public void clickSumButtonStep() {
        simpleFormDemoPage.clickTwoFieldFormSubmitButton();
    }

    @Then("^I should see the result of the sum$")
    public void checkSumResultMessage() {
        String actualResult = simpleFormDemoPage.getSumResult();
        Assert.assertEquals("9", actualResult);
    }


}
