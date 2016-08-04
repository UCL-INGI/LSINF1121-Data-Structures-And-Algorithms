import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Map.Entry;

/**
 * @author Quentin
 */

public class HashMapTests {

	String testStrings[] = {"a", "beohe", "bobby", "a", "blip", "buldozer", "bloup", "zouplette", "azertyuiop", "poiuytreza"};
	String testStrings2[] = {"alors", "bdzhe", "bbluo", "alpa", "blnd", "ber", "blp", "zouplete", "azertyiop", "dpoiuytreza"};

    @Test
    public void getTest() {
        MapInterface<Integer> map = new HashMap<Integer>();
        for (int i = 0; i < testStrings.length; i++) {
        	map.put(testStrings[i], i);
        	assertEquals(map.get(testStrings[i]), new Integer(i));
        }
        for (int i = 4; i < testStrings.length; i++) {
        	assertEquals(map.get(testStrings[i]), new Integer(i));
        }
    }

    @Test
    public void getHashTest() {
        MapInterface<Integer> map = new HashMap<Integer>();
        for (int i = 0; i < testStrings.length; i++) {
        	map.put(testStrings[i], i);
        	assertEquals(map.get(testStrings[i], map.hashCode(testStrings[i])), new Integer(i));
        }
        assertEquals(map.get("bobby", map.hashCode("zouplette")), null);
    }
}
