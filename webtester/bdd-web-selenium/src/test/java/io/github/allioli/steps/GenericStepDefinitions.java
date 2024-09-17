package io.github.allioli.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.allioli.pages.IBasePage;
import io.github.allioli.pages.MyPageFactory;
import org.apache.commons.text.CaseUtils;

public class GenericStepDefinitions {

    @And("I am in {string} page")
    public void waitForPage(String pageName) {
        String pageNameCamelCase = CaseUtils.toCamelCase(pageName, true, ' ');
        IBasePage page = MyPageFactory.getPage(pageNameCamelCase);
        page.await();
    }

    @And("I verify {string} page")
    public void waitForPageDefaultElements(String pageName) {
        String pageNameCamelCase = CaseUtils.toCamelCase(pageName, true, ' ');
        IBasePage page = MyPageFactory.getPage(pageNameCamelCase);
        page.waitForDefaultElements();
    }
}
