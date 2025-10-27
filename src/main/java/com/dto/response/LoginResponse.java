package com.dto.response;

public class LoginResponse {
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public LoginResponse(String token) {
        this.token = token;
    }
}
