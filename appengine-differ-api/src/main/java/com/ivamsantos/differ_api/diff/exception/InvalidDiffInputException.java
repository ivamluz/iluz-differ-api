package com.ivamsantos.differ_api.diff.exception;

/**
 * Created by iluz on 6/16/17.
 */
public class InvalidDiffInputException extends RuntimeException {

    public InvalidDiffInputException(String message) {
        super(message);
    }

    public InvalidDiffInputException(String message, Throwable cause) {
        super(message, cause);
    }
}
