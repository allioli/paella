# Overview

This is a node.js project.

It was put together using:

- `npm init`
- `npm install` of dependencies (see *dependencies* section in package.json).

It will run cucumber scenarios against us.letgo.com and report results in console and `reports/*.json` files.

Tests use chromedriver to run chrome browser with maximised windows.

Default selenium timeouts are adjusted to run on a low-end laptop and might appear conservative for higher powered machines.

Async *Promises* in selenium.js have been a real pain in order to write readable code using expectations together with assertions from the *chai library*.

Functionality like *driver.Key.RETURN* didn't work to trigger a RETURN press on an element, so I defaulted to "\n".



## Contents

| **Folder** | **Contents** |
| ---        | ---          |
|   features |    Folder containing all the code     |
|   node_modules |     Nodejs dependencies and binaries      |
|   report |    JSON reports after a cucumber run     |
|   package.json |     Project description   |
|   cucumber.bat |     Helper batch file to skip wrong choice of cucumber binary for Windows  |

## Dependencies

- Node.js installed
- Chrome browser

## How to run

`npm test`
