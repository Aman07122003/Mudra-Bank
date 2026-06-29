package com.example.backend.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Represents a transfer of funds between two bank accounts.
 *
 * <p>A transfer records the movement of money from a source
 * account to a target account. Each transfer contains the
 * transferred amount and the date and time at which the
 * transfer occurred.
 *
 * <p>Transfer records provide an audit trail of money movement
 * within the banking system.
 */
@Entity
@Table(name = "transfer")
public class Transfer {

    /**
     * Unique identifier for the transfer.
     *
     * <p>The value is generated automatically by the database
     * and uniquely identifies a transfer transaction.
     */

    @Column(nullable = false)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "source_account_number")
    private BankAccount sourceAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_number")
    private BankAccount targetAccount;

    @Column(nullable = false)
    @NotNull
    private Date transactionDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionNumber;

    /**
     * Default constructor required by JPA.
     */
    public Transfer() {
    }

    /**
     * Creates a new transfer.
     *
     * @param sourceAcc account from which funds are withdrawn
     * @param targetAcc account receiving the funds
     * @param amt amount being transferred
     * @param transDate date and time of the transfer
     */
    public Transfer(
            final BankAccount sourceAcc,
            final BankAccount targetAcc,
            final BigDecimal amt,
            final Date transDate) {

        this.amount = amt;
        this.sourceAccount = sourceAcc;
        this.targetAccount = targetAcc;
        this.transactionDate = transDate;
    }

    /**
     * Returns the transferred amount.
     *
     * @return transfer amount
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * Returns the source account.
     *
     * @return account from which funds were withdrawn
     */
    public BankAccount getSourceAccount() {
        return sourceAccount;
    }

    /**
     * Returns the target account.
     *
     * @return account that received the funds
     */
    public BankAccount getTargetAccount() {
        return targetAccount;
    }

    /**
     * Returns the transfer date and time.
     *
     * @return transaction timestamp
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Returns the unique transaction number.
     *
     * @return transfer identifier
     */
    public Long getTransactionNumber() {
        return transactionNumber;
    }
}
