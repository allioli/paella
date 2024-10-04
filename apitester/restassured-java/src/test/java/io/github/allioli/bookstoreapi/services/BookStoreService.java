package io.github.allioli.bookstoreapi.services;

import io.github.allioli.bookstoreapi.model.requests.AddBooksPayload;
import io.github.allioli.bookstoreapi.specs.BookstoreSpecs;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class BookStoreService extends BaseService {

    public BookStoreService(){
        super();
    }
    public BookStoreService(String authToken){
        super(authToken);
    }

    public Response getAllBooks() {
        return
            request
                .when()
                    .get("/BookStore/v1/Books")
                .then().log().ifValidationFails()
                    .statusCode(200)
                    .time(lessThan(2000L))
                .extract()
                    .response();
    }

    public void removeAllBooksFromUserAccount(String userID) {
        request
            .when()
                .queryParam("UserId", userID)
                .delete("/BookStore/v1/Books")
            .then().log().ifValidationFails()
                .statusCode(204);
    }

    public void addBookToUserAccount(AddBooksPayload payload) {
        request
            .when()
                .body(payload)
                .post("/BookStore/v1/Books")
            .then().log().ifValidationFails()
                .statusCode(201);
    }
}
