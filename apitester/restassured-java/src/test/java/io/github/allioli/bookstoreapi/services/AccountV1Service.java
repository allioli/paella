package io.github.allioli.bookstoreapi.services;

import io.github.allioli.bookstoreapi.GenericResponse;
import io.github.allioli.bookstoreapi.IGenericResponse;
import io.github.allioli.bookstoreapi.RoutesV1;
import io.github.allioli.bookstoreapi.model.responses.UserAccountData;
import io.restassured.response.Response;


import static org.hamcrest.Matchers.is;


public class AccountV1Service extends BaseService {

    public AccountV1Service(String authToken){
        super(authToken);
    }

    public IGenericResponse<UserAccountData> getUserAccount(String userId) {
        Response response =
            request
                .when()
                    .get(RoutesV1.userAccount(userId))
                .then().log().body()
                    .statusCode(200)
                    .body("userId", is(userId))
                .extract()
                    .response();

        return new GenericResponse<>(UserAccountData.class, response);
    }
}
