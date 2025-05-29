package com.example.msexchange.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {

    UNEXPECTED_ERROR("Unexpected error occurred"),
    CUSTOM_FEIGN_EXCEPTION("Custom Feign Exception: "),
    CLIENT_EXCEPTION("Client exception"),
    USER_ALREADY_EXCEPTION("User Already Exception"),
    USER_NOT_FOUND("User not found"),
    UNAUTHORIZED_EXCEPTION("Username or password is incorrect");


    private final String message;
}