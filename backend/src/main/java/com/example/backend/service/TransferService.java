package com.example.backend.service;

import com.example.backend.domain.BankAccount;
import com.example.backend.domain.Transfer;
import com.example.backend.domain.TransferDetail;
import com.example.backend.domain.TransferType;
import com.example.backend.dto.request.CreateTransferRequest;
import com.example.backend.dto.request.GetAccountTransfersRequest;
import com.example.backend.dto.response.CreateTransferResponse;
import com.example.backend.dto.response.GetAccountTransfersResponse;
import com.example.backend.exception.AccountNotFoundException;

import com.example.backend.exception.InsufficientFundsException;
import com.example.backend.exception.InvalidTransferException;
import com.example.backend.repository.BankAccountRepository;
import com.example.backend.repository.TransferRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;

/**
 * Service layer responsible for handling money transfer operations.
 *
 * <p>This service contains the business logic required to transfer
 * funds between bank accounts. It validates transfer requests,
 * verifies account balances, updates account balances, and records
 * completed transfers.
 *
 * <p>All transfer operations are executed within a transactional
 * boundary to ensure data consistency and integrity.
 */
@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final BankAccountRepository bankAccountRepository;

    /**
     * Creates a new transfer service.
     *
     * @param transferRepo repository used to persist transfer records
     * @param bankAccountRepo repository used to access and update bank accounts
     */
    public TransferService(
            final TransferRepository transferRepo,
            final BankAccountRepository bankAccountRepo) {

        this.transferRepository = transferRepo;
        this.bankAccountRepository = bankAccountRepo;
    }

    /**
     * Executes a money transfer between two bank accounts.
     *
     * <p>This method performs the following steps:
     * validates both accounts, checks sufficient balance,
     * updates source and destination balances, and stores
     * the transfer record.
     *
     * @param request contains source account, destination account, and amount
     * @return response indicating success or failure of the transfer
     */
    @Transactional(
            isolation = Isolation.READ_COMMITTED,
            timeout = 10
    )
    public CreateTransferResponse createTransfer(
            final CreateTransferRequest request) {

        final Long sourceAccountNumber =
                request.getSourceAccountNumber();
        final Long destinationAccountNumber =
                request.getDestinationAccountNumber();

        if (sourceAccountNumber.equals(destinationAccountNumber)) {

            throw new InvalidTransferException(
                    "Source and destination accounts must be different");
        }

        final Long firstLock = Math.min(
                sourceAccountNumber,
                destinationAccountNumber);
        final Long secondLock = Math.max(
                sourceAccountNumber,
                destinationAccountNumber);

        final BankAccount firstAccount =
                bankAccountRepository.findForUpdate(firstLock)
                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Bank account not found"));

        final BankAccount secondAccount =
                bankAccountRepository.findForUpdate(secondLock)
                        .orElseThrow(() ->
                                new AccountNotFoundException(
                                        "Bank account not found"));

        BankAccount sourceAccount;
        BankAccount destinationAccount;

        if (sourceAccountNumber.equals(firstLock)) {

            sourceAccount = firstAccount;
            destinationAccount = secondAccount;

        } else {

            sourceAccount = secondAccount;
            destinationAccount = firstAccount;
        }

        if (sourceAccount.getBalance()
                .compareTo(request.getAmount()) < 0) {

            throw new InsufficientFundsException(
                    "Insufficient funds");
        }

        sourceAccount.setBalance(
                sourceAccount.getBalance()
                        .subtract(request.getAmount()));

        destinationAccount.setBalance(
                destinationAccount.getBalance()
                        .add(request.getAmount()));

        final Transfer transfer =
                new Transfer(
                        sourceAccount,
                        destinationAccount,
                        request.getAmount(),
                        new Date());

        transferRepository.save(transfer);

        return new CreateTransferResponse(
                "Transfer completed successfully");
    }

    /**
     * Retrieves the transfer history for a specific bank account.
     *
     * <p>The transfer history is returned as a paginated list containing
     * debit and credit transactions associated with the specified account.
     *
     * @param request contains the account number and pagination information
     * @return paginated transfer history for the specified account
     */
    @Transactional(readOnly = true)
    public GetAccountTransfersResponse getAccountTransfers(
            final GetAccountTransfersRequest request) {

        final Pageable pageable =
                PageRequest.of(
                        request.getPage(),
                        request.getSize());

        final Page<Transfer> page =
                transferRepository.findAccountTransactions(
                        request.getAccountNumber(),
                        pageable);


        final GetAccountTransfersResponse response =
                new GetAccountTransfersResponse();

        for (final Transfer transfer : page.getContent()) {

            TransferType transferType;
            String fromToAccountNumber;

            if (transfer.getSourceAccount()
                    .getAccountNumber()
                    .equals(request.getAccountNumber())) {

                transferType = TransferType.DEBIT;

                fromToAccountNumber =
                        transfer.getTargetAccount()
                                .getHolderName();

            } else {

                transferType = TransferType.CREDIT;

                fromToAccountNumber =
                        transfer.getSourceAccount()
                                .getHolderName();
            }

            response.addTransaction(
                    new TransferDetail(
                            transfer.getTransactionNumber(),
                            transferType,
                            fromToAccountNumber,
                            transfer.getAmount(),
                            transfer.getTransactionDate()
                    ));
        }

        response.setPage(page.getNumber());
        response.setSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLast(page.isLast());

        return response;
    }
}
