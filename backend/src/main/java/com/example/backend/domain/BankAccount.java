package com.example.backend.domain;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.NaturalId;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountNumber;

    @Column(name = "holder_name", nullable = false, length = 100)
    @NotBlank(message = "Holder name cannot be blank")
    @Size(min = 2, max = 100,
            message = "Holder name must contain between {min} and {max} characters")
    @NaturalId
    private String holderName;

    @Column(name = "balance", nullable = false)
    @PositiveOrZero(message = "Balance cannot be negative")
    private BigDecimal balance;

    public BankAccount() {
    }

    public BankAccount(String holderName,
                       BigDecimal balance) {
        this.holderName = holderName;
        this.balance = balance;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}