package io.github.allioli.bookstore.model.responses;

import java.util.List;

public class GetBooksResponse {

    public List<Book> books;

    public GetBooksResponse(List<Book> books) {
        this.books = books;
    }
}
