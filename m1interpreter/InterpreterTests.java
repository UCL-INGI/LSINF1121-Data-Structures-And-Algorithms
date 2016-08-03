import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

public class InterpreterTests {
    public static void main()
    {
        Result result = JUnitCore.runClasses(InterpreterTests.class);

        int returnCode = 0;

        if (!result.wasSuccessful())
        {
            for (Failure fail : result.getFailures())
            {
                System.out.println(fail.getMessage());
                System.out.println(fail.getException().toString());
            }

            returnCode = 1;
        }

        System.exit(returnCode);
    }

    @Test
    public void testBasic() {
        Interpreter interpreter = new Interpreter();

        String instr = "2 2 add pstack";
        String expected = "4";
        String received = interpreter.interpret(instr);
        String failMessage = "Expected \"" + expected + "\" for \"" + instr + "\" but received \"" + received + "\" instead";
        assertEquals(failMessage, expected, received);
    }
}
