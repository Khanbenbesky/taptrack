package com.taptrack.employeeservice.employee.dto;

import org.springframework.http.HttpStatus;

public enum ErrorCodeDto {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Resource not found"),
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Bad request"),
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Validation failed"),
    SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE, "Service unavailable"),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "Resource already exists"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "Service unavailable");

    private final HttpStatus status;
    private final String message;

    ErrorCodeDto(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
