package com.listManager.dto;

import com.fasterxml.jackson.databind.JsonNode;

public class UserLoginDto {
    private final String username;
    private final String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserLoginDto(JsonNode json) {
        username = json.get("username").asText();
        password = json.get("password").asText();
    }

    public UserLoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }
}