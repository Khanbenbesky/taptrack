package com.taptrack.employeeservice.employee.exception;

import com.taptrack.employeeservice.employee.dto.ErrorCodeDto;
import org.springframework.http.HttpStatus;

public class ServiceUnavailableException extends BaseException {
    public ServiceUnavailableException(ErrorCodeDto errorCodeDto, HttpStatus status, String message) {
        super(ErrorCodeDto.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE, message);
    }
}
