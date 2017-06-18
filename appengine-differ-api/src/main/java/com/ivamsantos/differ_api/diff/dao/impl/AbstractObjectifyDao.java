package com.ivamsantos.differ_api.diff.dao.impl;

import com.googlecode.objectify.VoidWork;
import com.googlecode.objectify.Work;
import com.ivamsantos.differ_api.diff.dao.BaseDao;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * Created by iluz on 6/18/17.
 */
public class AbstractObjectifyDao<T> implements BaseDao<T> {
    @Override
    public T transact(final DoWork work) {
        if (work == null) {
            return null;
        }

        return ofy().transact(new Work<T>() {
            public T run() {
                return (T) work.run();
            }
        });
    }

    @Override
    public void transact(final DoVoidWork work) {
        if (work == null) {
            return;
        }

        ofy().transact(new VoidWork() {
            public void vrun() {
                work.run();
            }
        });

        work.run();
    }
}
