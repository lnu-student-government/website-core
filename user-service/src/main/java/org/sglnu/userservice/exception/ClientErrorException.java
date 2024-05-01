package org.sglnu.userservice.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ClientErrorException extends RuntimeException {

    private String message;
    private int status;

}
