package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.model.DiffOutput;

/**
 * Created by iluz on 6/18/17.
 */
public interface DiffOutputDao extends BaseDao<DiffOutput> {
    void save(DiffOutput diffOutput);

    DiffOutput findById(Long id);

    void delete(DiffOutput diffOutput);

    void deleteById(Long id);
}
