import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class RunTests {

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
}
