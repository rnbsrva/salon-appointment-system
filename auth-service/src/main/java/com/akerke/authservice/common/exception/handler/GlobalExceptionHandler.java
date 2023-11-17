package com.akerke.authservice.common.exception.handler;

import com.akerke.authservice.common.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling specific exceptions and returning appropriate responses.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handles the EmailRegisteredYetException and returns a ProblemDetail response with status 400.
     *
     * @param e the EmailRegisteredYetException
     * @return the ProblemDetail response
     */
    @ExceptionHandler(EmailRegisteredYetException.class)
    ProblemDetail handle(EmailRegisteredYetException e) {
        return withDetails(e, 400);
    }

    /**
     * Handles the PhoneNumberRegisteredYetException and returns a ProblemDetail response with status 400.
     *
     * @param e the PhoneNumberRegisteredYetException
     * @return the ProblemDetail response
     */
    @ExceptionHandler(PhoneNumberRegisteredYetException.class)
    ProblemDetail handle(PhoneNumberRegisteredYetException e) {
        return withDetails(e, 400);
    }

    /**
     * Handles the EntityNotFoundException and returns a ProblemDetail response with status 404.
     *
     * @param e the EntityNotFoundException
     * @return the ProblemDetail response
     */
    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handle(EntityNotFoundException e) {
        return withDetails(e, 404);
    }

    private ProblemDetail withDetails(RuntimeException e, int sc) {
        log.error("created problem details message={} status_code={}", e.getMessage(), sc);
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(sc), e.getMessage());
    }

    /**
     * Handles the InvalidRequestPayloadException and returns a ProblemDetail response with status 400.
     *
     * @param e the InvalidRequestPayloadException
     * @return the ProblemDetail response
     */
    @ExceptionHandler(InvalidRequestPayloadException.class)
    ProblemDetail handle(InvalidRequestPayloadException e) {
        return withDetails(e, 400);
    }

    /**
     * Handles the InvalidCredentialsException and returns a ProblemDetail response with status 403.
     *
     * @param e the InvalidCredentialsException
     * @return the ProblemDetail response
     */
    @ExceptionHandler(InvalidCredentialsException.class)
    ProblemDetail handle(InvalidCredentialsException e) {
        return withDetails(e, 403);
    }

}
