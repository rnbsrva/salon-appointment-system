package com.akerke.salonservice.exception;

/**
 * Exception thrown when a request class fails validation checks based on annotations.
 */
public class InvalidRequestException extends RuntimeException {
    /**
     * Constructs an InvalidRequestException with a message indicating the entity's class and a description of the error.
     *
     * @param requestClass The class that failed validation checks.
     * @param errorDescription A description of the validation error.
     */
    public InvalidRequestException(Class<?> requestClass, String errorDescription) {
        super("Invalid request for %s. Error: %s".formatted(requestClass.getSimpleName(), errorDescription));
    }
}

