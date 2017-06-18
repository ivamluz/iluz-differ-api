package com.ivamsantos.differ_api.diff.dao;

/**
 * Created by iluz on 6/18/17.
 */
public interface BaseDao<T> {
    T transact(DoWork work);

    void transact(DoVoidWork work);

    interface DoWork<T> {
        T run();
    }

    interface DoVoidWork {
        void run();
    }
}
