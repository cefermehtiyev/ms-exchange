package com.example.msexchange.exception;

import feign.FeignException;
import feign.Request;

import java.util.Collection;
import java.util.Map;

public class UnauthorizedException extends RuntimeException {
    int status;

    public UnauthorizedException(String message, int status) {
        super(message);
        this.status = status;
    }
}
