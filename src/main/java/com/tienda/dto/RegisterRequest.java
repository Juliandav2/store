package com.tienda.dto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RegisterRequest  {

    private final String username;
    private final String password;
    private final String role;

    @JsonCreator
    public RegisterRequest (@JsonProperty ("username") String username, @JsonProperty ("password") String password, @JsonProperty ("role") String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername () {
        return username;
    }

    public String getPassword () {
        return password;
    }

    public String getRole () {
        return role;
    }

}



