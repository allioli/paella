# Overview

This is a Cucumber BDD project that automates the validation of Scenarios that describe expected behaviour Gherkin language.

The basic building blocks are Cucumber JVM, Selenium and Junit, with Maven as building and dependency tool.

It will run test scenarios against https://www.lambdatest.com/selenium-playground on a local Chrome browser. 

Results are visible on the console as well as HTML and JSON reports in the target folder.


## Contents


| **Folder / File**                                                                                                          | **Contents**                                                   |
|----------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------|
| [src/test/resources/io/github/allioli/features](src/test/resources/io/github/allioli/features)                             | feature files with Gherkin scenarios                           |
| [src/test/java/io/github/allioli/pages](src/test/java/io/github/allioli/pages)                                             | Pages implementing Page Object Model                           |
| [src/test/java/io/github/allioli/steps](src/test/java/io/github/allioli/steps)                                             | Scenario step definitions and supporting code                  |
| [CucumberTestRunner.java](src/test/java/io/github/allioli/CucumberTestRunner.java)                                         | Test Runner class with JUnit Platform Suite Engine annotations |
| [pom.xml](pom.xml)                                                                                                         | Maven project dependencies / Cucumber parameters               |


## Dependencies

- Java 22
- Maven
- Selenium 4
- Junit 5
- Cucumber Junit Platform Suite Engine
- Cucumber Java
- Chrome browser
- Chrome Selenium driver

## How to run
### From terminal
`mvn clean test`
### From your favourite IDE
- Open existing bdd-web-selenium project
- Rebuild project 
- Open a feature file
- You should be able to run separate Scenarios or whole features
- Run / Debug at will

## Highlights

### Page Object model
We want to separate Web Page element locators, interaction, info retrieval and navigation from the test validation logic. This separation of concerns will reduce test maintenance efforts and avoid code duplication.

Each Page Object in this project implements interface `IBasePage.java`, which is the basic behaviour expected in order to
1. Check that the user is seeing the expected Page (mandatory)
2. Wait for all elements in Page default state are loaded (optional)

This is the behaviour needed by Generic Step Definitions, which leverage a simple `MyPageFactory.java` to obtain objects that implement IBasePage interface.

Other methods specific to each Page Object are also available to support interaction, info retrieval and navigation requirements from the logic in Step Definitions.

Common logic that is re-used in all Pages is declared in *AbstractPage*. Each Page Object must provide a concrete implementation of abstract method *identifyingElements*, with the minimal element locators to uniquely identify the Page.


#### Example [SimpleFormDemoPage.java](src/test/java/io/github/allioli/pages/SimpleFormDemoPage.java)
```java
public class SimpleFormDemoPage extends AbstractPage {

    @FindBy(how=How.ID, using="user-message")
    private WebElement singleInputFormMessageInput;

    @FindBy(how=How.ID, using="showInput")
    private WebElement singleInputFieldFormSubmitButton;

    @FindBy(how=How.ID, using="sum1")
    private WebElement twoFieldFormSumInput1;

    @FindBy(how=How.ID, using="sum2")
    private WebElement twoFieldFormSumInput2;

    @FindBy(how=How.CSS, using="#gettotal > button")
    private WebElement twoFieldFormSubmitButton;

    @FindBy(how=How.ID, using="addmessage")
    private WebElement twoFieldFormSumResult;

    @FindBy(how=How.ID, using="message")
    private WebElement userEnteredMessage;

    private static final String URL = "https://www.lambdatest.com/selenium-playground/simple-form-demo";

    public SimpleFormDemoPage(WebDriver driver) {
        super(driver);
    }

    public SimpleFormDemoPage openPage() {
        driver.get(URL);
        return this;
    }

    ...

    @Override
    protected List<WebElement> identifyingElements() {
        return List.of(singleInputFormMessageInput);
    }

    @Override
    public void waitForDefaultElements() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfAllElements(singleInputFormMessageInput, twoFieldFormSumInput1, twoFieldFormSumInput2));
        wait.until(ExpectedConditions.elementToBeClickable(singleInputFieldFormSubmitButton));
        wait.until(ExpectedConditions.elementToBeClickable(twoFieldFormSubmitButton));
    }
}
```
### Step Definitions
The Glue code is organised in different step definition files in package `io.github.allioli.steps`. There are two types of step definitions:
* Domain specific steps (Belong to a particular bit of functionality)
* Generic steps (Reusable steps for explicit page validation)

### Parallel execution
Tests run in parallel by means of *Cucumber JUnit Platform Engine*, following the configuration defined in the `pom.xml` file. This option takes precedence over the values in a separate `junit-platform.properties` file, according to the [documentation](https://junit.org/junit5/docs/current/user-guide/#running-tests-config-params). In the spirit of keeping it simple, no separate properties file is specified.
In this `pom.xml` example, the concurrency is fixed to 4 threads in parallel.

```xml
<build>
        <plugins>
             <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.5.0</version>
                <configuration>
                    <properties>
                        <configurationParameters>
                            cucumber.junit-platform.naming-strategy=long
                            cucumber.execution.parallel.enabled=true
                            cucumber.execution.parallel.config.strategy=fixed
                            cucumber.execution.parallel.config.fixed.parallelism=4
                            cucumber.execution.parallel.config.fixed.max-pool-size=4
                            cucumber.plugin=pretty, html:target/cucumber-reports/cucumber-report.html, json:target/cucumber-reports/cucumber-report.json, timeline:target/cucumber-reports/timeline-report
                        </configurationParameters>
                    </properties>
                    <includes>
                        <include>**/CucumberTestRunner.java</include>
                    </includes>
                </configuration>
             </plugin>
        </plugins>
    </build>
```


To prevent several threads accessing the same instance of the WebDriver, `MyDriverManager.java` provides access to ThreadLocal instances from the `Hooks.java` class.

### Reports
At the end of each test run, the following reports are expected:
* Cucumber HTML with failure screenshots
* Cucumber JSON
* Thread execution timeline

## Sources
* [Cucumber parallel execution](https://cucumber.io/docs/guides/parallel-execution/?sbsearch=parallel&lang=java)
* [Cucumber JUnit Platform Engine](https://github.com/cucumber/cucumber-jvm/tree/main/cucumber-junit-platform-engine)
* [Reminder to include package folder structure below test/resources](https://stackoverflow.com/questions/77595466/junit5-suite-did-not-discover-any-tests-spring-boot-with-cucumber-setup)
* [Benefits of managing BOM dependencies in POM file](https://www.terribletester.com/managing-cucumber-java-dependencies-with-maven-bom)
* [Multi-thread Selenium WebDriver](https://www.lambdatest.com/blog/threadlocal-in-java-with-selenium-webdriver/)
* [What's new in Selenium 4](https://www.browserstack.com/guide/selenium-4-features)