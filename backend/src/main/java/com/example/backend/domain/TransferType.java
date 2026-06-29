package com.example.backend.domain;

/**
 * <h2>Defines the type of account transaction.</h2>
 *
 * CREDIT indicates funds were added to an account.
 * DEBIT indicates funds were withdrawn from an account.
 */
public enum TransferType {
    CREDIT,
    DEBIT
}
