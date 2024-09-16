package io.github.allioli;

import org.junit.runner.RunWith;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = "classpath:features",
        glue     = {"io.github.allioli.steps"},
        plugin   = {"pretty", "html:target/cucumber-html-report"},
        tags     = "@forms"
)
public class CucumberTestRunner {
}
