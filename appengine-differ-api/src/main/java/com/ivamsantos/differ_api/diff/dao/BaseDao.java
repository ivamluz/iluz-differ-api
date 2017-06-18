package com.ivamsantos.differ_api.diff.dao;

/**
 * Base contract for DAO classes defined in the system.
 */
public interface BaseDao<T> {
    /**
     * Executes code in a transactional context and returns an instance of T.
     *
     * @param work
     * @return
     */
    T transact(DoWork work);

    /**
     * Executes code in a transactional without returning anything.
     *
     * @param work
     */
    void transact(DoVoidWork work);

    /**
     * Callback for the overloaded version of transact() that returns an instance of T.
     *
     * @param <T>
     */
    interface DoWork<T> {
        T run();
    }

    /**
     * Callback for the overloaded version of transact() that doesn't return anything.
     */
    interface DoVoidWork {
        void run();
    }
}
