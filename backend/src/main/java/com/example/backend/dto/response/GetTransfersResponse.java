package com.example.backend.dto.response;

import com.example.backend.domain.TransferDetail;

import java.util.List;

public class GetTransfersResponse {

    private List<TransferDetail> transfers;

    public GetTransfersResponse() {
    }

    public GetTransfersResponse(List<TransferDetail> transfers) {
        this.transfers = transfers;
    }

    public List<TransferDetail> getTransfers() {
        return transfers;
    }

    public void setTransfers(List<TransferDetail> transfers) {
        this.transfers = transfers;
    }
}