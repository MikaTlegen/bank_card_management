package com.exception.Transfer;

public class TransferToSameCardException extends RuntimeException {
    public TransferToSameCardException(String message) {
        super(message);
    }
}