package io.github.allioli;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class TestAlbumsApi {

    @BeforeSuite
    public void setUp() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    @Test(description = "Verify that the Get Post API returns correctly")
    public void verifyGetAPI() {

        given()
                .header("Content-Type", "application/json")
        .when()
                .get("/posts/1")
        .then()
                .statusCode(200)
                // To verify correct value
                .body("userId", equalTo(1))
                .body("id", equalTo(1))
                .body("title", equalTo("sunt aut facere repellat provident occaecati excepturi optio reprehenderit"))
                .body("body", equalTo("quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto"));
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

    @Test(description = "Verify that the publish post API returns correctly")
    public void verifyPostAPI() {
        // Given
        given()
                .header("Content-Type", "application/json")
                // When
                .when()
                .body("{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1\n}")
                .post("/posts")
                // Then
                .then()
                .statusCode(201)
                // To verify correct value
                .body("userId", equalTo(1))
                .body("id", equalTo(101))
                .body("title", equalTo("foo"))
                .body("body", equalTo("bar"));
    }
}
