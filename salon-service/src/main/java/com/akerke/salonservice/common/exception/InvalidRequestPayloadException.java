package com.akerke.salonservice.common.exception;

/**
 * Exception thrown when an invalid request payload is encountered.
 */
public class InvalidRequestPayloadException extends RuntimeException {

    /**
     * Constructs a new InvalidRequestPayloadException with the specified error message.
     *
     * @param errorMessage the error message describing the invalid request payload
     */
    public InvalidRequestPayloadException(String errorMessage) {
        super(errorMessage);
    }
}
