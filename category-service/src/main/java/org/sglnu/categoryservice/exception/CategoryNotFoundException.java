package org.sglnu.categoryservice.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException() {

    }

    public CategoryNotFoundException(String message) {
        super(message);
    }

}
