package com.example.backend.repository;

import com.example.backend.domain.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository
        extends JpaRepository<BankAccount, Long> {

    boolean existsByHolderName(String holderName);
}