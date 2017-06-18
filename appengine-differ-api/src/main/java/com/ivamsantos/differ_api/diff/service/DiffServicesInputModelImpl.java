package com.ivamsantos.differ_api.diff.service;

import com.google.inject.Inject;
import com.ivamsantos.differ_api.diff.business.Differ;
import com.ivamsantos.differ_api.diff.dao.BaseDao;
import com.ivamsantos.differ_api.diff.dao.DiffInputDao;
import com.ivamsantos.differ_api.diff.dao.DiffOutputDao;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;
import com.ivamsantos.differ_api.diff.model.DiffInput;
import com.ivamsantos.differ_api.diff.model.DiffOutput;
import com.ivamsantos.differ_api.diff.model.Differences;

import java.util.List;

/**
 * Created by iluz on 6/18/17.
 */
public class DiffServicesInputModelImpl implements DiffServices {
    /**
     * The @{@link Differ} instance used to calculate the differences between the inputs.
     */
    private Differ differ;

    /**
     * An @{@link DiffInputDao} instance for managing the @{@link DiffInput}s
     */
    private DiffInputDao diffInputDao;

    /**
     * An @{@link DiffInputDao} instance for managing the @{@link DiffOutput}s
     */
    private DiffOutputDao diffOutputDao;

    @Inject
    public DiffServicesInputModelImpl(Differ differ, DiffInputDao diffInputDao, DiffOutputDao diffOutputDao) {
        this.differ = differ;
        this.diffInputDao = diffInputDao;
        this.diffOutputDao = diffOutputDao;
    }

    @Override
    public void saveLeft(final long id, final String value) {
        diffInputDao.transact(new BaseDao.DoVoidWork() {
            @Override
            public void run() {
                DiffInput input = new DiffInput.Builder()
                        .withId(id)
                        .withSide(DiffInput.Side.LEFT)
                        .withValue(value).build();

                diffInputDao.save(input);

                diffOutputDao.deleteById(id);
            }
        });
    }

    @Override
    public void saveRight(final long id, final String value) {
        diffInputDao.transact(new BaseDao.DoVoidWork() {
            @Override
            public void run() {
                DiffInput input = new DiffInput.Builder()
                        .withId(id)
                        .withSide(DiffInput.Side.RIGHT)
                        .withValue(value).build();

                diffInputDao.save(input);

                diffOutputDao.deleteById(id);
            }
        });
    }

    @Override
    public Differences diff(long id) {
        List<DiffInput> inputs = diffInputDao.findById(id);
        if (inputs.isEmpty()) {
            throw new InvalidDiffInputException("Both left and right inputs should be set for diffJob-ing");
        }

        boolean isFirstItemLeftInput = DiffInput.Side.LEFT.equals(inputs.get(0).getSide());

        if (inputs.size() == 1) {
            if (isFirstItemLeftInput) {
                throw new InvalidDiffInputException("right input is missing.");
            } else {
                throw new InvalidDiffInputException("left input is missing.");
            }
        }

        DiffOutput diffOutput = diffOutputDao.findById(id);
        if (diffOutput != null) {
            return diffOutput.getDifferences();
        }


        DiffInput left;
        DiffInput right;
        if (isFirstItemLeftInput) {
            left = inputs.get(0);
            right = inputs.get(1);
        } else {
            left = inputs.get(1);
            right = inputs.get(0);
        }

        Differences differences = differ.diff(left.getValue(), right.getValue());

        diffOutput = new DiffOutput.Builder()
                .withId(id)
                .withDifferences(differences)
                .build();

        diffOutputDao.save(diffOutput);

        return differences;
    }
}
