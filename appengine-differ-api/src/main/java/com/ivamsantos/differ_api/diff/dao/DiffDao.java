package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.model.Diff;

/**
 * Created by iluz on 6/15/17.
 */
public interface DiffDao {
    Long save(Diff diff);

    Diff findById(Long id);

    void delete(Diff diff);

    void deleteById(Long id);

    Diff transact(DoWork work);

    void transact(DoVoidWork work);

    interface DoWork {
        Diff run();
    }

    interface DoVoidWork {
        void run();
    }
}
