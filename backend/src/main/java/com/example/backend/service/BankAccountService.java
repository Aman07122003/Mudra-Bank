package com.example.backend.service;

import com.example.backend.domain.BankAccount;
import com.example.backend.domain.BankAccountDetail;
import com.example.backend.dto.request.GetBankAccountDetailRequest;
import com.example.backend.exception.AccountNotFoundException;
import com.example.backend.exception.DuplicateAccountException;
import com.example.backend.repository.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.example.backend.dto.request.CreateBankAccountRequest;
import com.example.backend.dto.request.GetBankAccountsRequest;

import com.example.backend.dto.response.CreateBankAccountResponse;
import com.example.backend.dto.response.GetBankAccountDetailResponse;
import com.example.backend.dto.response.GetBankAccountsResponse;


/**
 * Service layer responsible for managing bank account operations.
 *
 * This class contains business logic for creating accounts,
 * crediting and debiting balances, and retrieving account details.
 *
 * It acts as an intermediary between the controller layer and
 * the repository layer, ensuring that all account operations
 * are executed in a consistent and controlled manner.
 */
@Service
public class BankAccountService {

    private final BankAccountRepository repository;


    /**
     * Creates a new bank account service.
     *
     * @param bankAccountRepository repository used to manage bank account
     *                              persistence
     */
    public BankAccountService(
            final BankAccountRepository bankAccountRepository) {
        this.repository = bankAccountRepository;
    }

    /**
     * Converts a {@link BankAccount} entity into a {@link BankAccountDetail}
     * data transfer object.
     *
     * @param entity bank account entity from the database
     * @return mapped bank account detail object
     */
    private BankAccountDetail toBankAccountDetail(final BankAccount entity) {
        return new BankAccountDetail(
                entity.getAccountNumber(),
                entity.getHolderName(),
                entity.getBalance()
        );
    }

    /**
     * Creates a new bank account in the system.
     *
     * @param request contains account holder name and initial balance
     * @return response confirming account creation
     */
    @Transactional
    public CreateBankAccountResponse createBankAccount(
            final CreateBankAccountRequest request) {

        if (repository.existsByHolderName(request.getAccountHolderName())) {
            throw new DuplicateAccountException(
                    "Account holder already exists");
        }

        BankAccount account = new BankAccount(
                request.getAccountHolderName(),
                request.getAccountBalance());

        CreateBankAccountResponse response = new CreateBankAccountResponse();

        repository.save(account);

        response.setMessage("Account created successfully");
        return response;
    }

    /**
     * Retrieves a bank account by its account number.
     *
     * @param request contains the account number to search for
     * @return response containing bank account details if found
     */
    public GetBankAccountDetailResponse getBankAccountByAccountNumber(
            final GetBankAccountDetailRequest request) {

        final GetBankAccountDetailResponse response =
                new GetBankAccountDetailResponse();

        BankAccount account = repository
                .findById(request.getBankAccountNumber())
                .orElseThrow(() ->
                        new AccountNotFoundException(
                                "Bank account not found"));

        response.setBankAccount(toBankAccountDetail(account));

        return response;
    }

    /**
     * Retrieves a paginated list of bank accounts.
     *
     * @param request contains pagination information including
     *                page number and page size
     * @return response containing the requested page of bank accounts
     */
    public GetBankAccountsResponse getBankAccounts(
            final GetBankAccountsRequest request) {

        final Pageable pageable =
                    PageRequest.of(
                            request.getPage(),
                            request.getSize());

        final Page<BankAccount> page =
                   repository.findAll(pageable);

        final GetBankAccountsResponse response =
                   new GetBankAccountsResponse();

        for (final BankAccount account : page.getContent()) {

            response.setBankAccount(
                    toBankAccountDetail(account));
        }

        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());

        return response;
    }
}
