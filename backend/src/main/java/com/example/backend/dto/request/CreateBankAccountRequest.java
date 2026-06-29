package com.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
/**
 * Request object used to create a new bank account.
 *
 * <p>Contains the account holder's name and the initial
 * account balance to be assigned when the account is
 * created.
 */
public class CreateBankAccountRequest {

    @NotNull
    @PositiveOrZero
    private BigDecimal accountBalance;

    @NotBlank
    private String accountHolderName;

    /**
     * Creates an empty request instance.
     *
     * <p>Required by frameworks that perform
     * object deserialization from JSON.
     */
    public CreateBankAccountRequest() {
    }

    /**
     * Returns the initial balance for the new account.
     *
     * @return account opening balance
     */
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    /**
     * Returns the name of the account holder.
     *
     * @return account holder name
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }
}
