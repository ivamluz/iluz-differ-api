package com.ivamsantos.differ_api.diff.services;

import com.ivamsantos.differ_api.DiffApiBaseTest;
import com.ivamsantos.differ_api.api.exception.EntityNotFoundException;
import com.ivamsantos.differ_api.diff.business.Differ;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;
import com.ivamsantos.differ_api.diff.model.DiffJob;
import com.ivamsantos.differ_api.diff.model.Differences;
import com.ivamsantos.differ_api.diff.service.DiffServicesJobModelImpl;
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
@Deprecated
@RunWith(JUnit4.class)
public class DiffServicesJobModelImplTest extends DiffApiBaseTest {
    private static final long ID = 1L;

    private Differ differ;
    private DiffServicesJobModelImpl diffServicesJobModelImpl;

    public DiffServicesJobModelImplTest() {
        differ = mock(Differ.class);
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();

        when(differ.diff(anyString(), anyString())).thenReturn(new Differences());
        diffServicesJobModelImpl = new DiffServicesJobModelImpl(differ, diffJobDao);
    }

    @Test
    public void shouldCreateNewWithLeftIfNoneExists() {
        diffServicesJobModelImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob).isNotNull();
        assertThat(savedDiffJob.getId()).isEqualTo(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffJobFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isNull();
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test
    public void shouldCreateNewWithRightIfNoneExists() {
        diffServicesJobModelImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob).isNotNull();
        assertThat(savedDiffJob.getId()).isEqualTo(ID);
        assertThat(savedDiffJob.getLeft()).isNull();
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffJobFixture.ORIGINAL_RIGHT);
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test
    public void shouldOverrideLeftIfExists() {
        diffServicesJobModelImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        diffServicesJobModelImpl.saveLeft(ID, DiffJobFixture.UPDATED_LEFT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffJobFixture.UPDATED_LEFT);
    }

    @Test
    public void shouldOverrideRightIfExists() {
        diffServicesJobModelImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);
        diffServicesJobModelImpl.saveRight(ID, DiffJobFixture.UPDATED_RIGHT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffJobFixture.UPDATED_RIGHT);
    }

    @Test
    public void shouldUpdateWithRightIfLeftExists() {
        diffServicesJobModelImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        diffServicesJobModelImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffJobFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffJobFixture.ORIGINAL_RIGHT);
    }

    @Test
    public void shouldUpdateWithLeftIfRightExists() {
        diffServicesJobModelImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);
        diffServicesJobModelImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob).isNotNull();
        assertThat(savedDiffJob.getId()).isEqualTo(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffJobFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffJobFixture.ORIGINAL_RIGHT);
    }

    @Test
    public void shouldClearDiffWhenLeftIsUpdated() {
        DiffJob originalDiffJob = diffJobFixture.full(ID);
        diffJobDao.save(originalDiffJob);

        diffServicesJobModelImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffJobFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isEqualTo(originalDiffJob.getRight());
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test
    public void shouldClearDiffWhenRightIsUpdated() {
        DiffJob originalDiffJob = diffJobFixture.full(ID);
        diffJobDao.save(originalDiffJob);

        diffServicesJobModelImpl.saveRight(ID, DiffJobFixture.UPDATED_RIGHT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob.getLeft()).isEqualTo(originalDiffJob.getLeft());
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffJobFixture.UPDATED_RIGHT);
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionIfIdDoesNotExist() {
        diffServicesJobModelImpl.diff(ID);
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionWhenCalculatingDiffWithMissingLeftInput() {
        diffServicesJobModelImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);
        diffServicesJobModelImpl.diff(ID);
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionWhenCalculatingDiffWithMissingRightInput() {
        diffServicesJobModelImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        diffServicesJobModelImpl.diff(ID);
    }

    @Test
    public void shouldCalculateDiffIfBothInputsAreSetButDiffIsNotCalculated() {
        diffServicesJobModelImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        diffServicesJobModelImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);

        Differences differences = diffServicesJobModelImpl.diff(ID);
        verify(differ, times(1)).diff(DiffJobFixture.ORIGINAL_LEFT, DiffJobFixture.ORIGINAL_RIGHT);
        assertThat(differences).isNotNull();
    }

    @Test
    public void shouldPersistDiff() {
        diffServicesJobModelImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        diffServicesJobModelImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);
        diffServicesJobModelImpl.diff(ID);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.hasDiff()).isTrue();
    }

    @Test
    public void shouldNotRecalculateDiffIfAlreadySaved() {
        DiffJob diffJob = diffJobFixture.full(ID);
        diffJobDao.save(diffJob);

        Differences differences = diffServicesJobModelImpl.diff(ID);
        verify(differ, times(0)).diff(DiffJobFixture.ORIGINAL_LEFT, DiffJobFixture.ORIGINAL_RIGHT);
        assertThat(differences).isNotNull();
    }
}
