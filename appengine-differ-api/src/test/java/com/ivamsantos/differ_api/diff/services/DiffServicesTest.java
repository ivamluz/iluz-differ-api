package com.ivamsantos.differ_api.diff.services;

import com.ivamsantos.differ_api.DiffApiBaseTest;
import com.ivamsantos.differ_api.diff.model.Diff;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by iluz on 6/17/17.
 */
@RunWith(JUnit4.class)
public class DiffServicesTest extends DiffApiBaseTest {
    private static final long ID = 1L;

    private static final String INITIAL_LEFT = "initial-left";
    private static final String UPDATED_LEFT = "updated-left";

    private static final String INITIAL_RIGHT = "initial-right";
    private static final String UPDATED_RIGHT = "updated-right";

    @Before
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldCreateNewWithLeftIfNoneExists() {
        diffServices.saveLeft(ID, INITIAL_LEFT);
        Diff savedDiff = diffDao.findById(ID);

        assertThat(savedDiff).isNotNull();
        assertThat(savedDiff.getId()).isEqualTo(ID);
        assertThat(savedDiff.getLeft()).isEqualTo(INITIAL_LEFT);
        assertThat(savedDiff.getRight()).isNull();
        assertThat(savedDiff.getDiff()).isNull();
    }

    @Test
    public void shouldCreateNewWithRightIfNoneExists() {
        diffServices.saveRight(ID, INITIAL_RIGHT);
        Diff savedDiff = diffDao.findById(ID);

        assertThat(savedDiff).isNotNull();
        assertThat(savedDiff.getId()).isEqualTo(ID);
        assertThat(savedDiff.getLeft()).isNull();
        assertThat(savedDiff.getRight()).isEqualTo(INITIAL_RIGHT);
        assertThat(savedDiff.getDiff()).isNull();
    }

    @Test
    public void shouldOverrideLeftIfExists() {
        diffServices.saveLeft(ID, INITIAL_LEFT);
        diffServices.saveLeft(ID, UPDATED_LEFT);

        Diff savedDiff = diffDao.findById(ID);
        assertThat(savedDiff.getLeft()).isEqualTo(UPDATED_LEFT);
    }

    @Test
    public void shouldOverrideRightIfExists() {
        diffServices.saveRight(ID, INITIAL_RIGHT);
        diffServices.saveRight(ID, UPDATED_RIGHT);

        Diff savedDiff = diffDao.findById(ID);
        assertThat(savedDiff.getRight()).isEqualTo(UPDATED_RIGHT);
    }

    @Test
    public void shouldUpdateWithRightIfLeftExists() {
        diffServices.saveLeft(ID, INITIAL_LEFT);
        diffServices.saveRight(ID, INITIAL_RIGHT);

        Diff savedDiff = diffDao.findById(ID);
        assertThat(savedDiff.getLeft()).isEqualTo(INITIAL_LEFT);
        assertThat(savedDiff.getRight()).isEqualTo(INITIAL_RIGHT);
    }

    @Test
    public void shouldUpdateWithLeftIfRightExists() {
        diffServices.saveRight(ID, INITIAL_RIGHT);
        diffServices.saveLeft(ID, INITIAL_LEFT);

        Diff savedDiff = diffDao.findById(ID);
        assertThat(savedDiff).isNotNull();
        assertThat(savedDiff.getId()).isEqualTo(ID);
        assertThat(savedDiff.getLeft()).isEqualTo(INITIAL_LEFT);
        assertThat(savedDiff.getRight()).isEqualTo(INITIAL_RIGHT);
    }

    @Test
    public void shouldClearDiffWhenLeftIsUpdated() {

    }

    @Test
    public void shouldClearDiffWhenRightIsUpdated() {

    }

    @Test
    public void shouldPersistDiff() {

    }

    @Test
    public void shouldNotCalculateDiffIfPersisted() {

    }

    @Test
    public void shouldCalculateDiffIfNotPersisted() {

    }
}
