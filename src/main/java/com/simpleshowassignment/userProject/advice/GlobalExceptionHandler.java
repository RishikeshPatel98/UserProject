package com.simpleshowassignment.userProject.advice;

import com.simpleshowassignment.userProject.exception.ErrorResponse;
import com.simpleshowassignment.userProject.exception.UserNotFoundException;
import com.simpleshowassignment.userProject.exception.UserProjectException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> handleDataIntegrityViolationException(DataIntegrityViolationException ex){
        String errorMessage = "Data integrity violation: " + extractRootCauseMessage(ex);
        log.error("Data integrity violation: {}", errorMessage);

        return new ResponseEntity<>(buildErrorResponse(errorMessage), HttpStatus.CONFLICT);
    }
    private String extractRootCauseMessage(Throwable throwable) {
        Throwable rootCause = throwable;
        while (rootCause.getCause() != null && rootCause != rootCause.getCause()) {
            rootCause = rootCause.getCause();
        }
        return rootCause.getMessage();
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
