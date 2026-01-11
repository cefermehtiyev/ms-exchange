package com.example.msexchange.exception;

public class InsufficientBalanceException extends RuntimeException {
    int status;
    public InsufficientBalanceException(String message, int status) {
        super(message);
        this.status = status;
    }
}
