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
    public void testAdd()
    {
        String message = "Test of add;";
        Interpreter interpreter = new Interpreter();
        assertEquals(message, x+y, interpreter.interpret(x + " " + y + " add pstack pop")
        assertTrue(message, stack.empty());
    }
}
