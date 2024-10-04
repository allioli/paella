package io.github.allioli.bookstoreapi.model.requests;

public class GenerateTokenPayload {
    public String userName;
    public String password;

    public GenerateTokenPayload(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}



