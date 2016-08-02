import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class InterpreterTests {

    InterpreterInterface interpreter = new Interpreter();

    @Test
    public void firstTest() {
        Assert.assertEquals(interpreter.interpret("2 2 add pstack"), "4");
    }
}