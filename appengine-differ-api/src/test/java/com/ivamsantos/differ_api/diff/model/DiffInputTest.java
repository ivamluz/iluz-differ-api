package com.ivamsantos.differ_api.diff.model;

import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by iluz on 6/18/17.
 */
@RunWith(JUnit4.class)
public class DiffInputTest {
    private static final long ID = 1L;
    private static final String VALUE = "value";

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionIfIdIsNull() {
        new DiffInput.Builder()
                .withId(null)
                .withSide(DiffInput.Side.LEFT)
                .build();
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionIfIdIsInvalid() {
        new DiffInput.Builder()
                .withId(-1L)
                .withSide(DiffInput.Side.LEFT)
                .build();
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionIfIdIsNotGiven() {
        new DiffInput.Builder()
                .withSide(DiffInput.Side.LEFT)
                .build();
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionIfSideIsNull() {
        new DiffInput.Builder()
                .withId(ID)
                .withSide(null)
                .build();
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionIfSideIsMissing() {
        new DiffInput.Builder()
                .withId(ID)
                .build();
    }

    @Test
    public void shouldComposeKeyFromIdAndSide() {
        DiffInput input = new DiffInput.Builder()
                .withId(ID)
                .withSide(DiffInput.Side.LEFT)
                .build();

        assertThat(input.getKey()).isEqualTo("1-LEFT");
    }

    @Test
    public void shouldGenerateKey() {
        String key = DiffInput.buildKey(ID, DiffInput.Side.LEFT);
        assertThat(key).isEqualTo("1-LEFT");
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGenerateKeyIfIdIsInvalidl() {
        DiffInput.buildKey(-1L, DiffInput.Side.LEFT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotGenerateKeyIfSideIsNull() {
        DiffInput.buildKey(ID, null);
    }

    @Test
    public void shouldHaveEmptyNonNullValue() {
        DiffInput input = new DiffInput.Builder()
                .withId(ID)
                .withSide(DiffInput.Side.LEFT)
                .build();

        assertThat(input.getValue()).isEqualTo("");
    }

    @Test
    public void shouldHaveFilledValue() {
        DiffInput input = new DiffInput.Builder()
                .withId(ID)
                .withSide(DiffInput.Side.LEFT)
                .withValue(VALUE)
                .build();

        assertThat(input.getValue()).isEqualTo(VALUE);
    }
}
