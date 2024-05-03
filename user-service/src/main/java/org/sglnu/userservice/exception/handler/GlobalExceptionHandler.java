package org.sglnu.userservice.exception.handler;

import org.sglnu.userservice.dto.ErrorDetail;
import org.sglnu.userservice.exception.EmailAlreadyUsedException;
import org.sglnu.userservice.exception.PasswordMismatchException;
import org.sglnu.userservice.exception.PhoneNumberAlreadyUsedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private ProblemDetail getProblemDetail(HttpStatus httpStatus, URI type, String title, URI instance,
                                           List<ErrorDetail> details) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setType(type);
        problemDetail.setTitle(title);
        problemDetail.setInstance(instance);
        problemDetail.setProperty("detail", details);
        return problemDetail;
    }

    @ExceptionHandler({PasswordMismatchException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handlePasswordMismatchException(PasswordMismatchException ex, WebRequest request) {
        return getProblemDetail(
                HttpStatus.BAD_REQUEST,
                URI.create("about:blank"),
                "Invalid password or email",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(new ErrorDetail("password", ex.getMessage()))
        );
    }

    @ExceptionHandler({EmailAlreadyUsedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleEmailAlreadyUsedException(EmailAlreadyUsedException ex, WebRequest request) {
        return getProblemDetail(
                HttpStatus.BAD_REQUEST,
                URI.create("about:blank"),
                "Email was already used",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(new ErrorDetail("email", ex.getMessage()))
        );
    }

    @ExceptionHandler({PhoneNumberAlreadyUsedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handlePhoneNumberAlreadyUsedException(PhoneNumberAlreadyUsedException ex, WebRequest request) {
        return getProblemDetail(
                HttpStatus.BAD_REQUEST,
                URI.create("about:blank"),
                "Phone number was already used",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(new ErrorDetail("Wrong PhoneNumber", ex.getMessage()))
        );
    }

}