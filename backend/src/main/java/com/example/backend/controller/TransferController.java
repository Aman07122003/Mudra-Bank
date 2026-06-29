package com.example.backend.controller;

import com.example.backend.converter.CSVHttpMessageConverter;
import com.example.backend.converter.PDFHttpMessageConverter;
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
    private final CSVHttpMessageConverter csvConverter;
    private final PDFHttpMessageConverter pdfConverter;

    /**
     * Creates a new transfer controller.
     *
     * @param transferService service used to process transfer requests
     */
    public TransferController(
            final TransferService transferService,
            final CSVHttpMessageConverter csvHttpMessageConverter,
            final PDFHttpMessageConverter pdfConverter) {

        this.service = transferService;
        this.csvConverter = csvHttpMessageConverter;
        this.pdfConverter = pdfConverter;
    }

    /**
     * Exports the transfer history of a specific bank account as a CSV file.
     *
     * <p>The transfer history is retrieved using the supplied account number
     * and pagination parameters, converted into CSV format, and returned
     * as a downloadable file.
     *
     * @param accountNumber unique account number whose transfer history
     *                      is to be exported
     * @param page zero-based page number
     * @param size number of transfer records per page
     * @return CSV file containing the requested transfer history
     */
    @GetMapping(
            value = "/account/{accountNumber}/export/csv",
            produces = "text/csv")
    public ResponseEntity<String> exportTransfersToCsv(

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

        String csv =
                csvConverter.toCsv(
                        response.getTransactions());

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=transactions.csv")
                .body(csv);
    }

    @GetMapping("/account/{accountNumber}/export/pdf")
    public ResponseEntity<byte[]> exportTransfersPdf(
            @PathVariable final Long accountNumber,
            @RequestParam(defaultValue = "0") final int page,
            @RequestParam(defaultValue = "10") final int size) {

        GetAccountTransfersRequest request =
                new GetAccountTransfersRequest(
                        accountNumber,
                        page,
                        size);

        GetAccountTransfersResponse response =
                service.getAccountTransfers(request);

        byte[] pdf =
                pdfConverter.toPdf(
                        response.getTransactions());

        return ResponseEntity.ok()
                .header(
                        "Content-Disposition",
                        "attachment; filename=transactions.pdf")
                .header(
                        "Content-Type",
                        "application/pdf")
                .body(pdf);
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
