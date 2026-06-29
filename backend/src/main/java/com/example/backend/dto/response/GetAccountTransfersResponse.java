package com.example.backend.dto.response;

import com.example.backend.domain.TransferDetail;

import java.util.ArrayList;
import java.util.List;

/**
 * Response object containing a paginated list of transfer records.
 *
 * <p>Includes the transfer history for a bank account along with
 * pagination information such as the current page, page size,
 * total number of records, total number of pages, and whether
 * the current page is the last page.</p>
 */
public class GetAccountTransfersResponse {

    private boolean last;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
    private final List<TransferDetail> transactions =
            new ArrayList<>();

    /**
     * Adds a transfer record to the response.
     *
     * @param transaction transfer record to add
     */
    public void addTransaction(final TransferDetail transaction) {
        transactions.add(transaction);
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
     * Returns the total number of transfer records.
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
     * Returns the transfer records for the requested page.
     *
     * @return list of transfer records
     */
    public List<TransferDetail> getTransactions() {
        return transactions;
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
     * Sets the total number of transfer records.
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
