package io.github.allioli;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue     = {"io.github.allioli.steps"},
        plugin   = {"pretty",
                    "html:target/cucumber-html-report/cucumber.html",
                    "json:target/cucumber-reports/cucumber.json"},
        tags     = "@forms"
)
public class CucumberTestRunner {
}
