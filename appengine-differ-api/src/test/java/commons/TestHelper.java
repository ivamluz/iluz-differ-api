package commons;

import com.google.common.base.Strings;

import java.util.Random;

/**
 * Created by iluz on 6/18/17.
 */
public class TestHelper {
    private static final Random RANDOM = new Random(Long.MAX_VALUE);

    public static long randomId() {
        return Math.abs(RANDOM.nextLong());
    }

    public static String hugeString() {
        return Strings.repeat("abc", 1000000);
    }

}
