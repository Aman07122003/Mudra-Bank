package com.example.backend.exception;

/**
 * Exception thrown when attempting to create a bank account
 * for an account holder that already exists.
 *
 * <p>This exception indicates that the requested operation
 * would violate the uniqueness constraint for account holders.
 */
public class DuplicateAccountException
        extends RuntimeException {

    /**
     * Creates a new duplicate account exception.
     *
     * @param errorMessage detailed exception message
     */
    public DuplicateAccountException(
            final String errorMessage) {

        super(errorMessage);
    }
}
