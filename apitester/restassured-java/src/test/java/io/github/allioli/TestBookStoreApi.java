package io.github.allioli;

import io.github.allioli.bookstoreapi.IGenericResponse;
import io.github.allioli.bookstoreapi.config.ApiTestConfiguration;
import io.github.allioli.bookstoreapi.services.AccountAuthV1Service;
import io.github.allioli.bookstoreapi.services.AccountV1Service;
import io.github.allioli.bookstoreapi.model.requests.AddBooksPayload;
import io.github.allioli.bookstoreapi.model.requests.GenerateTokenPayload;
import io.github.allioli.bookstoreapi.model.requests.ISBN;
import io.github.allioli.bookstoreapi.model.responses.Book;
import io.github.allioli.bookstoreapi.model.responses.AuthTokenData;
import io.github.allioli.bookstoreapi.model.responses.UserAccountData;
import io.github.allioli.bookstoreapi.services.BookStoreV1Service;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class TestBookStoreApi {

    private final ApiTestConfiguration cfg = ConfigFactory.create(ApiTestConfiguration.class);
    private String authToken = null;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = cfg.baseURI();

        if (authToken == null) {
            authenticateUserAndSaveAuthToken();
        }
    }

    @Test(description = "Should list all books")
    public void listAllBooks() {

        BookStoreV1Service bookStoreService = new BookStoreV1Service();
        Response response = bookStoreService.getAllBooks().getRestAssuredResponse();
        response
            .then()
                .assertThat()
                .body("books.size()", equalTo(8))
                .body("books[0]", hasKey("isbn"))
                .body("books[0].pages", greaterThan(0));
    }

    @Test(description = "Should validate response schema for GET books method")
    public void checkGetBooksContract() {

        BookStoreV1Service bookStoreService = new BookStoreV1Service();
        Response response = bookStoreService.getAllBooks().getRestAssuredResponse();
        response
            .then()
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schemas/books_v1_schema.json"));

    }

    @Test(description = "Should add book to user reading list")
    public void addBookToReadingList() {

        // GIVEN registered user and a book she likes
        String bookIsbn = "9781593277574";
        String userID = cfg.userID();

        // WHEN she adds a book to the collection in her user account
        BookStoreV1Service bookStoreService = new BookStoreV1Service(authToken);
        AddBooksPayload payload = new AddBooksPayload(userID, new ISBN(bookIsbn));
        bookStoreService.addBookToUserAccount(payload);

        // THEN the expected book is added to user account
        UserAccountData userAccountData = this.getUserAccount(userID).getBodyData();
        Assert.assertFalse(userAccountData.books.isEmpty());

        boolean userBookFound = false;
        for (Book bookOfUser : userAccountData.books) {
            if (bookOfUser.isbn.equals(bookIsbn)) {
                userBookFound = true;
                break;
            }
        }
        Assert.assertTrue(userBookFound);
    }

    @Test(description = "Should remove all books from user reading list")
    public void removeAllBooksFromReadingList() {

        // GIVEN registered user
        String userID = cfg.userID();

        // WHEN she removes all books from her user account
        BookStoreV1Service bookStoreService = new BookStoreV1Service(authToken);
        bookStoreService.removeAllBooksFromUserAccount(userID);

        // THEN the book collection in her account is empty
        UserAccountData userAccountData = this.getUserAccount(userID).getBodyData();
        Assert.assertTrue(userAccountData.books.isEmpty());

    }

    private void authenticateUserAndSaveAuthToken() {
        AccountAuthV1Service accountAuthService = new AccountAuthV1Service();
        GenerateTokenPayload payload = new GenerateTokenPayload(cfg.userName(), cfg.password());

        IGenericResponse<AuthTokenData> response = accountAuthService.generateUserToken(payload);
        authToken = response.getBodyData().token;
    }

    private IGenericResponse<UserAccountData> getUserAccount(String userID) {
        AccountV1Service accountService = new AccountV1Service(authToken);
        return accountService.getUserAccount(userID);

    }
}
