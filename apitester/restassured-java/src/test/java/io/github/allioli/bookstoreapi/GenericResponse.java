package io.github.allioli.bookstoreapi;

import io.restassured.response.Response;

public class GenericResponse<T> implements IGenericResponse<T> {
    private T data;
    private final Response response;

    public GenericResponse(Class<T> t, Response response) {
        this.response = response;
        try{
            this.data = t.getDeclaredConstructor().newInstance();
        } catch (Exception e){
            throw new RuntimeException("Missing default constructor in Response POJO of class: " + t.getTypeName());
        }
        // Let's cast the body data at instantiation time, so that we only perform the cast once
        // Throws RuntimeException if the body data is invalid
        this.data = (T) response.getBody().as(data.getClass());
    }

    public Response getRestAssuredResponse() {
        return response;
    }

    public T getBodyData() {
        return data;
    }
}
