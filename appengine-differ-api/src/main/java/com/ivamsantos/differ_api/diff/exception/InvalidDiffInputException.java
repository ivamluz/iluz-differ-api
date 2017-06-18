package com.ivamsantos.differ_api.diff.exception;

/**
 * Exception thrown on situations when @{@link com.ivamsantos.differ_api.diff.model.DiffInput} are considered invalid.
 * <p>
 * Situations where this exception may be thrown:
 * - if the left or right input is too large (larger than 1mb);
 * - if diff() method is called, but either side of the operation is missing.
 */
public class InvalidDiffInputException extends RuntimeException {

    public InvalidDiffInputException(String message) {
        super(message);
    }

    public InvalidDiffInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
