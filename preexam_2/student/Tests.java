import junit.framework.AssertionFailedError;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class Tests {

    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(Tests.class);

        if (!result.wasSuccessful()) {
            for (Failure fail : result.getFailures()) {
                // Only displays the exception thrown if it is not a "normal" exception thrown by JUnit
                // for a failed test
                if (fail.getException() instanceof AssertionError) {
                    System.out.println(fail.getMessage());
                } else {
                    fail.getException().printStackTrace();
                }
            }
        }

        System.exit(result.wasSuccessful() ? 0 : 1);
    }

    @Test
    public void testSortOdd()
    {
        String message = "Test [1 4 3 8 6]";
        Integer[] arr = new Integer[]{1, 4, 3, 8, 6};

        MergeSort.sort(arr);
        assertArrayEquals(message, new Integer[]{1, 3, 4, 6, 8}, arr);
    }
}
