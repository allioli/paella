package io.github.allioli.bookstoreapi.services;

import io.github.allioli.bookstoreapi.RoutesV1;
import io.github.allioli.bookstoreapi.model.requests.GenerateTokenPayload;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.notNullValue;

public class AccountAuthV1Service extends BaseService {

    public AccountAuthV1Service() {
        super();
    }

    public Response generateUserToken(GenerateTokenPayload payload) {

        return request
                .when()
                    .body(payload)
                    .post(RoutesV1.generateToken())
                .then().log().body()
                    .statusCode(200)
                    .assertThat()
                    .body("token", notNullValue())
                .extract()
                    .response();
    }
}
