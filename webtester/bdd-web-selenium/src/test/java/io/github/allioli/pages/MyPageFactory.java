package io.github.allioli.pages;

import io.github.allioli.steps.Hooks;

public class MyPageFactory {

    // pageName parameter must be in CamelCase
    public static IBasePage getPage(String pageName){
        return switch (pageName) {
            case "SimpleFormDemo" -> new SimpleFormDemoPage(Hooks.driverManager.getWebDriver());
            default -> throw new IllegalArgumentException("Unknown page name specified");
        };
    }
}
