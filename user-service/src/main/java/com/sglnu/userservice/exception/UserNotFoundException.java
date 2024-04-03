package com.sglnu.userservice.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {

    }

    public UserNotFoundException(String message) {
        super(message);
    }

}
