package io.github.allioli.bookstore.model.requests;

public class GenerateTokenPayload {
    public String userName;
    public String password;

    public GenerateTokenPayload(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}



