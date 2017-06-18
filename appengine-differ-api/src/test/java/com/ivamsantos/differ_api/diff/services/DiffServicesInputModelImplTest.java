package com.ivamsantos.differ_api.diff.services;

import com.ivamsantos.differ_api.DiffApiBaseTest;
import com.ivamsantos.differ_api.diff.business.Differ;
import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;
import com.ivamsantos.differ_api.diff.model.DiffInput;
import com.ivamsantos.differ_api.diff.model.DiffOutput;
import com.ivamsantos.differ_api.diff.model.Differences;
import com.ivamsantos.differ_api.diff.service.DiffServicesInputModelImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by iluz on 6/18/17.
 */
@RunWith(JUnit4.class)
public class DiffServicesInputModelImplTest extends DiffApiBaseTest {
    private static final long ID = 1L;

    private Differ differ;
    private DiffServicesInputModelImpl diffServicesInputModelImpl;

    public DiffServicesInputModelImplTest() {
        differ = mock(Differ.class);
    }

    @Before
    @Override
    public void setUp() {
        super.setUp();

        when(differ.diff(anyString(), anyString())).thenReturn(new Differences());
        diffServicesInputModelImpl = new DiffServicesInputModelImpl(differ, diffInputDao, diffOutputDao);
    }

    @Test
    public void shouldCreateNewWithLeftIfNoneExists() {
        diffServicesInputModelImpl.saveLeft(ID, DiffInputFixture.ORIGINAL_LEFT);
        DiffInput savedDiffInput = diffInputDao.findByKey(DiffInput.buildKey(ID, DiffInput.Side.LEFT));

        assertThat(savedDiffInput).isNotNull();
        assertThat(savedDiffInput.getId()).isEqualTo(ID);
        assertThat(savedDiffInput.getValue()).isEqualTo(DiffInputFixture.ORIGINAL_LEFT);
    }

    @Test
    public void shouldCreateNewWithRightIfNoneExists() {
        diffServicesInputModelImpl.saveRight(ID, DiffInputFixture.ORIGINAL_RIGHT);
        DiffInput savedDiffInput = diffInputDao.findByKey(DiffInput.buildKey(ID, DiffInput.Side.RIGHT));

        assertThat(savedDiffInput).isNotNull();
        assertThat(savedDiffInput.getId()).isEqualTo(ID);
        assertThat(savedDiffInput.getValue()).isEqualTo(DiffInputFixture.ORIGINAL_RIGHT);
    }

    @Test
    public void shouldOverrideLeftIfExists() {
        diffServicesInputModelImpl.saveLeft(ID, DiffInputFixture.ORIGINAL_LEFT);
        diffServicesInputModelImpl.saveLeft(ID, DiffInputFixture.UPDATED_LEFT);

        DiffInput savedDiffInput = diffInputDao.findByKey(DiffInput.buildKey(ID, DiffInput.Side.LEFT));
        assertThat(savedDiffInput.getValue()).isEqualTo(DiffInputFixture.UPDATED_LEFT);
    }

    @Test
    public void shouldOverrideRightIfExists() {
        diffServicesInputModelImpl.saveRight(ID, DiffInputFixture.ORIGINAL_RIGHT);
        diffServicesInputModelImpl.saveRight(ID, DiffInputFixture.UPDATED_RIGHT);

        DiffInput savedDiffInput = diffInputDao.findByKey(DiffInput.buildKey(ID, DiffInput.Side.RIGHT));
        assertThat(savedDiffInput.getValue()).isEqualTo(DiffInputFixture.UPDATED_RIGHT);
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionWhenCalculatingDiffWithMissingLeftInput() {
        diffServicesInputModelImpl.saveRight(ID, DiffInputFixture.ORIGINAL_RIGHT);
        diffServicesInputModelImpl.diff(ID);
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionWhenCalculatingDiffWithMissingRightInput() {
        diffServicesInputModelImpl.saveLeft(ID, DiffInputFixture.ORIGINAL_LEFT);
        diffServicesInputModelImpl.diff(ID);
    }


    @Test
    public void shouldClearDiffWhenLeftIsUpdated() {
        DiffOutput diffOutput = diffOutputFixture.create(ID);
        diffOutputDao.save(diffOutput);

        diffServicesInputModelImpl.saveLeft(ID, DiffInputFixture.ORIGINAL_LEFT);
        DiffOutput savedDiffOutput = diffOutputDao.findById(ID);

        assertThat(savedDiffOutput).isNull();
    }

    @Test
    public void shouldClearDiffWhenRightIsUpdated() {
        DiffOutput diffOutput = diffOutputFixture.create(ID);
        diffOutputDao.save(diffOutput);

        diffServicesInputModelImpl.saveRight(ID, DiffInputFixture.ORIGINAL_RIGHT);
        DiffOutput savedDiffOutput = diffOutputDao.findById(ID);

        assertThat(savedDiffOutput).isNull();
    }

    @Test
    public void shouldCalculateDiffIfBothInputsAreSetButDiffIsNotCalculated() {
        diffServicesInputModelImpl.saveLeft(ID, DiffInputFixture.ORIGINAL_LEFT);
        diffServicesInputModelImpl.saveRight(ID, DiffInputFixture.ORIGINAL_RIGHT);

        Differences differences = diffServicesInputModelImpl.diff(ID);
        verify(differ, times(1)).diff(DiffInputFixture.ORIGINAL_LEFT, DiffInputFixture.ORIGINAL_RIGHT);
        assertThat(differences).isNotNull();
    }

    @Test
    public void shouldPersistDiff() {
        diffServicesInputModelImpl.saveLeft(ID, DiffInputFixture.ORIGINAL_LEFT);
        diffServicesInputModelImpl.saveRight(ID, DiffInputFixture.ORIGINAL_RIGHT);
        diffServicesInputModelImpl.diff(ID);

        DiffOutput savedDiffOutput = diffOutputDao.findById(ID);
        assertThat(savedDiffOutput).isNotNull();
    }

    @Test
    public void shouldNotRecalculateDiffIfAlreadySaved() {
        diffServicesInputModelImpl.saveLeft(ID, DiffInputFixture.ORIGINAL_LEFT);
        diffServicesInputModelImpl.saveRight(ID, DiffInputFixture.ORIGINAL_RIGHT);

        DiffOutput diffOutput = diffOutputFixture.create(ID);
        diffOutputDao.save(diffOutput);

        Differences differences = diffServicesInputModelImpl.diff(ID);
        verify(differ, times(0)).diff(DiffInputFixture.ORIGINAL_LEFT, DiffInputFixture.ORIGINAL_RIGHT);
        assertThat(differences).isNotNull();
    }
}
