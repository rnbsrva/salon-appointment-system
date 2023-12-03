package com.akerke.authserver.common.exception.handler;


import com.akerke.authserver.common.exception.EmailRegisteredYetException;
import com.akerke.authserver.common.exception.InvalidCredentialsException;
import com.akerke.authserver.common.exception.PhoneNumberRegisteredYetException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for handling specific exceptions and returning appropriate responses.
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


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

    private ProblemDetail withDetails(RuntimeException e, int sc) {
        log.error("created problem details message={} status_code={}", e.getMessage(), sc);
        return ProblemDetail.forStatusAndDetail(sc, e.getMessage());
    }

    @Data
    @AllArgsConstructor
    static class ProblemDetail {
        private String message;
        private Integer statusCode;

        public static ProblemDetail forStatusAndDetail(Integer code, String message) {
            return new ProblemDetail(message, code);
        }
    }


}
