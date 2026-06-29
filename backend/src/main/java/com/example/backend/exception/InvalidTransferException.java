package com.example.backend.exception;

/**
 * Exception thrown when a transfer request is invalid.
 *
 * <p>This exception is typically thrown when the transfer
 * violates business rules, such as attempting to transfer
 * funds between the same bank account.
 */
public class InvalidTransferException
        extends RuntimeException {

    /**
     * Creates a new invalid transfer exception.
     *
     * @param errorMessage detailed exception message
     */
    public InvalidTransferException(
            final String errorMessage) {

        super(errorMessage);
    }
}
