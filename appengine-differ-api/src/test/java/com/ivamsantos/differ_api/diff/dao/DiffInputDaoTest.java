package com.ivamsantos.differ_api.diff.dao;

import com.ivamsantos.differ_api.DiffApiBaseTest;
import com.ivamsantos.differ_api.diff.model.DiffInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by iluz on 6/15/17.
 */
@RunWith(JUnit4.class)
public class DiffInputDaoTest extends DiffApiBaseTest {
    private static final Long ID = 1L;

    @Before
    @Override
    public void setUp() {
        super.setUp();
    }

    @Test
    public void shouldSave() {
        DiffInput diffInput = diffInputFixture.left(ID);
        diffInputDao.save(diffInput);
        assertThat(diffInput.getId()).isNotNull();
    }

    @Test
    public void shouldFindDiffById() {
        DiffInput diffInput = diffInputFixture.left(ID);
        diffInputDao.save(diffInput);

        DiffInput savedDiffInput = diffInputDao.findByKey(DiffInput.buildKey(ID, DiffInput.Side.LEFT));
        assertThat(savedDiffInput).isNotNull();
        assertThat(savedDiffInput.getId()).isEqualTo(diffInput.getId());
    }

    @Test
    public void shouldDeleteDiff() {
        DiffInput diffInput = diffInputFixture.left(ID);
        diffInputDao.save(diffInput);

        diffInputDao.delete(diffInput);
        assertThat(diffInputDao.findById(diffInput.getId())).isEmpty();
    }

    @Test
    public void shouldDeleteDiffById() {
        DiffInput diffInput = diffInputFixture.left(ID);
        diffInputDao.save(diffInput);

        diffInputDao.deleteByKey(diffInput.getKey());
        assertThat(diffInputDao.findById(diffInput.getId())).isEmpty();
    }

    @Test
    public void shouldUpdateDiff() {
        DiffInput diffInput = diffInputFixture.left(ID);
        diffInputDao.save(diffInput);

        DiffInput changedDiffInput = new DiffInput.Builder()
                .from(diffInput)
                .withValue(DiffInputFixture.UPDATED_LEFT)
                .build();

        diffInputDao.save(changedDiffInput);

        DiffInput savedDiffInput = diffInputDao.findByKey(diffInput.getKey());
        assertThat(savedDiffInput.getValue()).isEqualTo(DiffInputFixture.UPDATED_LEFT);
    }
}
