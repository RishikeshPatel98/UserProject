package com.simpleshowassignment.userProject.exception;

public class UserNotFoundException extends UserProjectException{

    public UserNotFoundException(ErrorResponse errorResponse) {
        super(errorResponse);
    }


}
