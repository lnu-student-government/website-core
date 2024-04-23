package org.sglnu.userservice.exception;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PostExeption extends RuntimeException {

    private final  PostErrorResult postErrorResult;

}
