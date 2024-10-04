package io.github.allioli.bookstoreapi;

import io.restassured.response.Response;

public interface IGenericResponse<T> {

    public T getBodyData();

    public Response getRestAssuredResponse();
}
