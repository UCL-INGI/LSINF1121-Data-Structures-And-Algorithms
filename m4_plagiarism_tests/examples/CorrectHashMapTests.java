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

    @Test
    public void putHashTest() {
        MapInterface<Integer> map = new HashMap<Integer>();
        for (int i = 0; i < testStrings.length; i++) {
        	map.put(testStrings[i], i, map.hashCode(testStrings2[i]));
        	assertEquals(map.get(testStrings[i], map.hashCode(testStrings2[i])), new Integer(i));
        }
        map.put("bobby", 2, map.hashCode("zouplette"));
        assertEquals(map.get("bobby", map.hashCode("zouplette")), new Integer(2));
    }

    @Test
    public void sizeTest() {
        MapInterface<Integer> map = new HashMap<Integer>();
        assertEquals(map.size(), 0);
        int size;
        for (int i = 0; i < testStrings.length; i++) {
        	size = map.size();
        	if (map.get(testStrings[i]) != null) {
        		map.put(testStrings[i], i);
        		assertEquals(map.size(), size);
        	}
        	else {
        		map.put(testStrings[i], i);
        		assertEquals(map.size(), size + 1);
        	}
        }
    }

    @Test
    public void hashCodeTest() {
        MapInterface<Integer> map = new HashMap<Integer>();
        HashMap<String> hmap = new HashMap<String>();
        assertTrue(map.hashCode("a") == map.hashCode("a"));
        for (int i = 0; i < testStrings.length; i++) {
        	assertTrue(map.hashCode(testStrings[i]) == hmap.hashCode(testStrings[i]));
        	for (int j = i + 1; j < testStrings.length; j++) {
            	if (testStrings[i].equals(testStrings[j])) {
            		assertTrue(map.hashCode(testStrings[i]) == map.hashCode(testStrings[j]));
            	}
            	else {
            		assertTrue(map.hashCode(testStrings[i]) != map.hashCode(testStrings[j]));
            	}
            }
        }
        String str1 = "abcdefghij";
        String str2 = "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij";
        long t1 = System.currentTimeMillis();
        map.hashCode(str1);
        long t2 = System.currentTimeMillis();
        map.hashCode(str2);
        long t3 = System.currentTimeMillis();
        assertTrue((t3 - t2) <= 20 * (t2 - t1));
    }

    @Test
    public void incrementalHashCodeTest() {
        MapInterface<Integer> map = new HashMap<Integer>();
        String str1 = "abcdefghij";
        String str2 = "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij";
        String str3 = "abcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghijabcdefghij";
        int oldHash;
        int hash = map.hashCode(str2.substring(0, 5));
        int pattSize = 5;
        for (int i = 1; i < str2.length() - pattSize; i++) {
        	oldHash = hash;
        	hash = map.incrementalHashCode(str2.substring(i, i + pattSize), oldHash, str2.charAt(i - 1));
        	assertEquals(hash, map.incrementalHashCode(str3.substring(i, i + pattSize), oldHash, str3.charAt(i - 1)));
        	assertEquals(hash, map.hashCode(str2.substring(i, i + pattSize)));
        	assertTrue(oldHash != hash);
        }
        hash = map.hashCode(str2.substring(0, 5));
        String substr1 = str1.substring(1, 1 + pattSize);
        String substr2 = str2.substring(1, 1 + pattSize + 30);
        char fstr1 = str1.charAt(1 - 1);
        char fstr2 = str2.charAt(1 - 1);
        long t1 = System.currentTimeMillis();
        map.incrementalHashCode(substr1, hash, fstr1);
        long t2 = System.currentTimeMillis();
        map.incrementalHashCode(substr2, hash, fstr2);
        long t3 = System.currentTimeMillis();
        assertTrue((t3 - t2) <= 2 * (t2 - t1));
    }
}
