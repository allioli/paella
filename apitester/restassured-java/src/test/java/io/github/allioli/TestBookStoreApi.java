package io.github.allioli;

import io.github.allioli.bookstoreapi.model.responses.GetBooksResponseBody;
import io.github.allioli.bookstoreapi.services.AccountAuthService;
import io.github.allioli.bookstoreapi.services.AccountService;
import io.github.allioli.bookstoreapi.model.requests.AddBooksPayload;
import io.github.allioli.bookstoreapi.model.requests.GenerateTokenPayload;
import io.github.allioli.bookstoreapi.model.requests.ISBN;
import io.github.allioli.bookstoreapi.model.responses.Book;
import io.github.allioli.bookstoreapi.model.responses.GenerateTokenResponseBody;
import io.github.allioli.bookstoreapi.model.responses.GetUserAccountResponseBody;
import io.github.allioli.bookstoreapi.services.BookStoreService;
import io.github.allioli.bookstoreapi.specs.BookstoreSpecs;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


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

    @Test(description = "Should list all books")
    public void listAllBooks() {

        BookStoreService bookStoreService = new BookStoreService();
        Response response = bookStoreService.getAllBooks();
        response
            .then()
                .assertThat()
                .body("size()", greaterThan(0))
                .body("books[0]", hasKey("isbn"))
                .body("books[0].pages", greaterThan(0));
    }

    @Test(description = "Should validate response schema for GET books method")
    public void checkGetBooksContract() {

        BookStoreService bookStoreService = new BookStoreService();
        Response response = bookStoreService.getAllBooks();
        response
            .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/books_v1_schema.json"));

    }

    @Test(description = "Should add book to user reading list")
    public void addBookToReadingList() {

        String bookIsbn = "9781593277574";

        // Add book to user account
        BookStoreService bookStoreService = new BookStoreService(authToken);
        AddBooksPayload payload = new AddBooksPayload(userID, new ISBN(bookIsbn));
        bookStoreService.addBookToUserAccount(payload);

        // Assert that expected book was added to user account
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

    @Test(description = "Should remove all books from user reading list")
    public void removeAllBooksFromReadingList() {

        // Remove all books from user account
        BookStoreService bookStoreService = new BookStoreService(authToken);
        bookStoreService.removeAllBooksFromUserAccount(userID);

        // Assert that user account has no books
        GetUserAccountResponseBody getUserAccountResponseBody = this.getUserAccount();
        Assert.assertTrue(getUserAccountResponseBody.books.isEmpty());

    }

    private void authenticateUserAndSaveAuthToken() {
        AccountAuthService accountAuthService = new AccountAuthService();
        GenerateTokenPayload payload = new GenerateTokenPayload(userName, password);

        Response response = accountAuthService.generateUserToken(payload);
        GenerateTokenResponseBody tokenInfo = response.as(GenerateTokenResponseBody.class);
        authToken = tokenInfo.token;
    }

    private GetUserAccountResponseBody getUserAccount() {
        AccountService accountService = new AccountService(authToken);
        Response response = accountService.getUserAccount(userID);
        return response.as(GetUserAccountResponseBody.class);
    }
}
