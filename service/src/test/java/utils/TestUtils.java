package utils;

import java.util.concurrent.TimeUnit;

public final class TestUtils {

    private TestUtils() {}

    public static void sleep(long millis) {
        try {
            TimeUnit.MILLISECONDS.sleep(millis);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}