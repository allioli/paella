package io.github.allioli.bookstoreapi.services;

import io.restassured.response.Response;


import static org.hamcrest.Matchers.is;


public class AccountService extends BaseService {

    public AccountService(String authToken){
        super(authToken);
    }

    public Response getUserAccount(String userId) {
        return
            request
                .when()
                    .pathParam("UUID", userId)
                    .get("/Account/v1/User/{UUID}")
                .then().log().body()
                    .statusCode(200)
                    .body("userId", is(userId))
                .extract()
                    .response();
    }
}
