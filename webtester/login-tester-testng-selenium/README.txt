
WHAT IS IT?
===========
This is a testng, selenium project.

It was put together using eclipse, mvn on a windows environment.

It will run testng tests against app.forcemanger.net and report results in console and  \target\surefire-reports\index.html

Tests use chromedriver to run chrome browser.

Default selenium timeouts are adjusted to run on a low-end laptop and might appear conservative for higher powered machines.

Examples include design patterns such as Page object and Page factory.

The default test suite with all login tests will be run by maven testng surefire plugin using "mvn clean test"


CONTENTS
========

src/test/java
-------------
Folder with TestNG tests and supporting code

pom.xml
------
Maven project dependencies and description

testng.xml
----------
Test suite configuration for surefire plugin

REQUIREMENTS
============
Java 8
Chrome browser

HOW TO RUN
==========
mvn clean test






