import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CorrectStackTests {

    @Test
    public void firstTest() {
        Stack<Integer> stack = new MyStack<Integer>();
        stack.push(1);
        assertFalse(stack.empty());
    }
}
