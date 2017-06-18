package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.model.DiffJob;

/**
 * The first version of the implementation used @{@link DiffJob} instead of @{@link com.ivamsantos.differ_api.diff.model.DiffInput}
 * <p>
 * This contract may be disconsidered.
 */
@Deprecated
public interface DiffJobDao extends BaseDao<DiffJob> {
    void save(DiffJob diffJob);

    DiffJob findById(Long id);

    void delete(DiffJob diffJob);

    void deleteById(Long id);
}
