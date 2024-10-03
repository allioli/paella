package io.github.allioli.bookstore.model.responses;

import java.util.List;

public class GetUserAccountResponseBody {
    public String userId;
    public String username;
    public List<Book> books;
}
