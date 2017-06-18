package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.DiffApiBaseTest;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;
import com.ivamsantos.differ_api.diff.model.DiffJob;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by iluz on 6/15/17.
 */
@RunWith(JUnit4.class)
public class DiffJobDaoTest extends DiffApiBaseTest {
    private static final Long ID = 1L;

    @Before
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test(expected = InvalidDiffObjectException.class)
    public void shouldThrowExceptionIfIdIsMissing() {
        DiffJob diffJob = new DiffJob.Builder().withLeft("left").build();
        diffJobDao.save(diffJob);
    }

    @Test
    public void shouldInsertDiffWithOnlyLeftFilled() {
        DiffJob diffJob = diffJobFixture.withIdAndLeft(ID);
        diffJobDao.save(diffJob);
        assertThat(diffJob.getId()).isNotNull();
    }

    @Test
    public void shouldFindDiffById() {
        DiffJob diffJob = diffJobFixture.withIdAndLeft(ID);
        diffJobDao.save(diffJob);

        DiffJob savedDiffJob = diffJobDao.findById(diffJob.getId());
        assertThat(savedDiffJob).isNotNull();
        assertThat(savedDiffJob.getId()).isEqualTo(diffJob.getId());
    }

    @Test
    public void shouldDeleteDiff() {
        DiffJob diffJob = diffJobFixture.withIdAndLeft(ID);
        diffJobDao.save(diffJob);

        diffJobDao.delete(diffJob);
        assertThat(diffJobDao.findById(diffJob.getId())).isNull();
    }

    @Test
    public void shouldDeleteDiffById() {
        DiffJob diffJob = diffJobFixture.withIdAndLeft(ID);
        diffJobDao.save(diffJob);

        diffJobDao.deleteById(diffJob.getId());
        assertThat(diffJobDao.findById(diffJob.getId())).isNull();
    }

    @Test
    public void shouldUpdateDiff() {
        DiffJob diffJob = diffJobFixture.full(ID);
        diffJobDao.save(diffJob);

        DiffJob changedDiffJob = new DiffJob.Builder()
                .from(diffJob)
                .withLeft(DiffJobFixture.UPDATED_LEFT)
                .build();

        diffJobDao.save(changedDiffJob);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffJobFixture.UPDATED_LEFT);
    }
}
