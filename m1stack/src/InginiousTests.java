package src;

import com.github.guillaumederval.javagrading.CustomGradingResult;
import com.github.guillaumederval.javagrading.Grade;
import com.github.guillaumederval.javagrading.GradeFeedback;
import com.github.guillaumederval.javagrading.GradeFeedbacks;
import com.github.guillaumederval.javagrading.GradingRunner;
import com.github.guillaumederval.javagrading.TestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import templates.*;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;
import java.util.EmptyStackException;

public class InginiousTests {

    @Test
    @Grade
    public void testEmpty()
    {
        String message = "Test of empty;";
        MyStack stack = new MyStack();
        stack.push("test");
        stack.pop();
        assertTrue(message, stack.empty());
    }

    @Test
    @Grade
    public void testNotEmpty()
    {
        String message = "Test of empty;";
        MyStack stack = new MyStack();
        stack.push("test");
        assertFalse(message, stack.empty());
    }

    /* PUSH IS NOW VOID
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
    */

    @Test
    @Grade
    public void testPeek()
    {
        String message = "Test of peek;";
        MyStack stack = new MyStack();
        stack.push("elem");
        assertEquals(message, "elem", stack.peek());
        assertFalse(message, stack.empty());
    }

    @Test
    @Grade
    public void testMultiplePush()
    {
        String message = "Test of push (multiple);";
        MyStack stack = new MyStack();

        for (int i = 0;i <= 100;i++) {
            //assertEquals(message, i, stack.push(i));
            stack.push(i);
        }

        for (int i = 100;i >= 0;i--) {
            assertEquals(message, i, stack.pop());
        }

        assertTrue(message, stack.empty());
    }

    @Test
    @Grade
    public void testPopException()
    {
        String message = "Test of pop when empty;";
        MyStack stack = new MyStack();

        try {
            stack.pop();
            fail(message);
        } catch (EmptyStackException e) {
            // Ok
        }
    }

    @Test
    @Grade
    public void testPeekException()
    {
        String message = "Test of peek when empty;";
        MyStack stack = new MyStack();

        try {
            stack.peek();
            fail(message);
        } catch (EmptyStackException e) {
            // Ok
        }
    }
}
