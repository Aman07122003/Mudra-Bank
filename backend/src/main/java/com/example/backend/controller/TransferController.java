package com.example.backend.controller;

import com.example.backend.dto.request.CreateTransferRequest;
import com.example.backend.dto.request.GetAccountTransfersRequest;
import com.example.backend.dto.response.CreateTransferResponse;
import com.example.backend.dto.response.GetAccountTransfersResponse;

import com.example.backend.service.TransferService;

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
 * REST controller responsible for handling transfer operations.
 *
 * <p>This controller exposes endpoints for:
 * <ul>
 *     <li>Creating transfers between bank accounts</li>
 *     <li>Retrieving transfer history</li>
 * </ul>
 *
 * <p>All transfer-related business logic is delegated to
 * {@link TransferService}.
 */
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("transfers")
@RestController
public class TransferController {
    private final TransferService service;

    /**
     * Creates a new transfer controller.
     *
     * @param transferService service used to process transfer requests
     */
    public TransferController(
            final TransferService transferService) {

        this.service = transferService;
    }

    /**
     * Exports the complete transfer history for a specific
     * bank account as CSV data.
     *
     * <p>This endpoint retrieves all transfer records associated
     * with the specified account by requesting the maximum page
     * size and returns the result as a downloadable CSV file.
     *
     * @param accountNumber unique account number whose transfer
     *                      history is to be exported
     * @return HTTP response containing the transfer collection
     *         formatted as CSV
     */
    @GetMapping(
            value = "/account/{accountNumber}/export/csv",
            produces = "text/csv")
    public ResponseEntity<Collection<?>> exportTransfersToCsv(
            @PathVariable final Long accountNumber) {

        GetAccountTransfersRequest request =
                new GetAccountTransfersRequest(
                        accountNumber,
                        0,
                        Integer.MAX_VALUE);

        GetAccountTransfersResponse response =
                service.getAccountTransfers(request);

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=transactions.csv")
                .body(response.getTransactions());
    }

    /**
     * Exports the complete transfer history for a specific
     * bank account as PDF data.
     *
     * <p>This endpoint retrieves all transfer records associated
     * with the specified account by requesting the maximum page
     * size and returns the result as a downloadable PDF file.
     *
     * @param accountNumber unique account number whose transfer
     *                      history is to be exported
     * @return HTTP response containing the transfer collection
     *         formatted as PDF
     */
    @GetMapping(
            value = "/account/{accountNumber}/export/pdf",
            produces = "application/pdf")
    public ResponseEntity<Collection<?>> exportTransfersToPdf(
            @PathVariable final Long accountNumber) {

        GetAccountTransfersRequest request =
                new GetAccountTransfersRequest(
                        accountNumber,
                        0,
                        Integer.MAX_VALUE);

        GetAccountTransfersResponse response =
                service.getAccountTransfers(request);

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=transactions.pdf")
                .body(response.getTransactions());
    }

    /**
     * Retrieves the transfer history for a specific bank account.
     *
     * <p>The results are returned in a paginated format based on the
     * specified page number and page size.
     *
     * @param accountNumber unique account number whose transfer history
     *                      is to be retrieved
     * @param page zero-based page number
     * @param size number of transfer records per page
     * @return paginated transfer history for the specified account
     */
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<GetAccountTransfersResponse> getAccountTransfers(

            @PathVariable final Long accountNumber,

            @RequestParam(defaultValue = "0")
            final int page,

            @RequestParam(defaultValue = "10")
            final int size) {

        GetAccountTransfersRequest request =
                new GetAccountTransfersRequest(
                        accountNumber,
                        page,
                        size);

        GetAccountTransfersResponse response =
                service.getAccountTransfers(request);

        return ResponseEntity.ok(response);
    }

    /**
     * Creates a new transfer between two existing bank accounts.
     *
     * <p>The specified amount is withdrawn from the source account
     * and deposited into the destination account. A transfer record
     * is then created and persisted.
     *
     * @param request transfer details including source account,
     *                destination account, and transfer amount
     * @return response indicating whether the transfer
     *         was successfully completed
     */
    @PutMapping
    public ResponseEntity<CreateTransferResponse> registerTransfer(
            @RequestBody final CreateTransferRequest request) {

        CreateTransferResponse response = service.createTransfer(request);

        return ResponseEntity.ok(response);
    }
}
