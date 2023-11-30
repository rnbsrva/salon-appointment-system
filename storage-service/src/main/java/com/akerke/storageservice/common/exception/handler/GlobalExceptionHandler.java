package com.akerke.storageservice.common.exception.handler;

import com.akerke.storageservice.common.exception.BucketInitializerException;
import com.akerke.storageservice.common.exception.FileOperationException;
import com.akerke.storageservice.common.exception.ImageMetadataNotFoundException;
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
     * Handles the BucketInitializerException and returns a ProblemDetail response with status 500.
     *
     * @param e the BucketInitializerException
     * @return the ProblemDetail response
     */
    @ExceptionHandler(BucketInitializerException.class)
    ProblemDetail handle(BucketInitializerException e) {
        return withDetails(e, 500);
    }

    /**
     * Handles the FileOperationException and returns a ProblemDetail response with status 500.
     *
     * @param e the FileOperationException
     * @return the ProblemDetail response
     */
    @ExceptionHandler(FileOperationException.class)
    ProblemDetail handle(FileOperationException e) {
        return withDetails(e, 500);
    }

    @ExceptionHandler(FileOperationException.class)
    ProblemDetail handle(ImageMetadataNotFoundException e) {
        return withDetails(e, 404);
    }

    private ProblemDetail withDetails(RuntimeException e, int sc) {
        log.error("created problem details message={} status_code={}", e.getMessage(), sc);
        return ProblemDetail.forStatusAndDetail(HttpStatusCode.valueOf(sc), e.getMessage());
    }
}
