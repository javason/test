package com.admin.tool.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomException {

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(HttpStatus.NOT_FOUND,
              String.format("%s not found with %s: '%s'", resource, field, value));
    }

    public ResourceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
