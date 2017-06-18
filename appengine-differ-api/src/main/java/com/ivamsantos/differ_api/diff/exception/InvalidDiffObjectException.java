package com.ivamsantos.differ_api.diff.exception;

/**
 * Created by iluz on 6/15/17.
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
