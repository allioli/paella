package io.github.allioli.steps;
import io.cucumber.java.en.Given;

import io.github.allioli.pages.CookiesDialog;
import org.openqa.selenium.WebDriver;

public class CookiesDialogSteps {

    private CookiesDialog cookiesDialog;

    public CookiesDialogSteps() {
        WebDriver driver = Hooks.driver;
        cookiesDialog = new CookiesDialog(driver);
    }

    @Given("I have dismissed Cookies dialog")
    public void dismissCookiesDialog() {
        cookiesDialog.await();
        cookiesDialog.clickAllowSelection();
    }
}


