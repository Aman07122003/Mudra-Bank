package com.example.backend.service;

import com.example.backend.domain.BankAccount;
import com.example.backend.domain.BankAccountDetail;
import com.example.backend.dto.request.*;
import com.example.backend.dto.request.GetBankAccountDetailRequest;
import com.example.backend.dto.response.*;
import com.example.backend.repository.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BankAccountService {

    private final BankAccountRepository repository;

    private BankAccountDetail toBankAccountDetail(final BankAccount entity) {
        return new BankAccountDetail(
                entity.getAccountNumber(),
                entity.getHolderName(),
                entity.getBalance()
        );
    }

    public BankAccountService(final BankAccountRepository repository) {
        this.repository = repository;
    }

    /**
     *
     * @param request
     * @return
     */
    public GetBankAccountDetailResponse getBankAccountByAccountNumber(
            final GetBankAccountDetailRequest request) {

        final GetBankAccountDetailResponse response = new GetBankAccountDetailResponse();

        Optional<BankAccount> account = repository
                .findById(request.getBankAccountNumber());

        if (account.isEmpty()) {
            return response;
        }

        BankAccount entity = account.get();
        response.setBankAccount(toBankAccountDetail(entity));

        return response;
    }

    /**
     *
     * @param request
     * @return
     */
    public CreateBankAccountResponse createBankAccount(
            final CreateBankAccountRequest request) {

        BankAccount account = new BankAccount();

        account.setHolderName(request.getAccountHolderName());
        account.setBalance(request.getAccountBalance());

        repository.save(account);
        CreateBankAccountResponse response = new CreateBankAccountResponse();

        response.setMessage("Account created successfully");
        response.setAccountHolderName(account.getHolderName());
        return response;
    }

    /**
     *
     * @param request
     * @return
     */
    public GetBankAccountsResponse getBankAccounts(
            final GetBankAccountsRequest request) {
        final GetBankAccountsResponse response = new GetBankAccountsResponse();

        Iterable<BankAccount> bankAccounts = repository.findAll();

        for(BankAccount bankAccount : bankAccounts) {
            response.addBankAccount(toBankAccountDetail(bankAccount));
        }

        return response;
    }

    public DebitAccountResponse debit(final DebitAccountRequest request) {

        BankAccount account = repository.findById(request.getAccountNumber()).orElseThrow();

        BigDecimal updatedBalance = account.getBalance().subtract(request.getAmount());

        account.setBalance(updatedBalance);

        repository.save(account);

        return new DebitAccountResponse(
                account.getAccountNumber(),
                updatedBalance
        );
    }

    public CreditAccountResponse credit(final @NonNull CreditAccountRequest request) {

        BankAccount account = repository.findById(
                request.getAccountNumber()
        ).orElseThrow();

        BigDecimal updatedBalance =
                account.getBalance()
                        .add(request.getBalance());

        account.setBalance(updatedBalance);

        repository.save(account);

        return new CreditAccountResponse(
                account.getAccountNumber(),
                updatedBalance
        );
    }
}