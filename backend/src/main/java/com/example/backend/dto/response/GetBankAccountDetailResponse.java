package com.example.backend.dto.response;

import com.example.backend.domain.BankAccountDetail;

/**
 * Response object returned when retrieving a specific bank account.
 *
 * <p>Contains the bank account details wrapped in a {@link BankAccountDetail}
 *  object. If the account does not exist, the value may be null.
 */
public class GetBankAccountDetailResponse {

    private BankAccountDetail bankAccount;

    /**
     * Returns the bank account details.
     *
     * @return bank account information if found, otherwise null
     */
    public BankAccountDetail getBankAccount() {
        return bankAccount;
    }

    /**
     * Sets the bank account details.
     *
     * @param bankAccount bank account information
     */
    public void setBankAccount(final BankAccountDetail bankAccount) {
        this.bankAccount = bankAccount;
    }
}
