package com.iws_manager.iws_manager_api.exception.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    private final String errorCode;
    private final String resourceId;
    private final String resourceType;

    // Basic constructor
    public NotFoundException(String message) {
        super(message);
        this.errorCode = "NOT_FOUND";
        this.resourceId = null;
        this.resourceType = null;
    }

    // Detailed constructor
    public NotFoundException(String resourceType, String resourceId) {
        super(String.format("%s not found with ID: %s", resourceType, resourceId));
        this.errorCode = "RESOURCE_NOT_FOUND";
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }

    // Custom error code constructor
    public NotFoundException(String errorCode, String message, String resourceType, String resourceId) {
        super(message);
        this.errorCode = errorCode;
        this.resourceType = resourceType;
        this.resourceId = resourceId;
    }
}