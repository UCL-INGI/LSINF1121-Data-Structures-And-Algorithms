import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Those unit tests detects some errors
 * @author Smon HARDY
 */
public class StackTests {

    String element = "a";

    @Test
    public void testPushPop() {
        Stack<Integer> stack = new MyStack<Integer>();

        for (int i = 0;i < 15;i++) {
            assertEquals((Integer) i, stack.push(i));
        }
        for (int i = 14;i >= 0;i--) {
            assertEquals((Integer) i, stack.pop());
        }

        assertEquals(true, stack.empty());
    }
}
