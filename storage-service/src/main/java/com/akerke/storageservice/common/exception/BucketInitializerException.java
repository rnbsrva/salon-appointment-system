package com.akerke.storageservice.common.exception;

/**
 * Exception thrown if an exception occurs during bucket creation in Minio.
 */
public class BucketInitializerException extends RuntimeException {

    /**
     * Constructs a new BucketInitializerException with the specified detail message.
     *
     * @param message the detail message
     */
    public BucketInitializerException(String message) {
        super(message);
    }
}