package com.example.backend.dto.response;

public class CreateTransferResponse {

    private String message;

    public CreateTransferResponse() {
    }

    public CreateTransferResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}