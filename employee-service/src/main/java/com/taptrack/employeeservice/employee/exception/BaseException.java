package com.taptrack.employeeservice.employee.exception;

import com.taptrack.employeeservice.employee.dto.ErrorCodeDto;
import org.springframework.http.HttpStatus;

public class BaseException extends RuntimeException {

    private final ErrorCodeDto errorCodeDto;
    private final HttpStatus status;

    public BaseException(ErrorCodeDto errorCodeDto, HttpStatus status) {
        super(errorCodeDto.getMessage());
        this.errorCodeDto = errorCodeDto;
        this.status = status;
    }

    public BaseException(ErrorCodeDto errorCodeDto, HttpStatus status, String message) {
        super(message);
        this.errorCodeDto = errorCodeDto;
        this.status = status;
    }

    public ErrorCodeDto getErrorCodeDto() {
        return errorCodeDto;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
