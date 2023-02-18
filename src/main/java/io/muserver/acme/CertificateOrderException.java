package io.muserver.acme;

/**
 * A certificate authorisation or order at an ACME server failed.
 */
public class CertificateOrderException extends RuntimeException {

    /**
     * Creates a new exception
     * @param message the error message
     */
    public CertificateOrderException(String message) {
        super(message);
    }

    /**
     * Creates a new exception
     * @param message the error message
     * @param cause the cause
     */
    public CertificateOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
