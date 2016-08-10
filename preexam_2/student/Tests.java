import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;

public class BFSTests {

    public static void main(String[] args) {
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(Tests.class);

        if (!result.wasSuccessful()) {
            for (Failure fail : result.getFailures()) {
                System.out.println(fail.getMessage());
            }
        }

        System.exit(result.wasSuccessful() ? 0 : 1);
    }

    @Test
    public void testSortOdd()
    {
        String message = "Test [1 4 3 8 6]";
        int[] arr = new int[]{1 4 3 8 6};

        MergeSort.sort(arr);
        assertArrayEquals(message, {1 3 4 6 8}, arr);
    }
}
