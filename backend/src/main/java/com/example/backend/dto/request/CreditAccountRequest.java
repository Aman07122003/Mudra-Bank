package com.example.backend.dto.request;

import java.math.BigDecimal;

public class CreditAccountRequest {
    private long accountNumber;
    private BigDecimal amount;

    public CreditAccountRequest(){
    }

    public CreditAccountRequest(long accountNumber,
                                BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public long getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getBalance() {
        return amount;
    }
}
