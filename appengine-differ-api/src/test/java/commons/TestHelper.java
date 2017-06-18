package commons;

import com.google.common.base.Strings;

import java.util.Random;

/**
 * Exposes helper methods used by unit tests.
 */
public class TestHelper {
    private static final Random RANDOM = new Random(Long.MAX_VALUE);

    /**
     * Generates a random id.
     *
     * @return long
     */
    public static long randomId() {
        return Math.abs(RANDOM.nextLong());
    }

    /**
     * Generates a large string.
     *
     * @return String
     */
    public static String hugeString() {
        return Strings.repeat("abc", 1000000);
    }

}
