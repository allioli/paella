# Overview

This is an appium, java-client project with junit tests.

It was put together using mvn, visual studio code.

It will run functional test cases on a bundled app running on a real Android device and report results to console and `target\surefire-reports`.


Examples include design patterns such as *Page object* and *Page factory*.


## Contents


| **Folder / File**                    | **Contents** |
| ---                           | ---          |
|   apps |   APK package of Android application to test      |
|   src/test/java               |    JUnit tests and supporting code     |
|   [pom.xml](pom.xml)               |    Maven project dependencies and description     |


## Dependencies

- Java 8 JDK
- Maven
- Node.js
- Appium server

### Installing stuff on Windows

- Download & Install JDK 8
- Update `JAVA_HOME` and `PATH` env variables
- Download & Install maven
- Update `PATH` env variable
- 

## How to run

The tests manage appium server runtime and client sessions.

`mvn clean test`
