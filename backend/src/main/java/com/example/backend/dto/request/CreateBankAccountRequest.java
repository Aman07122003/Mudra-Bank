package com.example.backend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

/**
 *
 */
public class CreateBankAccountRequest {

    @NotBlank
    private String accountHolderName;

    @NotNull
    @Positive
    private BigDecimal accountBalance;

    /**
     *
     */
    public CreateBankAccountRequest(){
    }

    /**
     *
     * @return
     */
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    /**
     *
     * @return
     */
    public String getAccountHolderName() {
        return accountHolderName;
    }
}
