package com.exception.card;

public class CardNumberNotFoundException extends RuntimeException {
    public CardNumberNotFoundException(String message) {
        super(message);
    }
}