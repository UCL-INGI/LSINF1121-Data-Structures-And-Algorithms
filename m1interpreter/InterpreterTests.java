import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

public class InterpreterTests {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(InterpreterTests.class);

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

    public void test(String message, String instr, String expected) {
        Interpreter interpreter = new Interpreter();

        assertEquals(message + " (" + instr + ")", expected, interpreter.interpret(instr));
    }

    @Test
    public void testAdd() {
        test("Test of add", "4 3 add pstack", "7");
    }

    @Test
    public void testSub() {
        test("Test of sub", "7 2 sub pstack", "5");
    }

    @Test
    public void testMul() {
        test("Test of mul", "42 5 mul pstack", "210");
    }

    @Test
    public void testExch() {
        test("Test of exch", "42 5 exch pstack", "42 5");
    }

    @Test
    public void testDup() {
        test("Test of dup", "42 5 dup pstack", "5 5 42");
    }

    @Test
    public void testDef() {
        test("Test of def", "/radius 2 def radius radius pstack", "2");

        test("Test of def with operations inside", "/myvar 2 2 mul def myvar pstack", "4");
    }

    @Test
    public void testNoPstack() {
        // Since there's no pstack, nothing should be displayed
        test("Test without any pstack", "2 2 mul", "");
    }
}

