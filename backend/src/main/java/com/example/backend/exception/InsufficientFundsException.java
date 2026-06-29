package com.example.backend.exception;

/**
 * Exception thrown when a bank account does not contain
 * sufficient funds to complete a transaction.
 *
 * <p>This exception is typically thrown when a debit or
 * transfer amount exceeds the available account balance.
 */
public class InsufficientFundsException
        extends RuntimeException {

    /**
     * Creates a new insufficient funds exception.
     *
     * @param errorMessage detailed exception message
     */
    public InsufficientFundsException(
            final String errorMessage) {

        super(errorMessage);
    }
}
