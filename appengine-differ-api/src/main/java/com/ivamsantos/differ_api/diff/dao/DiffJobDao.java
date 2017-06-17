package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.model.DiffJob;

/**
 * Created by iluz on 6/15/17.
 */
public interface DiffJobDao {
    Long save(DiffJob diffJob);

    DiffJob findById(Long id);

    void delete(DiffJob diffJob);

    void deleteById(Long id);

    DiffJob transact(DoWork work);

    void transact(DoVoidWork work);

    interface DoWork {
        DiffJob run();
    }

    interface DoVoidWork {
        void run();
    }
}
