package com.example.backend.controller;


import com.example.backend.domain.BankAccountDetail;

import com.example.backend.dto.request.*;

import com.example.backend.dto.response.*;

import com.example.backend.service.BankAccountService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("accounts")
@RestController
public class BankAccountController {

    private final BankAccountService service;

    public BankAccountController(final BankAccountService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<GetBankAccountsResponse> getBankAccounts() {

        GetBankAccountsRequest request =
                new GetBankAccountsRequest();

        GetBankAccountsResponse response =
                service.getBankAccounts(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{accountNumber}")
    public ResponseEntity<GetBankAccountDetailResponse> getAccount(
            @PathVariable final Long accountNumber) {

        GetBankAccountDetailRequest request = new GetBankAccountDetailRequest(accountNumber);
        GetBankAccountDetailResponse response = service.getBankAccountByAccountNumber(request);

        if(response.getBankAccount() == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<CreateBankAccountResponse> createBankAccount(@RequestBody final CreateBankAccountRequest request) {
        CreateBankAccountResponse response = service.createBankAccount(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/debit")
    public ResponseEntity<DebitAccountResponse> debit(
            @RequestBody final DebitAccountRequest request) {

        DebitAccountResponse response =
                service.debit(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/credit")
    public ResponseEntity<CreditAccountResponse> credit(
            @RequestBody final CreditAccountRequest request) {

        CreditAccountResponse response =
                service.credit(request);

        return ResponseEntity.ok(response);
    }

}
