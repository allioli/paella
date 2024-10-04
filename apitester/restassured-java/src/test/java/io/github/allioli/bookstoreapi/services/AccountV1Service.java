package io.github.allioli.bookstoreapi.services;

import io.github.allioli.bookstoreapi.GenericResponse;
import io.github.allioli.bookstoreapi.IGenericResponse;
import io.github.allioli.bookstoreapi.RoutesV1;
import io.github.allioli.bookstoreapi.model.requests.CredentialsPayload;
import io.github.allioli.bookstoreapi.model.responses.UserAccountData;
import io.github.allioli.bookstoreapi.model.responses.UserCreatedAccountData;
import io.restassured.response.Response;


import static org.hamcrest.Matchers.is;


public class AccountV1Service extends BaseService {

    public AccountV1Service(){
        super();
    }

    public AccountV1Service(String authToken){
        super(authToken);
    }

    public IGenericResponse<UserAccountData> getUserAccount(String userId) {
        Response response =
            request
                .when()
                    .get(RoutesV1.concreteUserAccount(userId))
                .then().log().body()
                    .statusCode(200)
                    .body("userId", is(userId))
                .extract()
                    .response();

        return new GenericResponse<>(UserAccountData.class, response);
    }

    public IGenericResponse<UserCreatedAccountData> createUserAccount(CredentialsPayload payload) {
        Response response =
                request
                    .when()
                        .body(payload)
                        .post(RoutesV1.userAccount())
                    .then().log().body()
                        .statusCode(201)
                    .extract()
                        .response();

        return new GenericResponse<>(UserCreatedAccountData.class, response);
    }

    public void deleteUserAccount(String userId) {

        request
            .when()
                .delete(RoutesV1.concreteUserAccount(userId))
            .then().log().ifValidationFails()
                .statusCode(204);

    }
}
