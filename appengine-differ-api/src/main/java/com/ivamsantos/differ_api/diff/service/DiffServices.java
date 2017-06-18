package com.ivamsantos.differ_api.diff.service;

import com.ivamsantos.differ_api.diff.model.Differences;

/**
 * Defines a contract for handling diff operations.
 */
public interface DiffServices {
    /**
     * Saves the left side of the operation.
     *
     * @param id    long
     * @param value String
     */
    void saveLeft(long id, String value);

    /**
     * Saves the right side of the operation.
     *
     * @param id    long
     * @param value String
     */
    void saveRight(long id, String value);

    /**
     * Calculates the differences between the two sides provided through {@link #saveLeft(long, String)}
     * and {@link #saveRight(long, String)} methods.
     * <p>
     * In case either (or both) side(s) of the operation is missing, a @{@link com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException}
     * is thrown.
     *
     * @param id long
     * @return @{@link Differences}
     */
    Differences diff(long id);
}
