package com.iws_manager.iws_manager_api.exception.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Getter;

@Getter
@ResponseStatus(HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    private final String errorCode;
    private final String details;

    // Constructor for simple message
    public ConflictException(String message) {
        super(message);
        this.errorCode = "CONFLICT";
        this.details = message;
    }

    // Constructor with custom error code and details
    public ConflictException(String errorCode, String message, String details) {
        super(message);
        this.errorCode = errorCode;
        this.details = details;
    }
}