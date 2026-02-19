package com.taptrack.employeeservice.employee.exception;

import com.taptrack.employeeservice.employee.dto.ErrorCodeDto;
import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends BaseException  {
    public DuplicateResourceException(ErrorCodeDto errorCodeDto, HttpStatus status, String message) {
        super(ErrorCodeDto.DUPLICATE_RESOURCE, HttpStatus.CONFLICT, message);
    }
}
