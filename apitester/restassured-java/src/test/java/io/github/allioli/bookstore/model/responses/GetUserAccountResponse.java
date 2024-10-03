package io.github.allioli.bookstore.model.responses;

import java.util.List;

public class GetUserAccountResponse {
    public String userId;
    public String username;
    public List<Book> books;
}
