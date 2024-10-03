package io.github.allioli;

import io.github.allioli.bookstore.services.AccountAuthService;
import io.github.allioli.bookstore.services.AccountService;
import io.github.allioli.bookstore.model.requests.AddBooksPayload;
import io.github.allioli.bookstore.model.requests.GenerateTokenPayload;
import io.github.allioli.bookstore.model.requests.ISBN;
import io.github.allioli.bookstore.model.responses.Book;
import io.github.allioli.bookstore.model.responses.GenerateTokenResponseBody;
import io.github.allioli.bookstore.model.responses.GetUserAccountResponseBody;
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
    private String authToken = null;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://bookstore.toolsqa.com";

        if (authToken == null) {
            authenticateUserAndSaveAuthToken();
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
                .spec(BookstoreSpecs.getAuthRequestSpec(authToken))
        .when()
                .body(payload)
                .post("/BookStore/v1/Books")
        .then().log().ifValidationFails()
                .statusCode(201);

        GetUserAccountResponseBody getUserAccountResponseBody = getUserAccount();
        Assert.assertFalse(getUserAccountResponseBody.books.isEmpty());

        boolean userBookFound = false;
        for (Book bookOfUser : getUserAccountResponseBody.books) {
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
                .spec(BookstoreSpecs.getAuthRequestSpec(authToken))
        .when()
                .queryParam("UserId", userID)
                .delete("/BookStore/v1/Books")
        .then().log().ifValidationFails()
                .statusCode(204);

        GetUserAccountResponseBody getUserAccountResponseBody = this.getUserAccount();

        Assert.assertTrue(getUserAccountResponseBody.books.isEmpty());
        Assert.assertEquals(getUserAccountResponseBody.username, userName);
    }

    private void authenticateUserAndSaveAuthToken() {
        GenerateTokenPayload payload = new GenerateTokenPayload(userName, password);
        Response response = AccountAuthService.generateUserToken(payload);
        GenerateTokenResponseBody tokenInfo = response.getBody().as(GenerateTokenResponseBody.class);
        authToken = tokenInfo.token;
    }

    private GetUserAccountResponseBody getUserAccount() {
        AccountService accountService = new AccountService(authToken);
        Response response = accountService.getUserAccount(userID);
        return response.as(GetUserAccountResponseBody.class);
    }
}
