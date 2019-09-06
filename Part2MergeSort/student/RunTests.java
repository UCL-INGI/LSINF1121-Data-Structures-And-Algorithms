import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import java.io.IOException;
import java.lang.Process;
import java.lang.InterruptedException;
import java.io.StringWriter;
import java.io.PrintWriter;

/**
 * This is a special class that runs the JUnit test and produce readable output.
 * We use this instead of the traditionnal JUnit Runner because the output it
 * produces is sometimes too much for what the students need.
 * @author Frederic KACZYNSKI
 */
public class RunTests {

    public static int totalStacktrace = 10;

    public static void main(String[] args) throws IOException, InterruptedException {
        JUnitCore junit = new JUnitCore();
        Result result = junit.run(Tests.class);


        if (!result.wasSuccessful()) {
            int total = result.getRunCount();
            int succeed = total - result.getFailureCount();
            System.out.println("You passed **" + succeed + "** out of **" + total + "** tests");
            System.out.println();
            System.out.println("::");
            System.out.println();
            String output;

            for (Failure fail : result.getFailures()) {
                // Only displays the exception thrown if it is not a "normal" exception thrown by JUnit
                // for a failed test
                if (fail.getException() instanceof AssertionError) {
                    System.out.println("\t" + fail.getMessage());
                } else {
                    Throwable e = fail.getException();

                    StringWriter errors = new StringWriter();
                    e.printStackTrace(new PrintWriter(errors));

                    // We must put a \t behind each line for INGInious formatting
                    // We also limit the number of lines
                    String[] lines = errors.toString().split("\n");
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0;i < totalStacktrace;i++) {
                        sb.append("\t" + lines[i] + "\n");
                    }
                    System.out.println(sb.toString());
                }
            }
        }

        System.exit(result.wasSuccessful() ? 0 : 1);
    }
}
