package com.exception.Transfer;

public class ExpiredCardTransferException extends RuntimeException {
    public ExpiredCardTransferException(String message) {
        super(message);
    }
}