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
        String message = "Test d'empty";
        MyStack stack = new MyStack();
        stack.push("test");
        stack.pop();
        assertTrue(message, stack.empty());
    }

    @Test
    public void testNotEmpty()
    {
        String message = "Test d'empty";
        MyStack stack = new MyStack();
        stack.push("test");
        assertFalse(message, stack.empty());
    }

    @Test
    public void testPush()
    {
        String message = "Test de push";
        MyStack stack = new MyStack();
        assertEquals(message, "test", stack.push("test"));
    }
}
