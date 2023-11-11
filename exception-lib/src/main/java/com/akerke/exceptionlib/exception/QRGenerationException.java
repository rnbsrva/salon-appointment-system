package com.akerke.exceptionlib.exception;

/**
 * Exception thrown if a QR code cannot be generated for a given link.
 */
public class QRGenerationException extends RuntimeException {

    /**
     * Constructs a new QRGenerationException with the specified link.
     *
     * @param link the link for which the QR code cannot be generated
     */
    public QRGenerationException(String link) {
        super("QR with link: %s cannot be generated".formatted(link));
    }
}