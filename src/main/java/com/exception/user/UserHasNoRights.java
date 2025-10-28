package com.exception.user;

public class UserHasNoRights extends RuntimeException {
    public UserHasNoRights(String message) {
        super(message);
    }
}
