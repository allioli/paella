package io.github.allioli.bookstoreapi.services;

import io.github.allioli.bookstoreapi.RoutesV1;
import io.github.allioli.bookstoreapi.model.requests.AddBooksPayload;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.lessThan;

public class BookStoreV1Service extends BaseService {

    public BookStoreV1Service(){
        super();
    }
    public BookStoreV1Service(String authToken){
        super(authToken);
    }

    public Response getAllBooks() {
        return
            request
                .when()
                    .get(RoutesV1.books())
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
                .delete(RoutesV1.books())
            .then().log().ifValidationFails()
                .statusCode(204);
    }

    public void addBookToUserAccount(AddBooksPayload payload) {
        request
            .when()
                .body(payload)
                .post(RoutesV1.books())
            .then().log().ifValidationFails()
                .statusCode(201);
    }
}
