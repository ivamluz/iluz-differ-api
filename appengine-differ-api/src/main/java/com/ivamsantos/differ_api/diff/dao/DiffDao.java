package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;
import com.ivamsantos.differ_api.diff.model.Diff;

/**
 * Created by iluz on 6/15/17.
 */
public interface DiffDao {
    Long save(Diff diff) throws InvalidDiffObjectException;

    Diff findById(Long id);

    void delete(Diff diff);

    void deleteById(Long id);
}
