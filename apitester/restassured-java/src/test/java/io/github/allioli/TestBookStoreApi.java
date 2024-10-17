package io.github.allioli;

import io.github.allioli.bookstoreapi.IGenericResponse;
import io.github.allioli.bookstoreapi.config.ApiTestConfiguration;
import io.github.allioli.bookstoreapi.model.responses.UserCreatedAccountData;
import io.github.allioli.bookstoreapi.services.AccountAuthV1Service;
import io.github.allioli.bookstoreapi.services.AccountV1Service;
import io.github.allioli.bookstoreapi.model.requests.AddBooksPayload;
import io.github.allioli.bookstoreapi.model.requests.CredentialsPayload;
import io.github.allioli.bookstoreapi.model.requests.ISBN;
import io.github.allioli.bookstoreapi.model.responses.Book;
import io.github.allioli.bookstoreapi.model.responses.AuthTokenData;
import io.github.allioli.bookstoreapi.model.responses.UserAccountData;
import io.github.allioli.bookstoreapi.services.BookStoreV1Service;
import io.restassured.RestAssured;

import io.restassured.response.Response;
import org.aeonbits.owner.ConfigFactory;
import org.instancio.Instancio;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;
import static org.instancio.Select.field;

public class TestBookStoreApi {

    private final ApiTestConfiguration cfg = ConfigFactory.create(ApiTestConfiguration.class);
    private String authToken = null;

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = cfg.baseURI();

        if (authToken == null) {
            CredentialsPayload registeredUserCredentials = new CredentialsPayload(cfg.userName(), cfg.password());
            authToken = authenticateUserAndReturnAuthToken(registeredUserCredentials);
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
        Assert.assertTrue(isBookPresent(userAccountData.books, bookIsbn));
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

    @Test(description = "Should create new user account")
    public void createNewUser() {

        // GIVEN non-registered user with credentials
        CredentialsPayload newCredentials = Instancio.of(CredentialsPayload.class)
                .generate(field("password"), gen -> gen.text().pattern("#d#d#C#c!secret"))
                .create();

        // WHEN she registers as a new user
        AccountV1Service accountV1Service = new AccountV1Service();
        IGenericResponse<UserCreatedAccountData> response = accountV1Service.createUserAccount(newCredentials);

        // THEN there is a new user account
        UserCreatedAccountData newAccountData = response.getBodyData();
        Assert.assertNotNull(newAccountData.userID);
        Assert.assertEquals(newAccountData.username, newCredentials.userName);
        Assert.assertTrue(newAccountData.books.isEmpty());

        // And she can delete user account
        String newUserAuthToken = authenticateUserAndReturnAuthToken(newCredentials);
        AccountV1Service accountV1ServiceWithAuth = new AccountV1Service(newUserAuthToken);
        accountV1ServiceWithAuth.deleteUserAccount(newAccountData.userID);
    }

    private String authenticateUserAndReturnAuthToken(CredentialsPayload credentials) {

        AccountAuthV1Service accountAuthService = new AccountAuthV1Service();
        IGenericResponse<AuthTokenData> response = accountAuthService.generateUserToken(credentials);

        accountAuthService.checkUserAuthorised(credentials);

        return response.getBodyData().token;
    }

    private IGenericResponse<UserAccountData> getUserAccount(String userID) {
        AccountV1Service accountService = new AccountV1Service(authToken);
        return accountService.getUserAccount(userID);

    }

    private boolean isBookPresent(List<Book> books, String isbn) {
        for (Book bookOfUser : books) {
            if (bookOfUser.isbn.equals(isbn)) {
                return true;
            }
        }
        return false;
    }
}
