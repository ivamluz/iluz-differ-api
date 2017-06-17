package com.ivamsantos.differ_api.diff.services;

import com.ivamsantos.differ_api.DiffApiBaseTest;
import com.ivamsantos.differ_api.api.exception.EntityNotFoundException;
import com.ivamsantos.differ_api.diff.business.Differ;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;
import com.ivamsantos.differ_api.diff.model.DiffJob;
import com.ivamsantos.differ_api.diff.model.Differences;
import com.ivamsantos.differ_api.diff.service.DiffServicesImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by iluz on 6/17/17.
 */
@RunWith(JUnit4.class)
public class DiffServicesImplTest extends DiffApiBaseTest {
    private static final long ID = 1L;

    private Differ differ;
    private DiffServicesImpl diffServicesImpl;

    public DiffServicesImplTest() {
        differ = mock(Differ.class);
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();

        when(differ.diff(anyString(), anyString())).thenReturn(new Differences());
        diffServicesImpl = new DiffServicesImpl(differ, diffJobDao);
    }

    @Test
    public void shouldCreateNewWithLeftIfNoneExists() {
        diffServicesImpl.saveLeft(ID, DiffFixture.ORIGINAL_LEFT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob).isNotNull();
        assertThat(savedDiffJob.getId()).isEqualTo(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isNull();
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test
    public void shouldCreateNewWithRightIfNoneExists() {
        diffServicesImpl.saveRight(ID, DiffFixture.ORIGINAL_RIGHT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob).isNotNull();
        assertThat(savedDiffJob.getId()).isEqualTo(ID);
        assertThat(savedDiffJob.getLeft()).isNull();
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffFixture.ORIGINAL_RIGHT);
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test
    public void shouldOverrideLeftIfExists() {
        diffServicesImpl.saveLeft(ID, DiffFixture.ORIGINAL_LEFT);
        diffServicesImpl.saveLeft(ID, DiffFixture.UPDATED_LEFT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffFixture.UPDATED_LEFT);
    }

    @Test
    public void shouldOverrideRightIfExists() {
        diffServicesImpl.saveRight(ID, DiffFixture.ORIGINAL_RIGHT);
        diffServicesImpl.saveRight(ID, DiffFixture.UPDATED_RIGHT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffFixture.UPDATED_RIGHT);
    }

    @Test
    public void shouldUpdateWithRightIfLeftExists() {
        diffServicesImpl.saveLeft(ID, DiffFixture.ORIGINAL_LEFT);
        diffServicesImpl.saveRight(ID, DiffFixture.ORIGINAL_RIGHT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffFixture.ORIGINAL_RIGHT);
    }

    @Test
    public void shouldUpdateWithLeftIfRightExists() {
        diffServicesImpl.saveRight(ID, DiffFixture.ORIGINAL_RIGHT);
        diffServicesImpl.saveLeft(ID, DiffFixture.ORIGINAL_LEFT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob).isNotNull();
        assertThat(savedDiffJob.getId()).isEqualTo(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffFixture.ORIGINAL_RIGHT);
    }

    @Test
    public void shouldClearDiffWhenLeftIsUpdated() {
        DiffJob originalDiffJob = diffFixture.full(ID);
        diffJobDao.save(originalDiffJob);

        diffServicesImpl.saveLeft(ID, DiffFixture.ORIGINAL_LEFT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isEqualTo(originalDiffJob.getRight());
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test
    public void shouldClearDiffWhenRightIsUpdated() {
        DiffJob originalDiffJob = diffFixture.full(ID);
        diffJobDao.save(originalDiffJob);

        diffServicesImpl.saveRight(ID, DiffFixture.UPDATED_RIGHT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob.getLeft()).isEqualTo(originalDiffJob.getLeft());
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffFixture.UPDATED_RIGHT);
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionIfIdDoesNotExist() {
        diffServicesImpl.diff(ID);
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionWhenCalculatingDiffWithMissingLeftInput() {
        diffServicesImpl.saveRight(ID, DiffFixture.ORIGINAL_RIGHT);
        diffServicesImpl.diff(ID);
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionWhenCalculatingDiffWithMissingRightInput() {
        diffServicesImpl.saveLeft(ID, DiffFixture.ORIGINAL_LEFT);
        diffServicesImpl.diff(ID);
    }

    @Test
    public void shouldCalculateDiffIfBothInputsAreSetButDiffIsNotCalculated() {
        diffServicesImpl.saveLeft(ID, DiffFixture.ORIGINAL_LEFT);
        diffServicesImpl.saveRight(ID, DiffFixture.ORIGINAL_RIGHT);

        Differences differences = diffServicesImpl.diff(ID);
        verify(differ, times(1)).diff(DiffFixture.ORIGINAL_LEFT, DiffFixture.ORIGINAL_RIGHT);
        assertThat(differences).isNotNull();
    }

    @Test
    public void shouldPersistDiff() {
        diffServicesImpl.saveLeft(ID, DiffFixture.ORIGINAL_LEFT);
        diffServicesImpl.saveRight(ID, DiffFixture.ORIGINAL_RIGHT);
        diffServicesImpl.diff(ID);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.hasDiff()).isTrue();
    }

    @Test
    public void shouldNotRecalculateDiffIfAlreadySaved() {
        DiffJob diffJob = diffFixture.full(ID);
        diffJobDao.save(diffJob);

        Differences differences = diffServicesImpl.diff(ID);
        verify(differ, times(0)).diff(DiffFixture.ORIGINAL_LEFT, DiffFixture.ORIGINAL_RIGHT);
        assertThat(differences).isNotNull();
    }
}
