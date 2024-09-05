package com.simpleshowassignment.userProject.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private int httpStatus;
    private LocalDateTime timestamp;
    private String errorMessage;
    private String path;
}
