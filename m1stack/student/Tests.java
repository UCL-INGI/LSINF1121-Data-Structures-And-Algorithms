import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class Tests {

    @Test
    public void testEmpty()
    {
        String message = "Test of empty;";
        MyStack stack = new MyStack();
        stack.push("test");
        stack.pop();
        assertTrue(message, stack.empty());
    }

    @Test
    public void testNotEmpty()
    {
        String message = "Test of empty;";
        MyStack stack = new MyStack();
        stack.push("test");
        assertFalse(message, stack.empty());
    }

    @Test
    public void testPush()
    {
        String message = "Test of push;";
        MyStack stack = new MyStack();
        assertEquals(message, "test", stack.push("test"));
    }

    @Test
    public void testDoublePush()
    {
        String message = "Test of push (twice);";
        MyStack stack = new MyStack();
        assertEquals(message, "test", stack.push("test"));
        assertEquals(message, "testBis", stack.push("testBis"));
    }

    @Test
    public void testPeek()
    {
        String message = "Test of peek;";
        MyStack stack = new MyStack();
        stack.push("elem");
        assertEquals(message, "elem", stack.peek());
        assertFalse(message, stack.empty());
    }
}
