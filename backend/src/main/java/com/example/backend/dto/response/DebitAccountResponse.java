package com.example.backend.dto.response;

import java.math.BigDecimal;

public class DebitAccountResponse {

    private Long accountNumber;
    private BigDecimal updatedBalance;

    public DebitAccountResponse() {
    }

    public DebitAccountResponse(Long accountNumber,
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