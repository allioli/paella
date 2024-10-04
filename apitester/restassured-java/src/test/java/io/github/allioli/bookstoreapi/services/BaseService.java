package io.github.allioli.bookstoreapi.services;

import io.github.allioli.bookstoreapi.specs.BookstoreSpecs;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class BaseService {

    protected final RequestSpecification request;

    protected BaseService(){
        request = RestAssured.given()
                .spec(BookstoreSpecs.getBaseRequestSpec());
    }
    protected BaseService(String authToken){
        request = RestAssured.given()
                .spec(BookstoreSpecs.getBaseRequestSpec())
                .spec(BookstoreSpecs.getAuthRequestSpec(authToken));
    }
}
