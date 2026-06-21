package com.example.backend.domain;

import java.math.BigDecimal;
import java.util.List;

public class BankAccountDetail {

    private Long accountNumber;
    private String holderName;
    private BigDecimal balance;

    public BankAccountDetail() {
    }

    public BankAccountDetail(Long accountNumber,
                             String holderName,
                             BigDecimal balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}