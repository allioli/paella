package io.github.allioli;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class TestBookStoreApi {

    private final String userID = "5fa127c4-1f2b-4ccd-b8f2-e0620f562da8";
    private final String userName = "xavi-test2";
    private final String password = "Secret1!";
    private String authToken = null;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
    }

    @BeforeTest
    public void setUpAuthToken() {
         if (authToken == null) {
             authToken = generateAuthToken();
        }
    }

    @Test(description = "List all books")
    public void getAllBooks() {

        given()
                .header("Content-Type", "application/json")
        .when()
                .get("/BookStore/v1/Books")
        .then().log().ifValidationFails()
                .statusCode(200)
                .assertThat()
                .body("size()", greaterThan(0))
                .body("books[0]", hasKey("isbn"))
                .body("books[0].pages", greaterThan(0))
                .time(lessThan(2000L));
    }

    @Test(description = "Borrow a book")
    public void borrowBook() {

        String bookIsbn = "9781593277574";

        given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
        .when()
                .body("{ \"userId\": \"" + userID + "\", " +
                        "\"collectionOfIsbns\": [ { \"isbn\": \"" + bookIsbn + "\" } ]}")
                .post("/BookStore/v1/Books")
        .then().log().ifValidationFails()
                .statusCode(201);

        Response getUserResponse = getUser();
        List<Map<String, String>> booksOfUser = JsonPath.from(getUserResponse.asString()).get("books");
        Assert.assertFalse(booksOfUser.isEmpty());

        boolean borrowedBookFound = false;
        for (Map<String, String> bookOfUser : booksOfUser) {
            if (bookOfUser.get("isbn").equals(bookIsbn)) {
                borrowedBookFound = true;
                break;
            }
        }
        Assert.assertTrue(borrowedBookFound);
    }

    @Test(description = "Return all books")
    public void returnAllBooks() {

        given()
                .header("Authorization", "Bearer " + authToken)
                .header("Content-Type", "application/json")
        .when()
                .queryParam("UserId", userID)
                .delete("/BookStore/v1/Books")
        .then().log().ifValidationFails()
                .statusCode(204);

        Response getUserResponse = getUser();
        List<Map<String, String>> booksOfUser = JsonPath.from(getUserResponse.asString()).get("books");
        Assert.assertTrue(booksOfUser.isEmpty());
    }



    private String generateAuthToken() {
        String token =
                given()
                        .header("Content-Type", "application/json")
                .when()
                        .body("{ \"userName\":\"" + userName + "\", \"password\":\"" + password + "\"}")
                        .post("/Account/v1/GenerateToken")
                .then().log().body()
                        .statusCode(200)
                        .assertThat()
                        .body("token", notNullValue())
                        .extract()
                        .response()
                        .path("token");

        return token;
    }

    private Response getUser() {
        Response response =
                given()
                        .header("Authorization", "Bearer " + authToken)
                        .header("Content-Type", "application/json")
                .when()
                        .pathParam("UUID", userID)
                        .get("/Account/v1/User/{UUID}")
                .then().log().body()
                        .statusCode(200)
                        .assertThat()
                        .body("username", is(userName))
                .extract()
                        .response();
        
        return response;
    }
}
