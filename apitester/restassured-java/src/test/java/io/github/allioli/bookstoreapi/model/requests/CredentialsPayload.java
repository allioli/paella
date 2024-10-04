package io.github.allioli.bookstoreapi.model.requests;

public class CredentialsPayload {
    public String userName;
    public String password;

    public CredentialsPayload(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
}



