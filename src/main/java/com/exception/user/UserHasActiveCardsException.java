package com.exception.user;

public class UserHasActiveCardsException extends RuntimeException {
    public UserHasActiveCardsException() {
        super("Cannot delete user with active cards. Please delete cards first");
    }
}
