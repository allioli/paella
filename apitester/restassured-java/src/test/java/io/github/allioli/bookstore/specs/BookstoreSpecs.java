package io.github.allioli.bookstore.specs;

import io.restassured.authentication.OAuth2Scheme;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class BookstoreSpecs {

    public static RequestSpecification getBaseRequestSpec() {

        return new RequestSpecBuilder()
                .addHeader("Content-Type", ContentType.JSON.toString())
                .addHeader("Accept", ContentType.JSON.toString())
                .build();
    }

    public static RequestSpecification getAuthRequestSpec(String token) {

        // Tried alternatively with setAuth() passing in OAuth2Scheme, but it triggers issue described
        // in https://stackoverflow.com/questions/51533815/getting-error-classnotfoundexception-even-after-scribe-for-oauth-in-restassure

        return new RequestSpecBuilder()
                .addHeader("Authorization", "Bearer " + token)
                .build();
    }
}
