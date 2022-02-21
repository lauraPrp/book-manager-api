package com.techreturners.bookmanager.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException extends Exception {
    private final String message;


    private final HttpStatus httpStatus;
    private final ZonedDateTime zdt;

    public ApiException(String message,
                        HttpStatus httpStatus,
                        ZonedDateTime zdt) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.zdt = zdt;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ZonedDateTime getZdt() {
        return zdt;
    }

}
