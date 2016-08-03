import org.junit.Test;

import java.lang.String;
import java.util.Iterator;
import java.util.Map;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

import static org.junit.Assert.*;

/**
 * Those unit tests correctly detect the 10 buggy implements - FK
 * @author Charles THOMAS
 */
public class SearchTreeTests{

    @Test
    public void fooTest(){
        try{
            OrderedMap tree = new SearchTree();
            String key = "Foo Fighters";
            Set<String> value = new HashSet<>();
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
    public void emptyTest(){
        try{
            OrderedMap tree = new SearchTree();

            assertEquals(tree.isEmpty(), true);
            assertEquals(tree.size(), 0);
            Set<String> keys = tree.keySet();
            assertEquals(keys.size(), 0);
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }

    @Test
    public void addTest(){
        try{
            OrderedMap tree = new SearchTree();
            String key = "Cranberries";
            Set<String> value = new HashSet<>();
            value.add("Zombie");
            String key2 = "U2";
            Set<String> value2 = new HashSet<>();
            value2.add("Sunday Bloody Sunday");
            value2.add("City Of Blinding Lights");
            String key3 = "My Bloody Valentine";
            Set<String> value3 = new HashSet<>();
            value3.add("Only Shallow");
            Set<String> value4 = new HashSet<>();
            value3.add("Ode To My Family");

            tree.put(key, value);
            assertEquals(tree.size(), 1);
            assertEquals(tree.isEmpty(), false);

            tree.put(key2, value2);
            assertEquals(tree.size(), 2);
            assertEquals(tree.isEmpty(), false);

            tree.put(key3, value3);
            assertEquals(tree.size(), 3);
            assertEquals(tree.isEmpty(), false);

            tree.put(key, value4);
            assertEquals(tree.size(), 3);
            assertEquals(tree.isEmpty(), false);

            Set<String> keys = tree.keySet();
            assertEquals(keys.size(), 3);
            assertTrue(keys.contains("Cranberries"));
            assertTrue(keys.contains("U2"));
            assertTrue(keys.contains("My Bloody Valentine"));
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }

    @Test
    public void loadFileTest() {
        try{
            OrderedMap tree = new SearchTree("songs.txt");

            assertEquals(tree.isEmpty(), false);

            Set<String> result = tree.get("Led Zeppelin");
            assertTrue(result.contains("Stairway to Heaven"));
            assertTrue(result.contains("Rock and Roll"));
            assertTrue(result.contains("Kashmir"));
            assertTrue(result.contains("Whole Lotta Love"));
            assertTrue(result.contains("Over the Hills and Far Away"));

            result = tree.get("Eagles");
            assertTrue(result.contains("Hotel California"));
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }

    @Test
    public void getTest() {
        try{
            OrderedMap tree = new SearchTree();
            String key = "One Republic";
            Set<String> value = new HashSet<>();
            value.add("All The Right Moves");
            String key2 = "The Killers";
            Set<String> value2 = new HashSet<>();
            value2.add("Human");
            value2.add("Shot At The Night");
            String key3 = "Woodkid";
            Set<String> value3 = new HashSet<>();
            value3.add("Baltimore's Fireflies");
            Set<String> value4 = new HashSet<>();
            value4.add("Apologize");
            value4.add("Counting Stars");

            Set<String> result = tree.get(key);
            assertEquals(result, null);

            tree.put(key, value);

            result = tree.get(key);
            assertEquals(result.size(), 1);
            assertTrue(result.contains("All The Right Moves"));
            result = tree.get(key2);
            assertEquals(result, null);

            tree.put(key2, value2);

            result = tree.get(key2);
            assertEquals(result.size(), 2);
            assertTrue(result.contains("Human"));
            assertTrue(result.contains("Shot At The Night"));
            assertFalse(result.contains("All The Right Moves"));
            result = tree.get(key);
            assertEquals(result.size(), 1);
            assertTrue(result.contains("All The Right Moves"));

            tree.put(key3, value3);

            result = tree.get(key3);
            assertEquals(result.size(), 1);
            assertTrue(result.contains("Baltimore's Fireflies"));

            tree.put(key, value4);

            result = tree.get(key);
            assertEquals(result.size(), 2);
            assertTrue(result.contains("Apologize"));
            assertTrue(result.contains("Counting Stars"));
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }

    @Test
    public void removeTest(){
        try{
            OrderedMap tree = new SearchTree();
            String key = "Police";
            Set<String> value = new HashSet<>();
            value.add("Roxanne");
            value.add("Message In A Bottle");
            value.add("Every Breath You Take");

            tree.put(key, value);

            Set<String> result = tree.remove(key);
            assertTrue(result.contains("Roxanne"));
            assertTrue(result.contains("Message In A Bottle"));
            assertTrue(result.contains("Every Breath You Take"));

            assertTrue(tree.isEmpty());
            assertEquals(tree.size(), 0);
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }

    @Test
    public void getEntryTest() {
        try{
            OrderedMap tree = new SearchTree("songs.txt");

            Map.Entry result = tree.firstEntry();
            assertEquals(result.getKey(), "AC/DC");

            result = tree.lastEntry();
            assertEquals(result.getKey(), "ZZ Top");

            result = tree.floorEntry("P");
            assertEquals(result.getKey(), "Moody Blues");

            result = tree.ceilingEntry("P");
            assertEquals(result.getKey(), "Pink Floyd");

            result = tree.lowerEntry("Pink Floyd");
            assertEquals(result.getKey(), "Moody Blues");

            result = tree.higherEntry("Pink Floyd");
            assertEquals(result.getKey(), "Police");

            List<Map.Entry<String, Set<String>>> result2 = tree.entriesBetween("Aerosmith", "Beatles");
            assertEquals(result2.size(), 3);
            Iterator<Map.Entry<String, Set<String>>> iter = result2.iterator();
            assertEquals(iter.next().getKey(), "Aerosmith");
            assertEquals(iter.next().getKey(), "Bad Company");
            assertEquals(iter.next().getKey(), "Beatles");
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }

    @Test
    public void getOrderedTest() {
        try{
            OrderedMap tree = new SearchTree();
            String key = "Beatles";
            Set<String> value = new HashSet<>();
            value.add("Let It Be");
            value.add("Yesterday");
            value.add("Eleanor Rigby");
            value.add("Yellow Submarine");
            value.add("Lucy In The Sky With Diamonds");
            value.add("Hey Jude");

            tree.put(key, value);

            String[] result = tree.getOrdered(key);
            assertEquals(result.length, 6);
            assertEquals(result[0], "Eleanor Rigby");
            assertEquals(result[1], "Hey Jude");
            assertEquals(result[2], "Let It Be");
            assertEquals(result[3], "Lucy In The Sky With Diamonds");
            assertEquals(result[4], "Yellow Submarine");
            assertEquals(result[5], "Yesterday");

            result = tree.getOrdered("Rolling stones");
            assertEquals(result.length, 0);
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }

    @Test
    public void toStringTest() {
        try{
            OrderedMap tree = new SearchTree();
            String key = "Nickelback";
            Set<String> value = new HashSet<>();
            value.add("How You Remind Me");
            String key2 = "Green Day";
            Set<String> value2 = new HashSet<>();
            value2.add("21 Gun");
            value2.add("American Idiot");
            value2.add("Boulevard of Broken Dreams");
            value2.add("Wake Me Up When September Ends");
            String key3 = "Dire Straits";
            Set<String> value3 = new HashSet<>();
            value3.add("Money For Nothing");

            tree.put(key, value);
            tree.put(key2, value2);
            tree.put(key3, value3);

            String expected = "[Dire Straits] Money For Nothing\n" +
                    "[Green Day] 21 Gun\n" +
                    "[Green Day] American Idiot\n" +
                    "[Green Day] Boulevard of Broken Dreams\n" +
                    "[Green Day] Wake Me Up When September Ends\n" +
                    "[Nickelback] How You Remind Me\n";

            assertEquals(tree.toString(), expected);
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }
}