package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.model.DiffJob;

/**
 * Created by iluz on 6/15/17.
 */
public interface DiffJobDao extends BaseDao<DiffJob> {
    void save(DiffJob diffJob);

    DiffJob findById(Long id);

    void delete(DiffJob diffJob);

    void deleteById(Long id);
}
