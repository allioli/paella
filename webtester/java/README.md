# Overview

This is a cucumber-jvm, selenium project.

It was put together using eclipse, mvn.

It will run cucumber scenarios against us.letgo.com and report results to console and `target\cucumber-html-report\index.html`.

Tests use chromedriver to run chrome browser with maximised windows.

Default selenium timeouts are adjusted to run on a low-end laptop and might appear conservative for higher powered machines.

Examples include design patterns such as *Page object*, *Page factory* and *Action Bot*.

Additionally, there is an example of guice injection of a Scenario-scopped World instance, which supports passing information between steps.

Default cukes runner tags are @account, @search


## Contents


| **Folder**                    | **Contents** |
| ---                           | ---          |
|   src/test/resources/features |   feature files with cucumber scenarios      |
|   src/test/java               |    Scenario step definitions and supporting code     |
|   pom.xml               |    Maven project dependencies and description     |




## Dependencies

- Java 8
- Chrome browser

## How to run

`mvn clean test`
