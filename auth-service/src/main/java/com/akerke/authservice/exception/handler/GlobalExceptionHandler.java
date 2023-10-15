package com.akerke.authservice.exception.handler;

import com.akerke.authservice.exception.EmailRegisteredYetException;
import com.akerke.authservice.exception.EntityNotFoundException;
import com.akerke.authservice.exception.InvalidCredentialsException;
import com.akerke.authservice.exception.InvalidRequestPayloadException;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.function.BiFunction;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(InvalidRequestPayloadException.class)
    ProblemDetail handle(InvalidRequestPayloadException e) {
        return withDetails.apply(e, 400);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    ProblemDetail handle(EntityNotFoundException e) {
        return withDetails.apply(e, 404);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    ProblemDetail handle(InvalidCredentialsException e) {
        return withDetails.apply(e, 403);
    }

    @ExceptionHandler(EmailRegisteredYetException.class)
    ProblemDetail handle(EmailRegisteredYetException e) {
        return withDetails.apply(e, 400);
    }

    private final BiFunction<RuntimeException, Integer, ProblemDetail>
            withDetails = (e, sc) -> ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(sc), e.getMessage());
}
