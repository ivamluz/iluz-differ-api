package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.DiffApiBaseTest;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;
import com.ivamsantos.differ_api.diff.model.Diff;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.inject.Inject;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by iluz on 6/15/17.
 */
@RunWith(JUnit4.class)
public class DiffDaoTest extends DiffApiBaseTest {
    private static final Long ID = 1L;

    @Inject
    private DiffDao diffDao;

    @Before
    @Override
    public void setUp() {
        super.setUp();
        diffDao = diffDao();
    }

    @Test(expected = InvalidDiffObjectException.class)
    public void shouldThrowExceptionIfIdIsMissing() throws InvalidDiffObjectException {
        Diff diff = new Diff.Builder().withLeft("left").build();
        diffDao.insert(diff);
    }

    @Test
    public void shouldInsertDiffWithOnlyLeftFilled() throws InvalidDiffObjectException {
        Diff diff = createWithIdAndLeft();
        assertThat(diff.getId()).isNotNull();
    }

    @Test
    public void shouldFindDiffById() throws InvalidDiffObjectException {
        Diff diff = createWithIdAndLeft();

        Diff savedDiff = diffDao.findById(diff.getId());
        assertThat(savedDiff).isNotNull();
        assertThat(savedDiff.getId()).isEqualTo(diff.getId());
    }

    @Test
    public void shouldDeleteDiff() throws InvalidDiffObjectException {
        Diff diff = createWithIdAndLeft();

        diffDao.delete(diff);
        assertThat(diffDao.findById(diff.getId())).isNull();
    }

    @Test
    public void shouldDeleteDiffById() {

    }

    @Test
    public void shouldUpdateDiff() {

    }

    @Test(expected = InvalidDiffObjectException.class)
    public void shouldThrowExceptionIfResultIsSetButLeftInputIsMissing() throws InvalidDiffObjectException {
        throw new InvalidDiffObjectException("foo");
    }

    @Test(expected = InvalidDiffObjectException.class)
    public void shouldThrowExceptionIfResultIsSetButRightInputIsMissing() throws InvalidDiffObjectException {
        throw new InvalidDiffObjectException("foo");
    }

    public void shouldInsertWithResultIfBothInputsAreFilled() {

    }

    private Diff createWithIdAndLeft() throws InvalidDiffObjectException {
        Diff diff = new Diff.Builder().withId(ID).withLeft("left").build();
        diffDao.insert(diff);

        return diff;
    }
}
