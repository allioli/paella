package io.github.allioli.bookstoreapi.specs;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BookstoreSpecs {

    public static RequestSpecification getBaseRequestSpec() {

        return new RequestSpecBuilder()
                .addHeader("Content-Type", ContentType.JSON.toString())
                .addHeader("Accept", ContentType.JSON.toString())
                .build()
                .log().ifValidationFails();
    }

    public static RequestSpecification getAuthRequestSpec(String token) {

        // Tried alternatively with setAuth() passing in OAuth2Scheme, but it triggers issue described
        // in https://github.com/rest-assured/rest-assured/issues/884

        return new RequestSpecBuilder()
                .build()
                .auth().oauth2(token);
    }
}
