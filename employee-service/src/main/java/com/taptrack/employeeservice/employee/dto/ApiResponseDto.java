package com.taptrack.employeeservice.employee.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public final class ApiResponseDto<T> {

    private final boolean success;
    private final String message;
    private final T data;
    private final String errorCode;
    private final LocalDateTime timestamp;

    // Private constructor to enforce factory usage
    private ApiResponseDto(boolean success, String message, T data, String errorCode, LocalDateTime timestamp) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.errorCode = errorCode;
        this.timestamp = timestamp;
    }

    // Success with data and message
    public static <T> ApiResponseDto<T> success(T data, String message) {
        return new ApiResponseDto<>(
                true,
                message != null ? message : "Request successful",
                data,
                null,
                LocalDateTime.now()
        );
    }

    // Success with data only
    public static <T> ApiResponseDto<T> success(T data) {
        return success(data, "Request successful");
    }

    // Success without data
    public static <T> ApiResponseDto<T> successMessage(String message) {
        return new ApiResponseDto<>(
                true,
                message,
                null,
                null,
                LocalDateTime.now()
        );
    }

    // Error response
    public static <T> ApiResponseDto<T> error(String message, String errorCode) {
        return new ApiResponseDto<>(
                false,
                message != null ? message : "Request failed",
                null,
                errorCode,
                LocalDateTime.now()
        );
    }

    // Getters only (immutable class)

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
