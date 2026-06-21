package com.example.backend.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class GetBankAccountDetailRequest {

    @NotNull
    @Positive
    private Long bankAccountNumber;

    public GetBankAccountDetailRequest() {
    }

    public GetBankAccountDetailRequest(Long bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public Long getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(Long bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }
}