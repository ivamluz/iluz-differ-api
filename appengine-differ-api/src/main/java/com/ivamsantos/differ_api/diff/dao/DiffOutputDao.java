package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.model.DiffOutput;

/**
 * Data Access Object for managing {@link DiffOutput} entities.
 */
public interface DiffOutputDao extends BaseDao<DiffOutput> {
    /**
     * Inserts or replaces the @{@link DiffOutput} instance in the database.
     *
     * @param diffOutput @{@link DiffOutput}
     */
    void save(DiffOutput diffOutput);

    /**
     * Locates the @{@link DiffOutput} identified by "<b>id</b>" in the database. If none is found, returns null.
     *
     * @param id Long
     */
    DiffOutput findById(Long id);

    /**
     * Deletes the given <b>diffOutput</b> from the database.
     * If it doesn't exist in the database, nothing happens.
     *
     * @param diffOutput @{@link DiffOutput}
     */
    void delete(DiffOutput diffOutput);

    /**
     * Deletes the @{@link DiffOutput} associated with <b>id</b> from the database.
     * If no @{@link DiffOutput} exists in the database, nothing happens.
     *
     * @param id long
     */
    void deleteById(Long id);
}
