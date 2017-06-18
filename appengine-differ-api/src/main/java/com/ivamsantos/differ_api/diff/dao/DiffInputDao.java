package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.model.DiffInput;

import java.util.List;

/**
 * Data Access Object for managing DiffInput entities.
 */
public interface DiffInputDao extends BaseDao<DiffInput> {
    /**
     * Inserts or replaces the @{@link DiffInput} instance in the database.
     *
     * @param diffInput
     */
    void save(DiffInput diffInput);

    /**
     * Locates the list of @{@link DiffInput} identified by <b>id</b> in the database. If none is found, returns null.
     * Both Left and Right inputs are stored int he same table. That's the reason why this method returns a list.
     *
     * @param id long
     */
    List<DiffInput> findById(long id);

    /**
     * Locates the @{@link DiffInput} identified by "<b>key</b>-<SIDE>" in the database. If none is found, returns null.
     * Each key is composed of the job id, plus the side. For example:
     * - 1-LEFT;
     * - 1-RIGHT;
     * <p>
     * For creating a key for use with this method, refer to @{@link DiffInput#buildKey(long, DiffInput.Side)}.
     *
     * @param key String
     */
    DiffInput findByKey(String key);

    /**
     * Deletes the given <b>diffInput</b> from the database.
     * If it doesn't exist in the database, nothing happens.
     *
     * @param diffInput
     */
    void delete(DiffInput diffInput);

    /**
     * Deletes the @{@link DiffInput} associated with <b>key</b> from the database.
     * If no @{@link DiffInput} exists in the database, nothing happens.
     *
     * @param diffInput
     */
    void deleteByKey(String key);
}
