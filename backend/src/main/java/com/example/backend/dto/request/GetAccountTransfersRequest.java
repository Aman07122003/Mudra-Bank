package com.example.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request for retrieving transactions
 * belonging to a specific account.
 */
public class GetAccountTransactionsRequest {

    @NotNull
    @Positive
    private Long accountNumber;

    public GetAccountTransactionsRequest() {
    }

    public GetAccountTransactionsRequest(
            Long accountNumber) {

        this.accountNumber = accountNumber;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }
}