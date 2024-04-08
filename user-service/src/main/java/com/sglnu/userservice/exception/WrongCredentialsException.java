package com.sglnu.userservice.exception;

import com.sglnu.commondatamodel.userdto.RegisterRequest;
import com.sglnu.commondatamodel.userdto.UpdateUserRequest;
import lombok.Getter;

@Getter
public class WrongCredentialsException extends RuntimeException{

    private final String title;
    private final Object credentials;

    public WrongCredentialsException(String title, RegisterRequest credentials) {
        this.title = title;
        this.credentials = credentials;
    }

    public WrongCredentialsException(String title, UpdateUserRequest credentials) {
        this.title = title;
        this.credentials = credentials;
    }

}
