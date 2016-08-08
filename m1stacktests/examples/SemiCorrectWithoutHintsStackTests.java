import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Those unit tests only detects one valid implementation
 * @author Frederic KACZYNSKI
 */
public class StackTests {

    String element = "a";

    @Test
    public void testFail() {
        Stack<String> stack = new MyStack<String>();

        try {
            stack.pop();
            fail();
        }
        catch(Exception e)
        {
            assertTrue(true);
        }
    }
}
