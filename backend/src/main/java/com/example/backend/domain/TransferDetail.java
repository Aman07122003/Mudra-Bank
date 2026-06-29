package com.example.backend.domain;

import com.example.backend.annotation.CsvColumn;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents the details of a bank account transfer.
 *
 * <p>This object is used to return transfer information to clients,
 * including the transaction number, transfer type, related account,
 * transferred amount, and transaction date.
 */
public class TransferDetail {

    @CsvColumn(order = 5, header = "Amount")
    private final BigDecimal amount;

    @CsvColumn(order = 4, header = "From/To")
    private final String fromToAccountNumber;

    @CsvColumn(order = 2, header = "Date")
    private final Date transactionDate;

    @CsvColumn(order = 1, header = "Transaction Number")
    private final Long transactionNumber;

    @CsvColumn(order = 3, header = "Type")
    private final TransferType transferType;

    /**
     * Creates a new transfer detail.
     *
     * @param transactionNum unique transaction number
     * @param type type of transfer (debit or credit)
     * @param fromToAccountNum account holder associated with the
     *                            transfer
     * @param amt transferred amount
     * @param date date and time when the transfer occurred
     */
    public TransferDetail(
            final Long transactionNum,
            final TransferType type,
            final String fromToAccountNum,
            final BigDecimal amt,
            final Date date) {

        this.amount = amt;
        this.fromToAccountNumber = fromToAccountNum;
        this.transactionDate = date;
        this.transactionNumber = transactionNum;
        this.transferType = type;
    }

    /**
     * Returns the transferred amount.
     *
     * @return transferred amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the account holder associated with the transfer.
     *
     * @return source account holder for credit transfers or
     *         destination account holder for debit transfers
     */
    public String getFromToAccountNumber() {
        return fromToAccountNumber;
    }

    /**
     * Returns the date and time of the transfer.
     *
     * @return transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Returns the unique transaction number.
     *
     * @return transaction number
     */
    public Long getTransactionNumber() {
        return transactionNumber;
    }

    /**
     * Returns the transfer type.
     *
     * @return transfer type
     */
    public TransferType getTransferType() {
        return transferType;
    }
}
