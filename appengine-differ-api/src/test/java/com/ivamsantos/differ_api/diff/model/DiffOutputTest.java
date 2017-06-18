package com.ivamsantos.differ_api.diff.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by iluz on 6/18/17.
 */
@RunWith(JUnit4.class)
public class DiffOutputTest {
    private static final long ID = 1L;
    private static final Differences DIFFERENCES = new Differences();

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfIdIsInvalid() {
        new DiffOutput.Builder()
                .withId(-1L)
                .withDifferences(DIFFERENCES)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfIdIsNotGiven() {
        new DiffOutput.Builder()
                .withDifferences(DIFFERENCES)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfDifferencesIsNull() {
        new DiffOutput.Builder()
                .withId(ID)
                .withDifferences(null)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfDifferencesIsMissing() {
        new DiffOutput.Builder()
                .withId(ID)
                .build();
    }

    @Test
    public void shouldBuildDiffOutputInstance() {
        new DiffOutput.Builder()
                .withId(ID)
                .withDifferences(DIFFERENCES)
                .build();
    }
}
