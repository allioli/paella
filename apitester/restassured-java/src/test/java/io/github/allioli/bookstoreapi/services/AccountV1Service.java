package io.github.allioli.bookstoreapi.services;

import io.github.allioli.bookstoreapi.RoutesV1;
import io.restassured.response.Response;


import static org.hamcrest.Matchers.is;


public class AccountV1Service extends BaseService {

    public AccountV1Service(String authToken){
        super(authToken);
    }

    public Response getUserAccount(String userId) {
        return
            request
                .when()
                    .get(RoutesV1.userAccount(userId))
                .then().log().body()
                    .statusCode(200)
                    .body("userId", is(userId))
                .extract()
                    .response();
    }
}
