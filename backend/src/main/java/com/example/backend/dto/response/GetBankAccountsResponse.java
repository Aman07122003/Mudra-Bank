package com.example.backend.dto.response;

import com.example.backend.domain.BankAccountDetail;

import java.util.ArrayList;
import java.util.List;

public class GetBankAccountsResponse {

    private List<BankAccountDetail> bankAccounts = new ArrayList<>();

    public List<BankAccountDetail> getBankAccounts() {
        return bankAccounts;
    }

    public void setBankAccounts(List<BankAccountDetail> bankAccounts) {
        this.bankAccounts = bankAccounts;
    }

    public void addBankAccount(BankAccountDetail bankAccount) {
        this.bankAccounts.add(bankAccount);
    }
}