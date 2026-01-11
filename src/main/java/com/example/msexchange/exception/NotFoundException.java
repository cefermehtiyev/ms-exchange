package com.example.msexchange.exception;

public class NotFoundException extends RuntimeException {
    int status;

    public NotFoundException(String message, int status) {
        super(message);
        this.status = status;
    }
}
