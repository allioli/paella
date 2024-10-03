package io.github.allioli.bookstore;

import io.github.allioli.bookstore.model.requests.GenerateTokenPayload;
import io.github.allioli.bookstore.model.responses.GenerateTokenResponse;
import io.github.allioli.bookstore.specs.BookstoreSpecs;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;


public class Endpoints {

    public static GenerateTokenResponse generateUserToken(GenerateTokenPayload payload) {

        return given()
            .spec(BookstoreSpecs.getBaseRequestSpec())
        .when()
            .body(payload)
            .post("/Account/v1/GenerateToken")
        .then().log().body()
                .statusCode(200)
                .assertThat()
                .body("token", notNullValue())
        .extract()
                .response()
                .as(GenerateTokenResponse.class);
    }
}
