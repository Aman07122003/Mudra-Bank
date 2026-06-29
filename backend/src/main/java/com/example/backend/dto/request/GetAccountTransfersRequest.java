package com.example.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

/**
 * Request object used to retrieve the transfer history
 * for a specific bank account.
 *
 * <p>Contains the account number and pagination information
 * required to retrieve a page of transfer records.</p>
 */
public class GetAccountTransfersRequest {

    @NotNull
    @Positive
    private Long accountNumber;

    @PositiveOrZero
    private int page = 0;

    @Positive
    private int size = 10;

    /**
     * Creates an empty request instance.
     *
     * <p>Required by frameworks that perform
     * object deserialization from JSON.</p>
     */
    public GetAccountTransfersRequest() {
    }

    /**
     * Creates a request to retrieve transfer history.
     *
     * @param accountNumber unique identifier of the bank account
     * @param page zero-based page number
     * @param size number of records per page
     */
    public GetAccountTransfersRequest(
            final Long accountNumber,
            final int page,
            final int size) {

        this.accountNumber = accountNumber;
        this.page = page;
        this.size = size;
    }

    /**
     * Returns the account number whose transfer history
     * is to be retrieved.
     *
     * @return bank account number
     */
    public Long getAccountNumber() {
        return accountNumber;
    }

    /**
     * Returns the requested page number.
     *
     * @return zero-based page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Returns the requested page size.
     *
     * @return number of records per page
     */
    public int getSize() {
        return size;
    }
}