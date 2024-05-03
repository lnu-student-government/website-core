package org.sglnu.userservice.exception.handler;

import org.sglnu.userservice.dto.ErrorDetail;
import org.sglnu.userservice.exception.FieldAlreadyUsedException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    @ExceptionHandler({FieldAlreadyUsedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleEmailAlreadyUsedException(FieldAlreadyUsedException ex, WebRequest request) {
        return getProblemDetail(
                HttpStatus.BAD_REQUEST,
                URI.create("about:blank"),
                "Email was already used",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(new ErrorDetail("email", ex.getMessage()))
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ProblemDetail>> handleValidationErrors(MethodArgumentNotValidException ex) {
        List<ErrorDetail> errors = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new ErrorDetail(fieldError.getField(), fieldError.getDefaultMessage()))
                .collect(Collectors.toList());
        ProblemDetail problemDetail = getProblemDetail(
                HttpStatus.BAD_REQUEST,
                URI.create("about:blank"),
                "Validation errors",
                null,
                errors
        );

        List<ProblemDetail> problemDetails = new ArrayList<>();
        problemDetails.add(problemDetail);
        return new ResponseEntity<>(problemDetails, HttpStatus.BAD_REQUEST);
    }

}
