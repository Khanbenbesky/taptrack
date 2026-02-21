package com.taptrack.employeeservice.exception;

import com.taptrack.employeeservice.employee.dto.ErrorCodeDto;
import org.springframework.http.HttpStatus;

public class DuplicateResourceException extends BaseException  {
    public DuplicateResourceException(String message) {
        super(
                ErrorCodeDto.DUPLICATE_RESOURCE,
                HttpStatus.CONFLICT,
                message
        );
    }
}
