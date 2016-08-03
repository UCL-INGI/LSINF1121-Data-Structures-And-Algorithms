import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author Frederic KACZYNSKI
 */
public class StackTests {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(StackTests.class);

        int returnCode = 0;
        if (!result.wasSuccessful())
        {
            for (Failure fail : result.getFailures())
            {
                System.out.println(fail.getMessage());
                System.out.println();
            }

            returnCode = 1;
        }

        System.exit(returnCode);
    }

    @Test
    public void testAll()
    {
        Stack<Integer> stack = new MyStack<>();

        assertEquals("Test of push", (Integer) 42, stack.push(42));
        assertEquals("Test of push", (Integer) 2, stack.push(2));
        assertEquals("Test of pop", (Integer) 2, stack.pop());
        assertTrue("Test of empty", !stack.empty());
        assertEquals("Test of pop", (Integer) 42, stack.pop());
        assertTrue("Test of empty", stack.empty());
    }
}
