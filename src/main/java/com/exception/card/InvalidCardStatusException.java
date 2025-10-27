package com.exception.card;

public class InvalidCardStatusException extends RuntimeException {
    public InvalidCardStatusException(String message) {
        super(message);
    }
}
