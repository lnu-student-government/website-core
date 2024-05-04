package org.sglnu.userservice.exception.handler;

import org.sglnu.userservice.dto.ErrorDetail;
import org.sglnu.userservice.exception.FieldAlreadyUsedExceptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ProblemDetail getProblemDetail(HttpStatus httpStatus, URI type, String title, URI instance,
                                           List<ErrorDetail> details) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setType(type);
        problemDetail.setTitle(title);
        problemDetail.setInstance(instance);
        problemDetail.setProperty("detail", details);
        return problemDetail;
    }

    @ExceptionHandler({FieldAlreadyUsedExceptions.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleEmailAlreadyUsedException(FieldAlreadyUsedExceptions ex, WebRequest request) {
        List<ErrorDetail> fieldErrors = ex.getExceptions().stream()
                .map(fieldError -> new ErrorDetail(fieldError.getFieldName(), fieldError.getMessage()))
                .toList();

        return getProblemDetail(
                HttpStatus.BAD_REQUEST,
                URI.create("about:blank"),
                "Email was already used",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                fieldErrors
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ErrorDetail> fieldErrors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();

        ProblemDetail problemDetail = getProblemDetail(
                HttpStatus.BAD_REQUEST,
                URI.create("about:blank"),
                "Validation errors",
                null,
                fieldErrors
        );

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

}
