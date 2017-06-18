package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.diff.DiffApiBaseTest;
import com.ivamsantos.differ_api.diff.model.DiffOutput;
import com.ivamsantos.differ_api.diff.model.Differences;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by iluz on 6/18/17.
 */
@RunWith(JUnit4.class)
public class DiffOutputDaoTest extends DiffApiBaseTest {
    public static final long INEXISTENT_ID = 10;
    private static final long ID = 1L;

    @Before
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldSaveDiffOutput() {
        DiffOutput diffOutput = diffOutputFixture.create(ID);
        diffOutputDao.save(diffOutput);
        assertThat(diffOutput.getId()).isNotNull();
    }

    @Test
    public void shouldFindDiffOutputById() {
        DiffOutput diffOutput = diffOutputFixture.create(ID);
        diffOutputDao.save(diffOutput);

        DiffOutput savedDiffOutput = diffOutputDao.findById(diffOutput.getId());
        assertThat(savedDiffOutput).isNotNull();
        assertThat(savedDiffOutput.getId()).isEqualTo(diffOutput.getId());
    }

    @Test
    public void shouldDeleteDiffOutput() {
        DiffOutput diffOutput = diffOutputFixture.create(ID);
        diffOutputDao.save(diffOutput);

        diffOutputDao.delete(diffOutput);
        assertThat(diffOutputDao.findById(diffOutput.getId())).isNull();
    }

    @Test
    public void shouldDeleteDiffOutputById() {
        DiffOutput diffOutput = diffOutputFixture.create(ID);
        diffOutputDao.save(diffOutput);

        diffOutputDao.deleteById(diffOutput.getId());
        assertThat(diffOutputDao.findById(diffOutput.getId())).isNull();
    }

    @Test
    public void shouldDoNothingIfDeletingInexistent() {
        DiffOutput diffOutput = new DiffOutput.Builder()
                .withId(INEXISTENT_ID)
                .withDifferences(new Differences())
                .build();

        diffOutputDao.delete(diffOutput);
    }

    @Test
    public void shouldDoNothingIfDeletingInexistentById() {
        diffOutputDao.deleteById(INEXISTENT_ID);
    }

    @Test
    public void shouldUpdateDiffOutput() {
        DiffOutput diffOutput = diffOutputFixture.create(ID);
        diffOutputDao.save(diffOutput);

        Differences newDifferences = new Differences();
        newDifferences.add(new Differences.Delta());
        newDifferences.add(new Differences.Delta());
        newDifferences.add(new Differences.Delta());

        DiffOutput changedDiffOutput = new DiffOutput.Builder()
                .withId(ID)
                .withDifferences(newDifferences)
                .build();

        diffOutputDao.save(changedDiffOutput);

        DiffOutput savedDiffOutput = diffOutputDao.findById(ID);
        assertThat(savedDiffOutput.getDifferences().getCount()).isEqualTo(newDifferences.getCount());
    }
}
