# Overview

This is a Cucumber BDD project that automates the validation of Scenarios that describe expected behaviour Gherkin language.

The basic building blocks are Cucumber JVM, Selenium and Junit, with Maven as building and dependency tool.

It will run test scenarios against https://www.lambdatest.com/selenium-playground on a local Chrome browser. 

Results are visible on the console as well as HTML and JSON reports in the build folder.


## Contents


| **Folder / File**                                                                  | **Contents**                                      |
|------------------------------------------------------------------------------------|---------------------------------------------------|
| [src/test/resources/features](src/test/resources/features)                         | feature files with Gherkin scenarios              |
| [src/test/java/io/github/allioli/pages](src/test/java/io/github/allioli/pages)     | Pages implementing Page Object Model              |
| [src/test/java/io/github/allioli/steps](src/test/java/io/github/allioli/steps)     | Scenario step definitions and supporting code     |
| [CucumberTestRunner.java](src/test/java/io/github/allioli/CucumberTestRunner.java) | Test Runner class with Cucumber runner parameters |
| [pom.xml](pom.xml)                                                                 | Maven project dependencies and description        |


## Dependencies

- Java 22
- Maven
- Selenium 4
- Junit 5
- Cucumber Java
- Chrome browser
- Chrome Selenium driver

## How to run

`mvn clean test`
