package com.taptrack.employeeservice.employee.dto;

import java.time.LocalDateTime;

public class ErrorResponseDto {

    private boolean success;
    private String errorCode;
    private String message;
    private int status;
    private String path;
    private LocalDateTime timestamp;

    private ErrorResponseDto(Builder builder) {
        this.success = builder.success;
        this.errorCode = builder.errorCode;
        this.message = builder.message;
        this.status = builder.status;
        this.path = builder.path;
        this.timestamp = builder.timestamp;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private boolean success;
        private String errorCode;
        private String message;
        private int status;
        private String path;
        private LocalDateTime timestamp;

        public Builder success(boolean success) {
            this.success = success;
            return this;
        }

        public Builder errorCode(String errorCode) {
            this.errorCode = errorCode;
            return this;
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder status(int status) {
            this.status = status;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public ErrorResponseDto build() {
            return new ErrorResponseDto(this);
        }
    }

    public boolean isSuccess() {
        return success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public int getStatus() {
        return status;
    }

    public String getPath() {
        return path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
