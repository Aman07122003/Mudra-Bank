package com.example.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Request object used to retrieve details of a specific bank account.
 *
 * <p>Contains the unique account number used to identify
 * the bank account to be retrieved.</p>
 */
public class GetBankAccountDetailRequest {

    @NotNull
    @Positive
    private Long bankAccountNumber;

    /**
     * Creates an empty request instance.
     *
     * <p>Required by frameworks that perform
     * object deserialization from JSON.</p>
     */
    public GetBankAccountDetailRequest() {
    }

    /**
     * Creates a request to fetch a bank account by its account number.
     *
     * @param number unique identifier of the bank account
     */
    public GetBankAccountDetailRequest(final Long number) {
        this.bankAccountNumber = number;
    }

    /**
     * Returns the bank account number used for lookup.
     *
     * @return bank account number
     */
    public Long getBankAccountNumber() {
        return bankAccountNumber;
    }
}
