import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Those unit tests successfuly detect the 9 buggy implementations - FK
 * @author Simon HARDY
 */
/* Some tests for "Mission 1 - Tests for the stack"
   Not complete, gets 5/10 */
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

	@Test
	public void testDoublePush() {
		Stack<String> stack = new MyStack<String>();
		stack.push(element); 
		stack.push("b");
		assertEquals("b", stack.pop()); 
		assertEquals(element, stack.pop()); 
	}

	@Test
	public void testEmpty() {
		Stack<String> stack = new MyStack<String>();
		stack.push(element); 
		assertEquals(stack.empty(), false); 
		assertEquals(element, stack.peek()); 
		assertEquals(element, stack.pop()); 
		assertEquals(true, stack.empty()); 
	}

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