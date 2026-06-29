package com.example.backend.exception;

/**
 * Exception thrown when a requested bank account cannot be found.
 *
 * <p>This exception is typically thrown when an operation references
 * a bank account that does not exist in the system.
 */
public class AccountNotFoundException
        extends RuntimeException {

    /**
     * Creates a new account not found exception.
     *
     * @param message detailed exception message
     */
    public AccountNotFoundException(
            final String message) {

        super(message);
    }
}
