package io.github.allioli.bookstore.model.responses;

import java.util.List;

public class GetUserAccountResponse {
    public String userId;
    public String username;
    public List<Book> books;

    public GetUserAccountResponse() {
    }

    public GetUserAccountResponse(String userId, String username, List<Book> books) {
        this.userId = userId;
        this.username = username;
        this.books = books;
    }
}
