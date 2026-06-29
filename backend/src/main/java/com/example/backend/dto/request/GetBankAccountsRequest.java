package com.example.backend.dto.request;

/**
 * Request object used to retrieve a paginated list of bank accounts.
 *
 * <p>Contains pagination information including the page number
 * and page size used to retrieve a subset of bank accounts.
 */
public class GetBankAccountsRequest {

    private int page = 0;
    private int size = 10;

    /**
     * Returns the requested page number.
     *
     * @return zero-based page number
     */
    public int getPage() {
        return page;
    }

    /**
     * Sets the requested page number.
     *
     * @param page zero-based page number
     */
    public void setPage(final int page) {
        this.page = page;
    }

    /**
     * Returns the requested page size.
     *
     * @return number of records per page
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the requested page size.
     *
     * @param size number of records per page
     */
    public void setSize(final int size) {
        this.size = size;
    }
}