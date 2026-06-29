package com.example.backend.dto.response;

import com.example.backend.domain.BankAccountDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object containing a paginated list of bank accounts.
 *
 * <p>Includes the bank account details along with pagination
 * information such as the current page, page size, total number
 * of records, total number of pages, and whether the current
 * page is the last page.</p>
 */
public class GetBankAccountsResponse {

    private final List<BankAccountDetail> bankAccounts = new ArrayList<>();
    private boolean last;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;

    /**
     * Returns the bank accounts for the requested page.
     *
     * @return list of bank account details
     */
    public List<BankAccountDetail> getBankAccounts() {
        return bankAccounts;
    }

    /**
     * Indicates whether this is the last page.
     *
     * @return {@code true} if this is the last page;
     *         {@code false} otherwise
     */
    public boolean isLast() {
        return last;
    }

    /**
     * Returns the current page number.
     *
     * @return zero-based page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Returns the page size.
     *
     * @return number of records per page
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the total number of bank accounts.
     *
     * @return total number of records
     */
    public long getTotalElements() {
        return totalElements;
    }

    /**
     * Returns the total number of pages.
     *
     * @return total number of pages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     * Adds a bank account to the response.
     *
     * @param bankAccount bank account details to add
     */
    public void setBankAccount(final BankAccountDetail bankAccount) {
        bankAccounts.add(bankAccount);
    }

    /**
     * Sets whether this is the last page.
     *
     * @param last {@code true} if this is the last page;
     *             {@code false} otherwise
     */
    public void setLast(final boolean last) {
        this.last = last;
    }

    /**
     * Sets the current page number.
     *
     * @param page zero-based page number
     */
    public void setPage(final int page) {
        this.page = page;
    }

    /**
     * Sets the page size.
     *
     * @param size number of records per page
     */
    public void setSize(final int size) {
        this.size = size;
    }

    /**
     * Sets the total number of bank accounts.
     *
     * @param totalElements total number of records
     */
    public void setTotalElements(final long totalElements) {
        this.totalElements = totalElements;
    }

    /**
     * Sets the total number of pages.
     *
     * @param totalPages total number of pages
     */
    public void setTotalPages(final int totalPages) {
        this.totalPages = totalPages;
    }
}