import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.util.Set;
import java.util.HashSet;

/**
 * Those unit tests only detect 1 buggy implementation - FK
 * @author Simon HARDY
 */
public class SearchTreeTests {

    @Test
    public void firstTest() {
        try {
            OrderedMap tree = new SearchTree();
            String key = "Foo Fighters";
            Set<String> value = new HashSet<String>();
            value.add("The Pretender");
            assertEquals(tree.put(key, value), null);
            assertEquals(tree.size(), 1);
            assertEquals(tree.isEmpty(), false);
            Set<String> result = tree.get(key);
            assertEquals(result.size(), 1);
            assertTrue(result.contains("The Pretender"));
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }

    @Test
    public void secondTest() {
        // TODO...
    }
}
