package com.exception.user;

public class SelfDeletionForbiddenException extends RuntimeException {
    public SelfDeletionForbiddenException() {
        super("You cannot delete your own account");
    }
}
