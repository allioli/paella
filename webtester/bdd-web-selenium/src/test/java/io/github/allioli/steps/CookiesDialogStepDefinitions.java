package io.github.allioli.steps;
import io.cucumber.java.en.Given;

import io.github.allioli.pages.CookiesDialog;
import org.openqa.selenium.WebDriver;

public class CookiesDialogStepDefinitions {

    private final CookiesDialog cookiesDialog;

    public CookiesDialogStepDefinitions() {
        cookiesDialog = new CookiesDialog(Hooks.driverManager.getWebDriver());
    }

    @Given("I have dismissed Cookies dialog")
    public void dismissCookiesDialog() {
        cookiesDialog.await();
        cookiesDialog.clickAllowSelection();
    }
}


