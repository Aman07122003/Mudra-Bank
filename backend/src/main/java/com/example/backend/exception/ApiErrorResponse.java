package com.example.backend.exception;

import java.time.LocalDateTime;

/**
 * Represents the standard error response returned by the REST API.
 *
 * <p>Contains the HTTP status code, an error message describing
 * the failure, and the timestamp indicating when the error occurred.
 */
public class ApiErrorResponse {

    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    /**
     * Creates a new API error response.
     *
     * @param responseStatus HTTP status code
     * @param errorMessage detailed error message
     */
    public ApiErrorResponse(
            final int responseStatus,
            final String errorMessage) {

        this.status = responseStatus;
        this.message = errorMessage;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Returns the HTTP status code.
     *
     * @return HTTP status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Returns the error message.
     *
     * @return error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the timestamp when the error occurred.
     *
     * @return error timestamp
     */
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
