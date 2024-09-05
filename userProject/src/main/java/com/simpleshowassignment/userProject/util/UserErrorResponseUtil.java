package com.simpleshowassignment.userProject.util;

import com.simpleshowassignment.userProject.exception.ErrorResponse;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class UserErrorResponseUtil {

    public static ErrorResponse buildErrorResponse(String message, String path) {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .httpStatus(HttpStatus.BAD_REQUEST.value())
                .path(path)
                .errorMessage(message)
                .build();
    }
}
