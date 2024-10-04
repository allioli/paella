# Overview

This is a RestAssured Java project to test Restful web services.

## Main goals
* Practise simple, concise test cases written in RestAssured DSL, leveraging the fluent interface to enhance test readability. 
* Expand the usage of RestAssured to test a web api with user authentication and functional test cases. 

Explore best practices to maximise reusable code, decouple testing logic from client-server interactions and reduce friction when incorporating more test cases.

We want to keep the fluent given, when, then descriptive test logic as much as possible, but avoiding maintenance issues when it comes to updating basic units of logic that are repeated in most of the tests.


## Contents


| **Item** | **Contents** |
| ---        | ---          |
|   [TestAlbumsApi.java](src/test/java/io/github/allioli/TestAlbumsApi.java) |    Simple RestAssured tests related to `https://jsonplaceholder.typicode.com`    |
|   [TestBookStoreApi.java](src/test/java/io/github/allioli/TestBookStoreApi.java) |   RestAssured tests related to `https://demoqa.com/swagger`    |
|   [schemas](src/test/resources/schemas/) |     Json schemas to support response data validation, including expected format for uri, date-time fields       |
|   [bookstoreapi.properties](src/test/resources/bookstoreapi.properties) |   BookstoreApi test settings      |
|   [bookstoreapi.model.requests](src/test/java/io/github/allioli/bookstoreapi/model/requests/) |   POJOS to represent request data        |
|   [bookstoreapi.model.responses](src/test/java/io/github/allioli/bookstoreapi/model/responses/) |   POJOS to represent response data        |
|   [bookstoreapi.services](src/test/java/io/github/allioli/bookstoreapi/services/) |   Reusable Endpoint classes with mapped actions on resources        |
|   [bookstoreapi.specs](src/test/java/io/github/allioli/bookstoreapi/specs/) |   Reusable RestAssured request specs used by services        |
|   [IGenericResponse.java](src/test/java/io/github/allioli/bookstoreapi/IGenericResponse.java) |   Interface for a generic response containing RestAssured response and deserialised response data        |
|   [RoutesV1.java](src/test/java/io/github/allioli/bookstoreapi/RoutesV1.java) |   Reusable service endpoint routes        |

## Dependencies

- Java 22
- RestAssured
- HamCrest assertions
- TestNG
- Owner Java properties
- Instancio Fake data generation

## How to run
### From terminal
`mvn clean test`
### From your favourite IDE
- Import maven project
- Synchronise dependencies
- You should see the "Run/Debug" next to the test cases in the code

### Test scope
There are different types of tests:
* Tests that check expected data or number of resources given a specific request
* Tests that check that returned data is valid according to the api documentation (json schema validation)
* Functional tests performing CRUD actions and expecting a specic outcome (calling multiple endpoints in one test)
 

## Best practices explored in this example project

# Service classes 
Service classes encapsulate client-server interaction details and provide a cleaner abstraction for the test logic. The aim is to have test input params and assertions in test cases. Calls to the endpoints and supporting logic live in Service classes. An exception to this rule are the checks for status code, time spent. Given that the tests are mostly happy path user flows, these can be regarded as low-level checks in this context. 
All Services support BookStore API v1. If there were endpoints versioned as v2, we would create Service classes for V2.

# POJOS for serialisation / deserialisation of data
Dealing with raw JSON in request / response body can lead to errors when maintaining tests. In the Bookstore API tests we are using POJOs to represent that data, and letting Restassured do the serialisation work in the request builder methods. Similarly, instead of deserialising String responses with `JsonPath.fromString).get("books");`, we can do `response.getBody().as(BooksData.class);` This comes very handy also to catch fields that are missing / have changed name unexpectedly.

Despite the improvement, one could argue that we still need to keep all the POJOs up-to-date manually: to go further, we should have a fixture generator based on the latest API spec, but this is out of scope of this project.

# Generic response interface
We have a little bit of a problem: some methods in Service classes would be more convenient if they returned deserialised POJOs as response data. At the same time, we don't want to lose all the RestAssured Response class goodness in terms of fluent assertions, access to headers, stataus code, etc.

A possible solution is `IGenericResponse.java`. It is a generic interface that exposes both RestAssures response and the deserialised POJO. This way, the test logic can get value from both to assert expected results.

# Reusable specs
Service classes support resource operations with and without authorisation. To prevent duplicating the request header builder in each call to server, Request specs are defined in `BookstoreSpecs.java` and built in a member request variable by the parent `BaseService.java` in the different constructors available. Service classes inheriting from `BaseService.java` re-use this request and don't have to specify common traits like `Content-Type` or `Authorisation`.


# All Routes lead to Rome
Endpoint route definitions are encapsulated in `RoutesV1.java`. Service classes use the provided abstraction to get the appropriate route for each endpoint operation, without having to re-define them every time. This will allow us to quickly update / extend them from a single place.

 

## Sources
* [TooksQA best practices tutorial](https://www.toolsqa.com/rest-assured/api-documentation/)
* [Basic tutorial](hhttps://naodeng.medium.com/rest-assured-tutorial-building-a-rest-assured-interface-automation-test-program-from-0-to-1-aa8c3f98c6d2)
* [RestAssured guide](https://www.baeldung.com/rest-assured-tutorial)
* [Advanced example in github, by Elias Negreira](https://github.com/eliasnogueira/restassured-complete-basic-example/tree/main)
* [Owner library](https://matteobaccan.github.io/owner/)
* [Instancio user guide](https://www.instancio.org/user-guide/)







