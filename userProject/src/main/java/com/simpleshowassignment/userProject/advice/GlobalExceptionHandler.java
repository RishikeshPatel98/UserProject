package com.simpleshowassignment.userProject.advice;

import com.simpleshowassignment.userProject.exception.ErrorResponse;
import com.simpleshowassignment.userProject.exception.UserNotFoundException;
import com.simpleshowassignment.userProject.exception.UserProjectException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

        private UserProjectException userProjectException;

    @ExceptionHandler(UserNotFoundException.class)
    ResponseEntity<ErrorResponse> userNotFoundHandler(UserNotFoundException ex) {
        return new ResponseEntity<>(ex.getErrorResponse(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        return new ResponseEntity<>(extractValidationError(ex), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("Parameter '%s' should be of type '%s'.",
                ex.getName(), ex.getRequiredType().getSimpleName());

        return new ResponseEntity<>(buildErrorResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }

    private ErrorResponse buildErrorResponse(String errorMessage) {
        return ErrorResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errorMessage(errorMessage)
                .build();
    }

    private ErrorResponse extractValidationError(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors().stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .findFirst()
                .orElse(ex.getMessage());

        return ErrorResponse.builder()
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .timestamp(LocalDateTime.now())
                .errorMessage(errorMessage)
                .build();
    }

}
