package com.taptrack.employeeservice.exception;

import com.taptrack.employeeservice.employee.dto.ErrorCodeDto;
import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends BaseException {

    public ResourceNotFoundException(String message) {
        super(ErrorCodeDto.RESOURCE_NOT_FOUND, HttpStatus.NOT_FOUND, message);
    }
}
