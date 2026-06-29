package com.example.backend.dto.response;
import com.example.backend.domain.TransferDetail;

import java.util.ArrayList;
import java.util.List;

public class GetAccountTransactionsResponse {

    private List<TransferDetail> transfers = new ArrayList<>();

    public GetAccountTransactionsResponse(
            List<TransferDetail> transactions
    ) {
        this.transfers = transactions;
    }

    public GetAccountTransactionsResponse() {

    }

    public void addTransaction(
            TransferDetail transaction) {

        transfers.add(transaction);
    }

    public List<TransferDetail> getTransactions() {
        return transfers;
    }
}