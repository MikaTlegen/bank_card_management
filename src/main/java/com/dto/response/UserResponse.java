package com.dto.response;

import com.enums.UserRole;

public class UserResponse {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String fullName;
    private UserRole role;
    private int cardCount;

    public UserResponse() {
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean isActive() {
        return isActive;
    }

    private boolean isActive;

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserName(String userName) {
        this.username = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public UserRole getRole() {
        return role;
    }


    public UserResponse(Long id, String username, String email, String fullName, UserRole role, int cardCount, boolean isActive) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.cardCount = cardCount;
        this.isActive = isActive;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }
}
