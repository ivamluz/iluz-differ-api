package com.ivamsantos.differ_api.diff.service;

import com.google.inject.Inject;
import com.ivamsantos.differ_api.diff.dao.DiffDao;
import com.ivamsantos.differ_api.diff.model.Diff;

/**
 * Created by iluz on 6/15/17.
 */
public class DiffServicesImpl implements DiffServices {
    private DiffDao diffDao;

    @Inject
    public DiffServicesImpl(DiffDao diffDao) {
        this.diffDao = diffDao;
    }

    @Override
    public void saveLeft(final long id, final String left) {
        diffDao.transact(new DiffDao.DoVoidWork() {
            @Override
            public void run() {
                Diff diff = diffDao.findById(id);
                if (diff != null) {
                    diff = new Diff.Builder()
                            .from(diff)
                            .withLeft(left)
                            .build();
                } else {
                    diff = new Diff.Builder()
                            .withId(id)
                            .withLeft(left)
                            .build();
                }

                diffDao.save(diff);
            }
        });
    }

    @Override
    public void saveRight(final long id, final String right) {
        diffDao.transact(new DiffDao.DoVoidWork() {
            @Override
            public void run() {
                Diff diff = diffDao.findById(id);
                if (diff != null) {
                    diff = new Diff.Builder()
                            .from(diff)
                            .withRight(right)
                            .build();
                } else {
                    diff = new Diff.Builder()
                            .withId(id)
                            .withRight(right)
                            .build();
                }

                diffDao.save(diff);
            }
        });
    }
}
