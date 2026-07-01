package com.example.backend.domain;

import com.example.backend.annotation.ExportColumn;

import java.math.BigDecimal;

/**
 * Represents a bank account summary returned by the service layer.
 *
 * <p>This class is used to transfer bank account information
 * between application layers without exposing the underlying
 * persistence entity.
 *
 * <p>It contains the account number, account holder name,
 * and current account balance.
 */
public class BankAccountDetail {

    @ExportColumn(order = 1, header = "Account Number")
    private final Long accountNumber;

    @ExportColumn(order = 3, header = "Balance")
    private final BigDecimal balance;

    @ExportColumn(order = 2, header = "Account Holder")
    private final String holderName;

    /**
     * Creates a bank account detail instance with all properties.
     *
     * @param number unique account number
     * @param bal current account balance
     * @param name name of the account holder
     */
    public BankAccountDetail(
            final Long number,
            final String name,
            final BigDecimal bal) {

        this.accountNumber = number;
        this.balance = bal;
        this.holderName = name;
    }

    /**
     * Returns the account number.
     *
     * @return unique account number
     */
    public Long getAccountNumber() {
        return accountNumber;
    }

    /**
     * Returns the current account balance.
     *
     * @return current balance
     */
    public BigDecimal getBalance() {
        return balance;
    }

    /**
     * Returns the account holder's name.
     *
     * @return holder name
     */
    public String getHolderName() {
        return holderName;
    }
}
