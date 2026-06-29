package com.example.backend.dto.request;

import java.math.BigDecimal;

/**
 * Request object used to create a transfer between two bank accounts.
 *
 * <p>Contains the source account, destination account,
 * and the amount to be transferred.
 */
public class CreateTransferRequest {

    private BigDecimal amount;
    private Long destinationAccountNumber;
    private Long sourceAccountNumber;

    /**
     * Returns the amount to be transferred.
     *
     * @return transfer amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the destination account number.
     *
     * @return account receiving the transferred funds
     */
    public Long getDestinationAccountNumber() {
        return destinationAccountNumber;
    }

    /**
     * Returns the source account number.
     *
     * @return account from which funds will be withdrawn
     */
    public Long getSourceAccountNumber() {
        return sourceAccountNumber;
    }
}
