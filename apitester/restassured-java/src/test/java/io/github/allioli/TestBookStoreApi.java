package io.github.allioli;

import io.github.allioli.bookstore.Endpoints;
import io.github.allioli.bookstore.model.requests.AddBooksPayload;
import io.github.allioli.bookstore.model.requests.GenerateTokenPayload;
import io.github.allioli.bookstore.model.requests.ISBN;
import io.github.allioli.bookstore.model.responses.Book;
import io.github.allioli.bookstore.model.responses.GenerateTokenResponse;
import io.github.allioli.bookstore.model.responses.GetUserAccountResponse;
import io.github.allioli.bookstore.specs.BookstoreSpecs;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;


public class TestBookStoreApi {

    private final String userID = "5fa127c4-1f2b-4ccd-b8f2-e0620f562da8";
    private final String userName = "xavi-test2";
    private final String password = "Secret1!";
    private GenerateTokenResponse authTokenInfo = null;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";

        if (authTokenInfo == null) {
            authTokenInfo = generateAuthToken();
        }
    }

    @Test(description = "List all books")
    public void getAllBooks() {

        given()
                .spec(BookstoreSpecs.getBaseRequestSpec())
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

    @Test(description = "Validate Get Books response")
    public void checkGetBooksContract() {

        given()
                .spec(BookstoreSpecs.getBaseRequestSpec())
        .when()
                .get("/BookStore/v1/Books")
        .then().log().ifValidationFails()
                .statusCode(200)
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/books_v1_schema.json"));

    }

    @Test(description = "Add book to reading list")
    public void addBookToReadingList() {

        String bookIsbn = "9781593277574";
        AddBooksPayload payload = new AddBooksPayload(userID, new ISBN(bookIsbn));

        given()
                .spec(BookstoreSpecs.getBaseRequestSpec())
                .spec(BookstoreSpecs.getAuthRequestSpec(authTokenInfo.token))
        .when()
                .body(payload)
                .post("/BookStore/v1/Books")
        .then().log().ifValidationFails()
                .statusCode(201);

        GetUserAccountResponse getUserAccountResponse = getUserAccount();
        Assert.assertFalse(getUserAccountResponse.books.isEmpty());

        boolean userBookFound = false;
        for (Book bookOfUser : getUserAccountResponse.books) {
            if (bookOfUser.isbn.equals(bookIsbn)) {
                userBookFound = true;
                break;
            }
        }
        Assert.assertTrue(userBookFound);
    }

    @Test(description = "Remove all books from reading list")
    public void removeAllBooksFromReadingList() {

        given()
                .spec(BookstoreSpecs.getBaseRequestSpec())
                .spec(BookstoreSpecs.getAuthRequestSpec(authTokenInfo.token))
        .when()
                .queryParam("UserId", userID)
                .delete("/BookStore/v1/Books")
        .then().log().ifValidationFails()
                .statusCode(204);

        GetUserAccountResponse getUserAccountResponse = getUserAccount();
        Assert.assertTrue(getUserAccountResponse.books.isEmpty());
    }

    private GenerateTokenResponse generateAuthToken() {
        GenerateTokenPayload payload = new GenerateTokenPayload(userName, password);
        return Endpoints.generateUserToken(payload);
    }

    private GetUserAccountResponse getUserAccount() {
        return
                given()
                        .spec(BookstoreSpecs.getBaseRequestSpec())
                        .spec(BookstoreSpecs.getAuthRequestSpec(authTokenInfo.token))
                .when()
                        .pathParam("UUID", userID)
                        .get("/Account/v1/User/{UUID}")
                .then().log().body()
                        .statusCode(200)
                        .assertThat()
                        .body("username", is(userName))
                .extract()
                        .response()
                        .as(GetUserAccountResponse.class);
    }
}
