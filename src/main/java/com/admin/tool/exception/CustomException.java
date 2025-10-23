package com.admin.tool.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException {

    private final HttpStatus status;
    private final String message;

    public CustomException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public CustomException(String message) {
        this(HttpStatus.BAD_REQUEST, message);
    }
}
