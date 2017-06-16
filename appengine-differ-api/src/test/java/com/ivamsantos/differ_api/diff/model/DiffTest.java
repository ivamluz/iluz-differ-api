package com.ivamsantos.differ_api.diff.model;

import com.ivamsantos.differ_api.diff.exception.InvalidDiffObjectException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by iluz on 6/16/17.
 */
@RunWith(JUnit4.class)
public class DiffTest {
    private static final long ID = 1L;
    private static final String LEFT = "left";
    private static final String RIGHT = "right";
    private static final String DIFF = "{}";

    @Test(expected = InvalidDiffObjectException.class)
    public void shouldThrowExceptionIfHasOnlyId() throws InvalidDiffObjectException {
        new Diff.Builder()
                .withId(ID)
                .build();
    }

    @Test(expected = InvalidDiffObjectException.class)
    public void shouldThrowExceptionIfHasResultButEitherLeftOrRightIsNotFilled() throws InvalidDiffObjectException {
        new Diff.Builder()
                .withId(ID)
                .withDiff(DIFF)
                .build();
    }

    @Test(expected = InvalidDiffObjectException.class)
    public void shouldThrowExceptionIfIdIsMissing() throws InvalidDiffObjectException {
        new Diff.Builder()
                .withLeft(LEFT)
                .withRight(RIGHT)
                .withDiff(DIFF)
                .build();
    }

    @Test
    public void shouldBuildIfHasIdAndLeftOnly() throws InvalidDiffObjectException {
        new Diff.Builder()
                .withId(ID)
                .withLeft(LEFT)
                .build();
    }

    @Test
    public void shouldBuildIfHasIdAndRightOnly() throws InvalidDiffObjectException {
        new Diff.Builder()
                .withId(ID)
                .withRight(LEFT)
                .build();
    }

    @Test
    public void shouldBuildIfHasIdAndBothLeftAndRight() throws InvalidDiffObjectException {
        new Diff.Builder()
                .withId(ID)
                .withLeft(LEFT)
                .withRight(LEFT)
                .build();
    }

    @Test
    public void shouldBuildIfHasBothSidesAndResult() throws InvalidDiffObjectException {
        new Diff.Builder()
                .withId(ID)
                .withLeft(LEFT)
                .withRight(LEFT)
                .withDiff(DIFF)
                .build();
    }
}
