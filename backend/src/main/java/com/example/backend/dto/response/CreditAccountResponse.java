package com.example.backend.dto.response;

import java.math.BigDecimal;

public class CreditAccountResponse {

    private Long accountNumber;
    private BigDecimal updatedBalance;

    public CreditAccountResponse() {
    }

    public CreditAccountResponse(Long accountNumber,
                                 BigDecimal updatedBalance) {
        this.accountNumber = accountNumber;
        this.updatedBalance = updatedBalance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getUpdatedBalance() {
        return updatedBalance;
    }
}