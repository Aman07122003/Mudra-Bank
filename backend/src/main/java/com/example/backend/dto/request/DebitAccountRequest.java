package com.example.backend.dto.request;

import java.math.BigDecimal;

public class DebitAccountRequest {

    private Long accountNumber;
    private BigDecimal amount;

    public DebitAccountRequest() {
    }

    public DebitAccountRequest(Long accountNumber,
                               BigDecimal amount) {
        this.accountNumber = accountNumber;
        this.amount = amount;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
