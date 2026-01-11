package com.example.msexchange.exception;

public class AlreadyExistException extends RuntimeException{
    int status;

    public AlreadyExistException(String message, int status) {
        super(message);
        this.status = status;
    }
}
