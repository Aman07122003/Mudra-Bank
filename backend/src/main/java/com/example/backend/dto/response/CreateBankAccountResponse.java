package com.example.backend.dto.response;

/**
 * Response object returned after successfully creating a bank account.
 *
 * <p>Contains a status message indicating the result of the
 * account creation operation.</p>
 */
public class CreateBankAccountResponse {

    private String message;

    /**
     * Creates an empty response instance.
     *
     * <p>Required by frameworks that perform
     * object serialization and deserialization.</p>
     */
    public CreateBankAccountResponse() {
    }

    /**
     * Creates a response with the specified message.
     *
     * @param message status message describing the result
     *                of the account creation operation
     */
    public CreateBankAccountResponse(final String message) {
        this.message = message;
    }

    /**
     * Returns the status message.
     *
     * @return operation status message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the status message.
     *
     * @param message operation status message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}