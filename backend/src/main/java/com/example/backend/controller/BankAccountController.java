package com.example.backend.controller;
import com.example.backend.dto.request.CreateBankAccountRequest;
import com.example.backend.dto.request.GetBankAccountDetailRequest;
import com.example.backend.dto.request.GetBankAccountsRequest;

import com.example.backend.dto.response.CreateBankAccountResponse;
import com.example.backend.dto.response.GetBankAccountDetailResponse;
import com.example.backend.dto.response.GetBankAccountsResponse;

import com.example.backend.service.BankAccountService;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * REST controller responsible for managing bank account operations.
 *
 * <p>This controller exposes endpoints for:
 * <ul>
 *     <li>Creating bank accounts</li>
 *     <li>Retrieving a specific account</li>
 *     <li>Retrieving all accounts</li>
 *     <li>Crediting an account</li>
 *     <li>Debiting an account</li>
 * </ul>
 *
 * <p>All requests are delegated to the {@link BankAccountService}.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("accounts")
@RestController
public class BankAccountController {
    private final BankAccountService service;

    /**
     * Creates a new controller instance.
     *
     * @param accountService service used to perform
     *                           account-related operations
     */
    public BankAccountController(
            final BankAccountService accountService) {

        this.service = accountService;
    }

    /**
     * Creates a new bank account.
     *
     * <p>The request contains the account holder's name and
     * the initial account balance. The account is persisted
     * and a confirmation response is returned.
     *
     * @param request account creation details
     * @return response containing account creation status
     */
    @PutMapping
    public ResponseEntity<CreateBankAccountResponse> createBankAccount(
            @RequestBody final CreateBankAccountRequest request) {

        CreateBankAccountResponse response =
                service.createBankAccount(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Exports all bank accounts as CSV data.
     *
     * <p>This endpoint retrieves all available bank accounts
     * by requesting the maximum page size and returns the
     * result as a downloadable CSV file.
     *
     * @return HTTP response containing the bank account
     *         collection formatted as CSV
     */
    @GetMapping(
            value = "/export/csv",
            produces = "text/csv")
    public ResponseEntity<Collection<?>> exportAccountsToCsv() {

        GetBankAccountsRequest request =
                new GetBankAccountsRequest();

        request.setPage(0);
        request.setSize(Integer.MAX_VALUE);

        GetBankAccountsResponse response =
                service.getBankAccounts(request);

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=accounts.csv")
                .body(response.getBankAccounts());
    }

    /**
     * Exports all bank accounts as PDF data.
     *
     * <p>This endpoint retrieves all available bank accounts
     * by requesting the maximum page size and returns the
     * result as a downloadable PDF file.
     *
     * @return HTTP response containing the bank account
     *         collection formatted as PDF
     */
    @GetMapping(
            value = "/export/pdf",
            produces = "application/pdf")
    public ResponseEntity<Collection<?>> exportAccountsToPdf() {

        GetBankAccountsRequest request =
                new GetBankAccountsRequest();

        request.setPage(0);
        request.setSize(Integer.MAX_VALUE);

        GetBankAccountsResponse response =
                service.getBankAccounts(request);

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=accounts.pdf")
                .body(response.getBankAccounts());
    }

    /**
     * Retrieves details for a specific bank account.
     *
     * <p>If the account exists, its details are returned.
     * Otherwise, an HTTP 404 (Not Found) response is returned.
     *
     * @param accountNumber unique account number
     * @return account details if found, otherwise 404
     */
    @GetMapping("/{accountNumber}")
    public ResponseEntity<GetBankAccountDetailResponse> getAccount(
            @PathVariable final Long accountNumber) {

        GetBankAccountDetailRequest request =
                new GetBankAccountDetailRequest(accountNumber);

        GetBankAccountDetailResponse response =
                service.getBankAccountByAccountNumber(request);

        if (response.getBankAccount() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves all bank accounts in the system.
     *
     * <p>This endpoint returns a collection of account summaries,
     * including account number, holder name, and balance.
     *
     * @param page zero-based page number
     * @param size number of records per page
     * @return list of all bank accounts
     */
    @GetMapping
    public ResponseEntity<GetBankAccountsResponse> getBankAccounts(
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size) {

        GetBankAccountsRequest request = new GetBankAccountsRequest();
        request.setPage(page);
        request.setSize(size);

        GetBankAccountsResponse response = service.getBankAccounts(request);

        return ResponseEntity.ok(response);
    }
}
