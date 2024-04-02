package com.sglnu.userservice.exception;

import lombok.Getter;

import java.util.Map;

public class WrongCredentialsException extends RuntimeException{

    @Getter
    private final Map<String, String> credentials;
    @Getter
    private final String title;

    public WrongCredentialsException(String title, Map<String, String> credentials) {
        this.title = title;
        this.credentials = credentials;
    }

}
