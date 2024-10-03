package io.github.allioli.bookstore.services;

import io.github.allioli.bookstore.specs.BookstoreSpecs;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


public class AccountService {

    private final RequestSpecification request;

    public AccountService(String authToken){
        request = RestAssured.given()
                .spec(BookstoreSpecs.getBaseRequestSpec())
                .spec(BookstoreSpecs.getAuthRequestSpec(authToken));
    }

    public Response getUserAccount(String userId) {
        return
            request
                .when()
                    .pathParam("UUID", userId)
                    .get("/Account/v1/User/{UUID}")
                .then().log().body()
                    .statusCode(200)
                .extract()
                    .response();
    }
}
