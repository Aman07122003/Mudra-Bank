package com.example.backend.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferDetail {

    private Long transactionNumber;
    private Long sourceAccountNumber;
    private Long targetAccountNumber;
    private BigDecimal amount;
    private LocalDateTime transactionDate;

    public TransferDetail() {
    }

    public TransferDetail(Long transactionNumber,
                          Long sourceAccountNumber,
                          Long targetAccountNumber,
                          BigDecimal amount,
                          LocalDateTime transactionDate) {
        this.transactionNumber = transactionNumber;
        this.sourceAccountNumber = sourceAccountNumber;
        this.targetAccountNumber = targetAccountNumber;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(Long transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public Long getSourceAccountNumber() {
        return sourceAccountNumber;
    }

    public void setSourceAccountNumber(Long sourceAccountNumber) {
        this.sourceAccountNumber = sourceAccountNumber;
    }

    public Long getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(Long targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }
}