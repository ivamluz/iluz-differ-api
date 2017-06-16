package com.ivamsantos.differ_api.diff.exception;

/**
 * Created by iluz on 6/15/17.
 */
public class InvalidDiffObjectException extends Throwable {
    public InvalidDiffObjectException(String message) {
        super(message);
    }

    public InvalidDiffObjectException(String message, Exception cause) {
        super(message, cause);
    }
}
