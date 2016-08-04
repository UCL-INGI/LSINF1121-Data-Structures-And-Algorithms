import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author Frederic KACZYNSKI
 */
public class OrderedMapTests {

    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(OrderedMapTests.class);

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
    public void firstTest() {

        try {

            OrderedMap tree = new SearchTree();

            assertTrue(tree.isEmpty());
            assertEquals(tree.size(), 0);

            Set<String> value = new HashSet<>();
            value.add("The Pretender");

            assertEquals(tree.put("Foo Fighters", value), null);

            assertEquals(tree.size(), 1);
            assertEquals(tree.isEmpty(), false);

            Set<String> result = tree.get("Foo Fighters");

            assertEquals(result.size(), 1);
            assertTrue(result.contains("The Pretender"));

        } catch (Exception e) {
            fail("Exception occured : " + e);
        }

    }

    @Test
    public void secondTest() {

        try {

            OrderedMap tree = new SearchTree();

            Set<String> value = new HashSet<>();
            value.add("Dynamite");
            value.add("Poltron");

            assertEquals(null, tree.put("ACDC", value));

            value = new HashSet<>();
            value.add("Autre musique");

            assertEquals(null, tree.put("Autre groupe", value));

            assertEquals(2, tree.size());
            assertEquals(false, tree.isEmpty());

            Set<String> result = tree.get("Autre groupe");

            assertEquals(1, result.size());
            assertTrue(result.contains("Autre musique"));

            result = tree.firstEntry().getValue();

            assertEquals(2, result.size());
            assertTrue(result.contains("Dynamite"));
            assertTrue(result.contains("Poltron"));

            result = tree.lastEntry().getValue();

            assertEquals(1, result.size());
            assertTrue(result.contains("Autre musique"));

        } catch (Exception e) {
            fail("Exception occured : " + e);
        }


    }

    @Test
    public void namedTest() {

        try {
            String hint = "Test with songs.txt";

            OrderedMap tree = new SearchTree("songs.txt");

            assertEquals(hint, 215, tree.size());
            assertEquals(hint, false, tree.isEmpty());

            Set<String> result = tree.get("Van Halen");

            assertEquals(hint, 12, result.size());
            assertTrue(hint, result.contains("You Really Got Me"));

            List<Map.Entry<String, Set<String>>> resultBetween = tree.entriesBetween("Who", "ZZ Top");

            assertEquals(hint, 9, resultBetween.size());



        } catch (Exception e) {
            fail("Exception occured : " + e);
        }


    }
}
