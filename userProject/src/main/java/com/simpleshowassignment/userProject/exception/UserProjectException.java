package com.simpleshowassignment.userProject.exception;

public class UserProjectException extends RuntimeException{

    private ErrorResponse errorResponse;

    public UserProjectException (ErrorResponse errorResponse) {
        super(errorResponse.getErrorMessage());
        this.errorResponse = errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }
}
