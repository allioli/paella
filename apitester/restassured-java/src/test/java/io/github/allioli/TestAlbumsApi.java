package io.github.allioli;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestAlbumsApi {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(description = "Should List all albums")
    public void getAllAlbums() {
        given()
                .header("Content-Type", "application/json")
        .when().log().ifValidationFails()
                .get("/albums")
        .then().log().ifValidationFails()
                .statusCode(200)
                .assertThat().body("size()", is(100))
                .time(lessThan(1000L));
    }

    @Test(description = "Should List albums for user1")
    public void getAlbumsForUser1() {
        given()
                .header("Content-Type", "application/json")
        .when()
                .queryParam("userId", 1)
                .get("/albums")
        .then().log().ifValidationFails()
                .statusCode(200)
                .assertThat().body("size()", is(10));
    }
}
