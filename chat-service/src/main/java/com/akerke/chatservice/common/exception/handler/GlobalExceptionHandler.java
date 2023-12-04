package com.akerke.chatservice.common.exception.handler;

import com.akerke.chatservice.common.exception.EntityNotFoundException;
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
}
