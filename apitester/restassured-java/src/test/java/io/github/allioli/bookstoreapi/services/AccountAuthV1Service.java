package io.github.allioli.bookstoreapi.services;

import io.github.allioli.bookstoreapi.GenericResponse;
import io.github.allioli.bookstoreapi.IGenericResponse;
import io.github.allioli.bookstoreapi.RoutesV1;
import io.github.allioli.bookstoreapi.model.requests.CredentialsPayload;
import io.github.allioli.bookstoreapi.model.responses.AuthTokenData;
import io.restassured.response.Response;

import static org.hamcrest.Matchers.*;

public class AccountAuthV1Service extends BaseService {

    public AccountAuthV1Service() {
        super();
    }

    public IGenericResponse<AuthTokenData> generateUserToken(CredentialsPayload payload) {

        Response response = request
                .when()
                    .body(payload)
                    .post(RoutesV1.generateToken())
                .then().log().body()
                    .statusCode(200)
                    .assertThat()
                    .body("token", notNullValue())
                .extract()
                    .response();

        return new GenericResponse<>(AuthTokenData.class, response);
    }

    public void checkUserAuthorised(CredentialsPayload payload) {

        request
            .when()
                .body(payload)
                .post(RoutesV1.userAuthorised())
            .then().log().ifValidationFails()
                .statusCode(200)
            .extract()
                .response();
    }
}
