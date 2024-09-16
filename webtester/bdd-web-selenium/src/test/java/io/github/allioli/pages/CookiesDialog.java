package io.github.allioli.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class CookiesDialog extends BasePage {

    @FindBy(how= How.ID, using="CybotCookiebotDialogBodyLevelButtonLevelOptinAllowallSelection")
    private WebElement allowSelectionButton;

    public CookiesDialog(WebDriver driver) {
        super(driver);
    }

    @Override
    protected List<WebElement> identifyingElements() {
        return List.of(allowSelectionButton);
    }

    @Override
    public void waitForDefaultElements() {
    }

    public void clickAllowSelection() {
        allowSelectionButton.click();
    }
}