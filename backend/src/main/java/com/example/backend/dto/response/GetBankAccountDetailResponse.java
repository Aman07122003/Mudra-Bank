package com.example.backend.dto.response;

import com.example.backend.domain.BankAccountDetail;

public class GetBankAccountDetailResponse {

    private BankAccountDetail bankAccount;

    public BankAccountDetail getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(BankAccountDetail bankAccount) {
        this.bankAccount = bankAccount;
    }
}