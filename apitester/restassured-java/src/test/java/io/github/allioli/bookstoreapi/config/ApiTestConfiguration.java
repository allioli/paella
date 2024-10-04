package io.github.allioli.bookstoreapi.config;

import org.aeonbits.owner.Config;

@Config.Sources({"classpath:bookstoreapi.properties"})
public interface ApiTestConfiguration extends Config {

    @Key("api.base.uri")
    String baseURI();

    @Key("api.user.name")
    String userName();

    @Key("api.password")
    String password();

    @Key("api.user.id")
    String userID();
}
