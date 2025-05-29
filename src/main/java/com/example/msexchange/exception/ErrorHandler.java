package com.example.msexchange.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.example.msexchange.exception.ErrorMessage.CUSTOM_FEIGN_EXCEPTION;
import static com.example.msexchange.exception.ErrorMessage.UNEXPECTED_ERROR;
import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ErrorResponse handle(Exception ex) {
        log.error("Exception: ", ex);
        return new ErrorResponse(ex.getMessage(), 500);

    }

    @ExceptionHandler(CustomFeignException.class)
    @ResponseStatus(BAD_REQUEST)
    public ErrorResponse handle(CustomFeignException ex) {
        log.error("CustomFeignException: ", ex);
        return new ErrorResponse(ex.getMessage(),ex.status);
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(CONFLICT)
    public ErrorResponse handle(AlreadyExistException ex) {
        log.error("CustomFeignException: ", ex);
        return new ErrorResponse(ex.getMessage(),ex.status);
    }

    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handle(NotFoundException ex){
        log.error("NotFoundException: ",ex);
        return new ErrorResponse(ex.getMessage(), ex.status);
    }




}