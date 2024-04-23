package org.sglnu.userservice.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum PostErrorResult {

    POST_NOT_FOUND(HttpStatus.SC_NOT_FOUND,"Post not found"),
    PASSWORDS_DO_NOT_MATCH(HttpStatus.SC_BAD_REQUEST, "Passwords do not match"),
    INVALID_EMAIL(HttpStatus.SC_BAD_REQUEST, "Incorrect password or email"),
    PASSWORD_INCORRECT(HttpStatus.SC_BAD_REQUEST,"Incorrect password or email")
    ;

    private final int  httpStatus;
    private final String message;

}
