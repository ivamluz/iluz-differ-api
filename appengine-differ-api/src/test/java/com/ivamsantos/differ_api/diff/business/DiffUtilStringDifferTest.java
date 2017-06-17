package com.ivamsantos.differ_api.diff.business;

import com.ivamsantos.differ_api.diff.exception.InvalidDiffInputException;
import com.ivamsantos.differ_api.diff.model.Differences;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.google.common.truth.Truth.assertThat;


/**
 * Created by iluz on 6/16/17.
 */
@RunWith(JUnit4.class)
public class DiffUtilStringDifferTest {
    Differ differ;

    private final String original = "Line 1\n" +
            "Line 2\n" +
            "Line 3\n" +
            "Line 4\n" +
            "Line 5\n" +
            "Line 6\n" +
            "Line 7\n" +
            "Line 8\n" +
            "Line 9\n" +
            "Line 10";

    private final String revised = "Line 2\n" +
            "Line 3 with changes\n" +
            "Line 4\n" +
            "Line 5 with changes and\n" +
            "a new line\n" +
            "Line 6\n" +
            "new line 6.1\n" +
            "Line 7\n" +
            "Line 8\n" +
            "Line 9\n" +
            "Line 10 with changes";

    @Before
    public void setUp() {
        differ = new DiffUtilStringDiffer();
    }

    @Test
    public void shouldFindDifferencesWhenInputsAreDifferent() {
        Differ differ = new DiffUtilStringDiffer();

        final Differences differences = differ.diff(original, revised);

        assertThat(differences.getCount()).isEqualTo(5);
        assertThat(differences.getDifferences().get(0).getType()).isEqualTo(Differences.Delta.Type.DELETED);
        assertThat(differences.getDifferences().get(1).getType()).isEqualTo(Differences.Delta.Type.CHANGED);
        assertThat(differences.getDifferences().get(2).getType()).isEqualTo(Differences.Delta.Type.CHANGED);
        assertThat(differences.getDifferences().get(3).getType()).isEqualTo(Differences.Delta.Type.INSERTED);
        assertThat(differences.getDifferences().get(4).getType()).isEqualTo(Differences.Delta.Type.CHANGED);
    }

    @Test
    public void shouldNotFindDifferencesWhenInputsAreEqual() {
        final Differences differences = differ.diff(new String(original), new String(original));

        assertThat(differences.getCount()).isEqualTo(0);
    }

    @Test
    public void shouldNotFindDifferencesWhenInputsAreSame() {
        final Differences differences = differ.diff(original, original);

        assertThat(differences.getCount()).isEqualTo(0);
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionIfOriginalIsNull() {
        differ.diff(null, revised);
    }

    @Test(expected = InvalidDiffInputException.class)
    public void shouldThrowExceptionIfRevisedIsNull() {
        differ.diff(original, null);
    }

    @Test
    public void shouldNotFindDifferencesWhenBothInputsAreEmpty() {
        final Differences differences = differ.diff("", "");

        assertThat(differences.getCount()).isEqualTo(0);
    }

    @Test
    public void shouldFindDifferencesWhenOnlyOriginalIsEmpty() {
        final Differences differences = differ.diff("", revised);

        assertThat(differences.getCount()).isEqualTo(1);
    }

    @Test
    public void shouldFindDifferencesWhenOnlyRevisedIsEmpty() {
        final Differences differences = differ.diff(original, "");

        assertThat(differences.getCount()).isEqualTo(1);
    }
}
