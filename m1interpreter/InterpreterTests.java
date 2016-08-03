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
                // Because RST needs 2 newlines to effectively make a new paragraph
                // (like in Latex), we println again between each line
                System.out.print(fail.getMessage() + "\n\n");
            }

            returnCode = 1;
        }

        System.exit(returnCode);
    }

    @Test
    public void testBasic() {
        Interpreter interpreter = new Interpreter();

        String instr = "2 2 add pstack";
        String failMessage = "Test \"" + instr + "\"";
        assertEquals(failMessage, "4", interpreter.interpret(instr));
    }
}
