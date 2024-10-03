package io.github.allioli.bookstore.model.responses;

public class GenerateTokenResponse {

    public String token;
    public String expires;
    public String status;
    public String result;

    public GenerateTokenResponse() {
    }

    public GenerateTokenResponse(String token, String expires, String status, String result) {
        this.token = token;
        this.expires = expires;
        this.status = status;
        this.result = result;
    }
}
