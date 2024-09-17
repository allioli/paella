package io.github.allioli.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import java.util.List;

public class CookiesDialog extends AbstractPage {

    @FindBy(how= How.ID, using="CybotCookiebotDialogBodyLevelButtonLevelOptinAllowallSelection")
    private WebElement allowSelectionButton;

    public CookiesDialog(WebDriver driver) {
        super(driver);
    }

    @Override
    protected List<WebElement> identifyingElements() {
        return List.of(allowSelectionButton);
    }

    public void clickAllowSelection() {
        allowSelectionButton.click();
    }
}
