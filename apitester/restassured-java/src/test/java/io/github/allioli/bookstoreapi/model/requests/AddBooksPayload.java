package io.github.allioli.bookstoreapi.model.requests;

import java.util.ArrayList;
import java.util.List;

public class AddBooksPayload {
    public String userId;
    public List<ISBN> collectionOfIsbns;


    public AddBooksPayload(String userId, ISBN isbn) {
        this.userId = userId;
        collectionOfIsbns = new ArrayList<>();
        collectionOfIsbns.add(isbn);
    }
}
