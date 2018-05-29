# Overview

This is a testng, selenium project.

It was put together using eclipse, mvn on a windows environment.

It will run testng tests against app.forcemanger.net and report results to console and `\target\surefire-reports\index.html`

Tests use chromedriver to run chrome browser.

Default selenium timeouts are adjusted to run on a low-end laptop and might appear conservative for higher powered machines.

Examples include design patterns such as *Page object* and *Page factory*.

Maven will run the default test suite with all login tests using the Surefire plugin.


## Contents

| **Folder / File**                    | **Contents** |
| ---                           | ---          |
|   src/test/java               |   Folder with TestNG tests and supporting code     |
|   [pom.xml](pom.xml)                     |    Maven project dependencies and description     |
|   [testng.xml](testng.xml)                  |  Test suite configuration for Maven Surefire plugin |


## Dependencies

- Java 8
- Chrome browser

## How to run

`mvn clean test`






