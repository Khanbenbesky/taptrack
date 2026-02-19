package com.taptrack.employeeservice.employee.exception;

import com.taptrack.employeeservice.employee.dto.ErrorCodeDto;
import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

    public BadRequestException(String message) {
        super(ErrorCodeDto.BAD_REQUEST, HttpStatus.BAD_REQUEST, message);
    }
}
