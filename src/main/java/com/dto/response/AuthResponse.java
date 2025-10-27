package com.dto.response;

import com.enums.UserRole;

public class AuthResponse {
    private String token;
    private UserRole role;
    private String username;
    private String type = "Bearer";
    private Long userId;

    public AuthResponse() {
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public UserRole getRole() {
        return role;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    public Long getUserId() {
        return userId;
    }

    public AuthResponse(String token, UserRole role, String username, String type, Long userId) {
        this.token = token;
        this.role = role;
        this.username = username;
        this.type = type;
        this.userId = userId;
    }
}
