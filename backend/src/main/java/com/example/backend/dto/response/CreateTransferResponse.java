package com.example.backend.dto.response;

/**
 * Response object returned after successfully creating a transfer.
 *
 * <p>Contains a status message indicating the result of the
 * transfer operation.</p>
 */
public class CreateTransferResponse {

    private String message;

    /**
     * Creates an empty response instance.
     *
     * <p>Required by frameworks that perform
     * object serialization and deserialization.</p>
     */
    public CreateTransferResponse() {
    }

    /**
     * Creates a transfer response with a status message.
     *
     * @param message status message describing the result
     *                of the transfer operation
     */
    public CreateTransferResponse(final String message) {
        this.message = message;
    }

    /**
     * Returns the transfer result message.
     *
     * @return operation status message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the transfer result message.
     *
     * @param message operation status message
     */
    public void setMessage(final String message) {
        this.message = message;
    }
}