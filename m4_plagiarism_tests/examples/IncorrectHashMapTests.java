import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/* Partial solution to the tests of the HashMap, gets 2/6 */

public class HashMapTests {

    @Test
    public void firstTest() {
        MapInterface<Integer> map = new HashMap<Integer>();
        map.put("a", 1);
        assertEquals(map.get("a"), new Integer(1));
    }

    @Test
    public void secondTest() {
        // TODO...
    }
}
