package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;

@Value
public class CredentialDto implements Serializable {
    private String login;
    private String password;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public CredentialDto(@JsonProperty("login") String login,
                         @JsonProperty("password") String password) {
        this.login = login;
        this.password = password;
    }
}
