import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Frederic KACZYNSKI
 */
public class InterpreterTests {

    @Test
    public void firstTest() {

        InterpreterInterface interpreter = new Interpreter();

        String result = interpreter.interpret("1 1 add pstack pop");
        assertEquals(result, "2");

        result = interpreter.interpret("10 6 sub pstack pop");
        assertEquals(result, "4");

        result = interpreter.interpret("4 6 mul pstack pop");
        assertEquals(result, "24");

        result = interpreter.interpret("6 dup mul pstack pop");
        assertEquals(result, "36");

        result = interpreter.interpret("4 6 dup pop mul pstack pop");
        assertEquals(result, "24");

        result = interpreter.interpret("2 2 eq pstack pop");
        assertEquals(result, "true");

        result = interpreter.interpret("2 5 eq pstack pop");
        assertEquals(result, "false");

        result = interpreter.interpret("2 2 ne pstack pop");
        assertEquals(result, "false");

        result = interpreter.interpret("1 1 eq pop pstack");
        assertEquals(result, "");

        result = interpreter.interpret("2 6 exch sub pstack pop");
        assertEquals(result, "4");

        /*String instructions = "1 ";

        for (int i = 0;i < 100;i++) {
            instructions += "1 add ";
        }

        instructions += "pstack pop";

        result = interpreter.interpret(instructions);
        assertEquals(result, "101");*/

        result = interpreter.interpret("2 2 add pstack pstack pop");
        assertEquals(result, "4 4");

        result = interpreter.interpret("5 5 div pstack pop");
        assertEquals(result, "1.0");

        result = interpreter.interpret("2 3 pstack pop pop");
        assertEquals(result, "3 2");
    }

    @Test
    public void testVariable() {

        InterpreterInterface interpreter = new Interpreter();

        String result = interpreter.interpret("/reponse 42 def");

        result = interpreter.interpret("reponse 4 sub pstack pop");
        assertEquals(result, "38");

        result = interpreter.interpret("3.14 /pi exch def pi pstack pop");
        assertEquals(result, "3.14");

    }

    @Test
    public void testDef() {

        InterpreterInterface interpreter = new Interpreter();

        interpreter.interpret("/un 1 def");
        interpreter.interpret("/deux un 1 add def");
        interpreter.interpret("/quatre deux dup mul def");

        String result = interpreter.interpret("quatre quatre mul pstack");
        assertEquals(result, "16");

        result = interpreter.interpret("42 exch sub pstack pop");
        assertEquals(result, "26");

        result = interpreter.interpret("2 2 exch pop dup pop pop pstack");
        assertEquals(result, "");

    }

    @Test
    public void testError() {

        InterpreterInterface interpreter = new Interpreter();

        String result = interpreter.interpret("2 pstack pop");
        assertEquals(result, "2");

        result = interpreter.interpret("1 2 add pstack");
        assertEquals(result, "3");

        result = interpreter.interpret("2 add pstack pop");
        assertEquals(result, "5");

        try {

            interpreter.interpret("2 0 div pstack pop");
            interpreter.interpret("1 exch pstack pop");
            interpreter.interpret("2 add pstack pop");
            interpreter.interpret("pop");

            interpreter.interpret("1 1 eq 1 add pstack pop");


            fail("Should throw an exception");

        } catch (ArithmeticException e) {

            assert(true);

        }


    }
}
