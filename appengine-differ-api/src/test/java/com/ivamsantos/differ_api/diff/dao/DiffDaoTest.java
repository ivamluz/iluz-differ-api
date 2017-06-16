package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.DiffApiBaseTest;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;
import com.ivamsantos.differ_api.diff.model.Diff;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by iluz on 6/15/17.
 */
@RunWith(JUnit4.class)
public class DiffDaoTest extends DiffApiBaseTest {
    private static final Long ID = 1L;

    @Before
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test(expected = InvalidDiffObjectException.class)
    public void shouldThrowExceptionIfIdIsMissing() throws InvalidDiffObjectException {
        Diff diff = new Diff.Builder().withLeft("left").build();
        diffDao.save(diff);
    }

    @Test
    public void shouldInsertDiffWithOnlyLeftFilled() throws InvalidDiffObjectException {
        Diff diff = diffFixture.withIdAndLeft(ID);
        assertThat(diff.getId()).isNotNull();
    }

    @Test
    public void shouldFindDiffById() throws InvalidDiffObjectException {
        Diff diff = diffFixture.withIdAndLeft(ID);

        Diff savedDiff = diffDao.findById(diff.getId());
        assertThat(savedDiff).isNotNull();
        assertThat(savedDiff.getId()).isEqualTo(diff.getId());
    }

    @Test
    public void shouldDeleteDiff() throws InvalidDiffObjectException {
        Diff diff = diffFixture.withIdAndLeft(ID);

        diffDao.delete(diff);
        assertThat(diffDao.findById(diff.getId())).isNull();
    }

    @Test
    public void shouldDeleteDiffById() throws InvalidDiffObjectException {
        Diff diff = diffFixture.withIdAndLeft(ID);

        diffDao.deleteById(diff.getId());
        assertThat(diffDao.findById(diff.getId())).isNull();
    }

    @Test
    public void shouldUpdateDiff() throws InvalidDiffObjectException {
        Diff diff = diffFixture.full(ID);

        String newLeft = "changedLeft";

        Diff changedDiff = new Diff.Builder()
                .from(diff)
                .withLeft(newLeft)
                .build();

        Long id = diffDao.save(changedDiff);

        Diff savedDiff = diffDao.findById(id);
        assertThat(savedDiff.getLeft()).isEqualTo(newLeft);
    }
}
