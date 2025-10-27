package com.exception;

public class SelfDeletionForbiddenException extends RuntimeException {
    public SelfDeletionForbiddenException() {
        super("You cannot delete your own account");
    }
}
