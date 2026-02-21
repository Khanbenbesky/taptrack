package com.taptrack.employeeservice.exception;

import com.taptrack.employeeservice.employee.dto.ErrorCodeDto;
import com.taptrack.employeeservice.employee.dto.ErrorResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponseDto> handleBaseException(
            BaseException ex,
            HttpServletRequest request) {

        logger.error("Business exception occurred: {}", ex.getMessage());
        ErrorResponseDto response = ErrorResponseDto.builder()
                .success(false)
                .errorCode(ex.getErrorCodeDto().getMessage())
                .message(ex.getMessage())
                .status(ex.getStatus().value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, ex.getStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidation(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));

        ErrorResponseDto response = ErrorResponseDto.builder()
                .success(false)
                .errorCode(ErrorCodeDto.VALIDATION_ERROR.getStatus().toString())
                .message(message)
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleException(
            Exception ex,
            HttpServletRequest request) {

        logger.error("Unhandled exception", ex);

        ErrorResponseDto response = ErrorResponseDto.builder()
                .success(false)
                .errorCode(ErrorCodeDto.INTERNAL_SERVER_ERROR.getMessage())
                .message("Something went wrong")
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .path(request.getRequestURI())
                .timestamp(LocalDateTime.now())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
