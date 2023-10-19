package com.akerke.salonservice.exception.handler;

import com.akerke.salonservice.exception.EntityNotFoundException;
import com.akerke.salonservice.exception.InvalidRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.function.BiFunction;

@RestControllerAdvice
public class GlobalHandler extends ResponseEntityExceptionHandler {

    /**
     * Exception handler for handling InvalidRequestException.
     *
     * @param e The InvalidRequestException to handle.
     * @return A ProblemDetail response with status code HttpStatus.BAD_REQUEST.
     */
    @ExceptionHandler(InvalidRequestException.class)
    ProblemDetail handleInvalidRequestException(InvalidRequestException e) {
        return withDetails.apply(e, HttpStatus.BAD_REQUEST);
    }

    /**
     * Exception handler for handling EntityNotFoundException.
     *
     * @param e The EntityNotFoundException to handle.
     * @return A ProblemDetail response with status code HttpStatus.NOT_FOUND.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handleEntityNotFoundException(EntityNotFoundException e) {
        return withDetails.apply(e, HttpStatus.NOT_FOUND);
    }

    /**
     * Function to create a ProblemDetail response with status and detail.
     */
    private final BiFunction<RuntimeException, HttpStatus, ProblemDetail> withDetails =
            (e, status) -> ProblemDetail.forStatusAndDetail(status, e.getMessage());

}