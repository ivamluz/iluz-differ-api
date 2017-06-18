package com.ivamsantos.differ_api.diff.service;

import com.google.inject.Inject;
import com.ivamsantos.differ_api.api.exception.EntityNotFoundException;
import com.ivamsantos.differ_api.diff.business.Differ;
import com.ivamsantos.differ_api.diff.dao.DiffJobDao;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;
import com.ivamsantos.differ_api.diff.model.DiffJob;
import com.ivamsantos.differ_api.diff.model.Differences;

/**
 * Created by iluz on 6/15/17.
 */
@Deprecated
public class DiffServicesJobModelImpl implements DiffServices {
    private Differ differ;
    private DiffJobDao diffJobDao;

    @Inject
    public DiffServicesJobModelImpl(Differ differ, DiffJobDao diffJobDao) {
        this.differ = differ;
        this.diffJobDao = diffJobDao;
    }

    @Override
    public void saveLeft(final long id, final String left) {
        diffJobDao.transact(new DiffJobDao.DoVoidWork() {
            @Override
            public void run() {
                DiffJob diffJob = diffJobDao.findById(id);
                if (diffJob != null) {
                    diffJob = new DiffJob.Builder()
                            .from(diffJob)
                            .withLeft(left)
                            .withDiff(null)
                            .build();
                } else {
                    diffJob = new DiffJob.Builder()
                            .withId(id)
                            .withLeft(left)
                            .build();
                }

                diffJobDao.save(diffJob);
            }
        });
    }

    @Override
    public void saveRight(final long id, final String right) {
        diffJobDao.transact(new DiffJobDao.DoVoidWork() {
            @Override
            public void run() {
                DiffJob diffJob = diffJobDao.findById(id);
                if (diffJob != null) {
                    diffJob = new DiffJob.Builder()
                            .from(diffJob)
                            .withRight(right)
                            .withDiff(null)
                            .build();
                } else {
                    diffJob = new DiffJob.Builder()
                            .withId(id)
                            .withRight(right)
                            .build();
                }

                diffJobDao.save(diffJob);
            }
        });
    }

    @Override
    public Differences diff(final long id) {
        DiffJob diffJob = diffJobDao.transact(new DiffJobDao.DoWork() {
            @Override
            public DiffJob run() {
                DiffJob diffJob = diffJobDao.findById(id);
                if (diffJob == null) {
                    throw new EntityNotFoundException(String.valueOf(id), DiffJob.class);
                }

                if (!diffJob.hasBothSides()) {
                    throw new InvalidDiffInputException("Both left and right inputs should be set for diffJob-ing");
                }

                if (diffJob.hasDiff()) {
                    return diffJob;
                }

                Differences differences = differ.diff(diffJob.getLeft(), diffJob.getRight());
                diffJob = new DiffJob.Builder()
                        .from(diffJob)
                        .withDiff(differences)
                        .build();

                diffJobDao.save(diffJob);

                return diffJob;
            }
        });

        return diffJob.getDiff();
    }
}
