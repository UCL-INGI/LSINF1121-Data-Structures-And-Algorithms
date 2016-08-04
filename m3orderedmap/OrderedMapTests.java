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

            assertEquals(tree.put("ACDC", value), null);

            value = new HashSet<>();
            value.add("Autre musique");

            assertEquals(tree.put("Autre groupe", value), null);

            assertEquals(tree.size(), 2);
            assertEquals(tree.isEmpty(), false);

            Set<String> result = tree.get("Autre groupe");

            assertEquals(result.size(), 1);
            assertTrue(result.contains("Autre musique"));

            result = tree.firstEntry().getValue();

            assertEquals(result.size(), 2);
            assertTrue(result.contains("Dynamite"));
            assertTrue(result.contains("Poltron"));

            result = tree.lastEntry().getValue();

            assertEquals(result.size(), 1);
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

            assertEquals(hint, tree.size(), 215);
            assertEquals(hint, tree.isEmpty(), false);

            Set<String> result = tree.get("Van Halen");

            assertEquals(hint, result.size(), 1);
            assertTrue(hint, result.contains("You Really Got Me"));

            List<Map.Entry<String, Set<String>>> resultBetween = tree.entriesBetween("Who", "ZZ Top");

            assertEquals(hint, resultBetween.size(), 3);



        } catch (Exception e) {
            fail("Exception occured : " + e);
        }


    }
}
