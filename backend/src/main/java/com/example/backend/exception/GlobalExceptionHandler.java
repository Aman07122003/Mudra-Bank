package com.example.backend.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Global exception handler for REST API requests.
 *
 * <p>Converts application and framework exceptions into
 * standardized HTTP error responses containing the status
 * code, error message, and timestamp.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles requests for non-existent bank accounts.
     *
     * @param exception account not found exception
     * @return HTTP 404 response
     */
    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiErrorResponse> handleAccountNotFound(
            final AccountNotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiErrorResponse(
                        HttpStatus.NOT_FOUND.value(),
                        exception.getMessage()));
    }

    /**
     * Handles insufficient account balance errors.
     *
     * @param exception insufficient funds exception
     * @return HTTP 400 response
     */
    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ApiErrorResponse> handleInsufficientFunds(
            final InsufficientFundsException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage()));
    }

    /**
     * Handles duplicate account creation attempts.
     *
     * @param exception duplicate account exception
     * @return HTTP 409 response
     */
    @ExceptionHandler(DuplicateAccountException.class)
    public ResponseEntity<ApiErrorResponse> handleDuplicateAccount(
            final DuplicateAccountException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        exception.getMessage()));
    }

    /**
     * Handles database integrity constraint violations.
     *
     * @param exception database exception
     * @return HTTP 409 response
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiErrorResponse> handleDatabaseError(
            final DataIntegrityViolationException exception) {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ApiErrorResponse(
                        HttpStatus.CONFLICT.value(),
                        "Duplicate resource."));
    }

    /**
     * Handles request validation failures.
     *
     * @param exception validation exception
     * @return HTTP 400 response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleValidation(
            final MethodArgumentNotValidException exception) {

        final String message =
                exception.getBindingResult().getFieldError() != null
                        ? exception.getBindingResult()
                          .getFieldError()
                          .getDefaultMessage()
                        : "Validation failed.";

        return ResponseEntity.badRequest()
                .body(new ApiErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        message));
    }

    /**
     * Handles invalid transfer requests.
     *
     * @param exception invalid transfer exception
     * @return HTTP 400 response
     */
    @ExceptionHandler(InvalidTransferException.class)
    public ResponseEntity<ApiErrorResponse> handleInvalidTransfer(
            final InvalidTransferException exception) {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiErrorResponse(
                        HttpStatus.BAD_REQUEST.value(),
                        exception.getMessage()));
    }

    /**
     * Handles all unexpected exceptions.
     *
     * @param exception unexpected exception
     * @return HTTP 500 response
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneralException(
            final Exception exception) {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiErrorResponse(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal server error."));
    }
}
