package org.sglnu.mediaservice.exception.handler;

import org.sglnu.mediaservice.dto.ErrorDetail;
import org.sglnu.mediaservice.exception.DetailedEntityNotFoundException;
import org.sglnu.mediaservice.mapper.ErrorDetailMapper;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorDetailMapper errorDetailMapper;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<ErrorDetail> errors = ex.getAllErrors().stream()
                .map(errorDetailMapper::toErrorDetail)
                .toList();

        return ResponseEntity.badRequest().body(getProblemDetail(
                BAD_REQUEST,
                URI.create("about:blank"),
                "Validation failed for %s".formatted(ex.getTarget().getClass().getSimpleName()),
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                errors
        ));
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.badRequest().body(getProblemDetail(
                BAD_REQUEST,
                URI.create("about:blank"),
                "Type mismatch",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.toErrorDetail(ex))
        ));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        return ResponseEntity.badRequest().body(getProblemDetail(
                BAD_REQUEST,
                URI.create("about:blank"),
                "Mandatory request parameter is missing",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.from(ex))
        ));
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Throwable cause = ex.getCause();

        if (cause instanceof JsonMappingException jsonMappingException) {
            List<JsonMappingException.Reference> path = jsonMappingException.getPath();
            String fieldName = path.get(path.size() - 1).getFieldName();
            String errorMessage = "Cannot deserialize value %s".formatted(jsonMappingException.getCause().getMessage());

            return ResponseEntity.badRequest().body(getProblemDetail(
                    BAD_REQUEST,
                    URI.create("about:blank"),
                    "Type mismatch for the field [%s]".formatted(fieldName),
                    URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                    List.of(new ErrorDetail(fieldName, errorMessage))
            ));
        }

        return super.handleHttpMessageNotReadable(ex, headers, status, request);
    }

    @ExceptionHandler(DetailedEntityNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ProblemDetail handleDetailedEntityNotFoundException(DetailedEntityNotFoundException ex, WebRequest request) {
        return getProblemDetail(
                NOT_FOUND,
                URI.create("about:blank"),
                "Entity not found!",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.from(ex))
        );
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    public ProblemDetail handleUnexpectedException(Exception ex, WebRequest request) {
        return getProblemDetail(
                INTERNAL_SERVER_ERROR,
                URI.create("about:blank"),
                "Unexpected error!",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(new ErrorDetail("request", ex.getMessage()))
        );
    }

    private ProblemDetail getProblemDetail(HttpStatus httpStatus, URI type, String title, URI instance, List<ErrorDetail> details) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setType(type);
        problemDetail.setTitle(title);
        problemDetail.setInstance(instance);
        problemDetail.setProperty("detail", details);
        return problemDetail;
    }
}