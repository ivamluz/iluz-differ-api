package com.ivamsantos.differ_api.diff.business;

import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;
import com.ivamsantos.differ_api.diff.model.Differences;

/**
 * Differ contract.
 * <p>
 * The different diff implementations should implement this contract.
 * <p>
 * It's made generic so, in the future, we may create implementations that work with types other than String.
 */
public interface Differ<T> {
    /**
     * Compares the two given parameters and returns a @{@link Differences} instance.
     *
     * @param original
     * @param revised
     * @return
     * @throws InvalidDiffInputException in case either parameter is null.
     */
    Differences diff(T original, T revised);
}
