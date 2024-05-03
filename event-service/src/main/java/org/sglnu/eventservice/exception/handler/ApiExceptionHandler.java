package org.sglnu.eventservice.exception.handler;

import jakarta.validation.ConstraintViolation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sglnu.eventservice.dto.ErrorDetail;
import org.sglnu.eventservice.exception.EventIsFullException;
import org.sglnu.eventservice.exception.EventNotFoundException;
import org.sglnu.eventservice.exception.UserIsAlreadySubscribed;
import org.sglnu.eventservice.exception.UserIsNotSubscribedToEvent;
import org.sglnu.eventservice.mapper.ErrorDetailMapper;
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

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@Slf4j
@RestControllerAdvice
@RequiredArgsConstructor
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final ErrorDetailMapper errorDetailMapper;

    @ExceptionHandler({ EventNotFoundException.class })
    @ResponseStatus(NOT_FOUND)
    public ProblemDetail handleEventNotFoundException(EventNotFoundException ex, WebRequest request) {
        return getProblemDetail(
                NOT_FOUND,
                URI.create("about:blank"),
                "Event not found!",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.from(ex))
        );
    }

    @ExceptionHandler({ EventIsFullException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleEventIsFullException(EventIsFullException ex, WebRequest request) {
        return getProblemDetail(
                HttpStatus.BAD_REQUEST,
                URI.create("about:blank"),
                "Event is full!",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.from(ex))
        );
    }

    @ExceptionHandler({ UserIsAlreadySubscribed.class })
    @ResponseStatus(BAD_REQUEST)
    public ProblemDetail handleUserIsAlreadySubscribed(UserIsAlreadySubscribed ex, WebRequest request) {
        return getProblemDetail(
                HttpStatus.BAD_REQUEST,
                URI.create("about:blank"),
                "User is already subscribed!",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.from(ex))
        );
    }

    @ExceptionHandler({ UserIsNotSubscribedToEvent.class })
    @ResponseStatus(NOT_FOUND)
    public ProblemDetail handleUserIsNotSubscribedToEvent(UserIsNotSubscribedToEvent ex, WebRequest request) {
        return getProblemDetail(
                NOT_FOUND,
                URI.create("about:blank"),
                "User is not subscribed to event!",
                URI.create(((ServletWebRequest) request).getRequest().getRequestURI()),
                List.of(errorDetailMapper.from(ex))
        );
    }

    private ProblemDetail getProblemDetail(HttpStatus httpStatus, URI type, String title, URI instance,
                                           List<ErrorDetail> details) {
        ProblemDetail problemDetail = ProblemDetail.forStatus(httpStatus);
        problemDetail.setType(type);
        problemDetail.setTitle(title);
        problemDetail.setInstance(instance);
        problemDetail.setProperty("detail", details);
        return problemDetail;
    }

}
