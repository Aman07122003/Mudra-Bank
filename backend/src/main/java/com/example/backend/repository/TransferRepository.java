package com.example.backend.repository;

import com.example.backend.domain.Transfer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository responsible for performing persistence operations
 * related to {@link Transfer} entities.
 *
 * <p>Provides CRUD operations inherited from
 * {@link JpaRepository} along with custom queries for
 * retrieving transfer history.
 */
public interface TransferRepository
        extends JpaRepository<Transfer, Long> {

    /**
     * Retrieves the transfer history for the specified bank account.
     *
     * <p>The result includes all transfers where the given account
     * is either the source account (debit) or the destination
     * account (credit). The transfers are returned in descending
     * order of transaction date and support pagination.
     *
     * @param accountNumber unique account number whose transfer
     *                      history is to be retrieved
     * @param pageable pagination information including page number
     *                 and page size
     * @return paginated transfer history for the specified account
     */
    @Query("""
            SELECT t
            FROM Transfer t
            WHERE t.sourceAccount.accountNumber = :accountNumber
               OR t.targetAccount.accountNumber = :accountNumber
            ORDER BY t.transactionDate DESC
            """)
    Page<Transfer> findAccountTransactions(
            @Param("accountNumber") Long accountNumber, Pageable pageable);
}
