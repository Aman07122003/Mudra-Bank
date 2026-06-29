package com.example.backend.repository;

import com.example.backend.domain.BankAccount;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

/**
 * Repository responsible for performing persistence operations
 * related to {@link BankAccount} entities.
 *
 * <p>Provides CRUD operations inherited from
 * {@link JpaRepository} along with custom queries used for
 * account validation and transactional updates.
 */
public interface BankAccountRepository
        extends JpaRepository<BankAccount, Long> {

    /**
     * Determines whether a bank account exists for the specified
     * account holder.
     *
     * @param holderName name of the account holder
     * @return {@code true} if an account with the specified holder
     *         name exists; {@code false} otherwise
     */
    boolean existsByHolderName(String holderName);

    /**
     * Retrieves a bank account by its account number while acquiring
     * a pessimistic write lock.
     *
     * <p>The lock prevents concurrent transactions from modifying
     * the same account until the current transaction completes.
     * A lock timeout of five seconds is applied to avoid waiting
     * indefinitely for the lock.
     *
     * @param accountNumber unique account number
     * @return an {@link Optional} containing the bank account if found;
     *         otherwise an empty {@link Optional}
     */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({
            @QueryHint(
                    name = "jakarta.persistence.lock.timeout",
                    value = "5000"
            )
    })
    @Query("""
            SELECT b
            FROM BankAccount b
            WHERE b.accountNumber = :accountNumber
            """)
    Optional<BankAccount> findForUpdate(
            @Param("accountNumber") Long accountNumber);
}
