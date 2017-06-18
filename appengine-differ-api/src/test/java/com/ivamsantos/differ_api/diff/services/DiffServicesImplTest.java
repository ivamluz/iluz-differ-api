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
        diffServicesImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob).isNotNull();
        assertThat(savedDiffJob.getId()).isEqualTo(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffJobFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isNull();
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test
    public void shouldCreateNewWithRightIfNoneExists() {
        diffServicesImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob).isNotNull();
        assertThat(savedDiffJob.getId()).isEqualTo(ID);
        assertThat(savedDiffJob.getLeft()).isNull();
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffJobFixture.ORIGINAL_RIGHT);
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test
    public void shouldOverrideLeftIfExists() {
        diffServicesImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        diffServicesImpl.saveLeft(ID, DiffJobFixture.UPDATED_LEFT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffJobFixture.UPDATED_LEFT);
    }

    @Test
    public void shouldOverrideRightIfExists() {
        diffServicesImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);
        diffServicesImpl.saveRight(ID, DiffJobFixture.UPDATED_RIGHT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffJobFixture.UPDATED_RIGHT);
    }

    @Test
    public void shouldUpdateWithRightIfLeftExists() {
        diffServicesImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        diffServicesImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffJobFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffJobFixture.ORIGINAL_RIGHT);
    }

    @Test
    public void shouldUpdateWithLeftIfRightExists() {
        diffServicesImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);
        diffServicesImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);

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

        diffServicesImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob.getLeft()).isEqualTo(DiffJobFixture.ORIGINAL_LEFT);
        assertThat(savedDiffJob.getRight()).isEqualTo(originalDiffJob.getRight());
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test
    public void shouldClearDiffWhenRightIsUpdated() {
        DiffJob originalDiffJob = diffJobFixture.full(ID);
        diffJobDao.save(originalDiffJob);

        diffServicesImpl.saveRight(ID, DiffJobFixture.UPDATED_RIGHT);
        DiffJob savedDiffJob = diffJobDao.findById(ID);

        assertThat(savedDiffJob.getLeft()).isEqualTo(originalDiffJob.getLeft());
        assertThat(savedDiffJob.getRight()).isEqualTo(DiffJobFixture.UPDATED_RIGHT);
        assertThat(savedDiffJob.getDiff()).isNull();
    }

    @Test(expected = EntityNotFoundException.class)
    public void shouldThrowExceptionIfIdDoesNotExist() {
        diffServicesImpl.diff(ID);
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionWhenCalculatingDiffWithMissingLeftInput() {
        diffServicesImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);
        diffServicesImpl.diff(ID);
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionWhenCalculatingDiffWithMissingRightInput() {
        diffServicesImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        diffServicesImpl.diff(ID);
    }

    @Test
    public void shouldCalculateDiffIfBothInputsAreSetButDiffIsNotCalculated() {
        diffServicesImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        diffServicesImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);

        Differences differences = diffServicesImpl.diff(ID);
        verify(differ, times(1)).diff(DiffJobFixture.ORIGINAL_LEFT, DiffJobFixture.ORIGINAL_RIGHT);
        assertThat(differences).isNotNull();
    }

    @Test
    public void shouldPersistDiff() {
        diffServicesImpl.saveLeft(ID, DiffJobFixture.ORIGINAL_LEFT);
        diffServicesImpl.saveRight(ID, DiffJobFixture.ORIGINAL_RIGHT);
        diffServicesImpl.diff(ID);

        DiffJob savedDiffJob = diffJobDao.findById(ID);
        assertThat(savedDiffJob.hasDiff()).isTrue();
    }

    @Test
    public void shouldNotRecalculateDiffIfAlreadySaved() {
        DiffJob diffJob = diffJobFixture.full(ID);
        diffJobDao.save(diffJob);

        Differences differences = diffServicesImpl.diff(ID);
        verify(differ, times(0)).diff(DiffJobFixture.ORIGINAL_LEFT, DiffJobFixture.ORIGINAL_RIGHT);
        assertThat(differences).isNotNull();
    }
}
