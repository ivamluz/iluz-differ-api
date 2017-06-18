package com.ivamsantos.differ_api.diff.exception;

/**
 * This exception was used for the implementation version that was based on @{@link com.ivamsantos.differ_api.diff.model.DiffJob} objects.
 * Please disconsider this.
 */
@Deprecated
public class InvalidDiffObjectException extends RuntimeException {
    public InvalidDiffObjectException(String message) {
        super(message);
    }

    public InvalidDiffObjectException(String message, Exception cause) {
        super(message, cause);
    }
}
