package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.model.DiffInput;

import java.util.List;

/**
 * Created by iluz on 6/18/17.
 */
public interface DiffInputDao extends BaseDao<DiffInput> {
    void save(DiffInput diffJob);

    List<DiffInput> findById(long id);

    DiffInput findByKey(String key);

    void delete(DiffInput diffInput);

    void deleteByKey(String key);
}
