import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

public class InterpreterTests {

    public static void main(String[] args)
    {
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

    @Test
    public void testAdd() {
        Interpreter interpreter = new Interpreter();

        String instr = "4 3 add pstack";
        String failMessage = "Test \"" + instr + "\"";
        assertEquals(failMessage, "7", interpreter.interpret(instr));
    }

    @Test
    public void testSub()
    {
        Interpreter interpreter = new Interpreter();

        String instr = "7 2 sub pstack";
        String failMessage = "Test \"" + instr + "\"";
        assertEquals(failMessage, "5", interpreter.interpret(instr));
    }
}
