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

### Set up on Windows

- Download & Install JDK 8
    - Update `JAVA_HOME` and `PATH` env variables
- Download & Install maven
    - Update `PATH` env variable
- Download & Install Node.js
    - Update `PATH`env variable
- Install appium node module
    - `npm -g appium`
- Install [appium Desktop](http://appium.io/downloads.html)
    - Standalone appium server + Inspector session
    - Really useful while developing test cases to 
        - Debug your session & capabilities
        - Inspect app element locators while app is running on device
        - Check interactions
    - Remember to *stop this appium server* before running the tests
        - The tests will start their own instance of appium server

- Switch device to Developer Mode
    - Configure `Developer Options`
        - Stay awake
        - USB Debugging
        - Verify apps via USB
        - Show touches


## How to run

The tests manage the lifecycle of appium server and client sessions.

`mvn clean test`
