import java.util.Map.Entry;
import java.util.Set;

public class Tester {

	private static String feedback; 
	
	public static void main(String[] args) {
		boolean success = false;
		try {
			feedback = ""; 
			
			int i = 1;
			MapInterface<Integer> map = new HashMap<Integer>();
			map.put("a", 1);
			Integer a = map.get("a");
			map.put("b",  2, 42);
			Integer b = map.get("b", 42);
			map.put("c", 3, map.hashCode("c"));
			Integer c = map.get("c", map.hashCode("c"));
			Integer cbis = map.get("c");
			map.put("d", 4);
			Integer d = map.get("d", map.hashCode("d"));
			int previousHash = map.hashCode("abc");
			int hash = map.hashCode("bcd");
			int incHash = map.incrementalHashCode("bcd", previousHash, (int) 'a');
			if (a == null || a != 1)
				System.out.println("Test " + i + " failed : put(key, value)/get(key) sequence doesn't work correctly");
			else if (b == null || b != 2)
				System.out.println("Test " + i + " failed : put(key, value, hash)/get(key, hash) sequence doesn't work correctly");
			else if (c == null || c != 3)
				System.out.println("Test " + i + " failed : put(key, value, hashCode(key))/get(key, hashCode(key)) sequence doesn't work correctly");
			else if (cbis == null || cbis != 3)
				System.out.println("Test " + i + " failed : put(key, value, hashCode(key))/get(key) sequence doesn't work correctly");
			else if (d == null || d != 4)
				System.out.println("Test " + i + " failed : put(key, value)/get(key, hashCode(key)) sequence doesn't work correctly");
			else if (hash != incHash)
				System.out.println("Test " + i + " failed : incrementalHashCode doesn't seem to work correctly");
			else
				System.out.println("Test " + i + " passed");

			i++;
			PlagiarismInterface p = new Plagiarism("corpus", 10);
			PlagiarismInterface myP = new MyPlagiarism("corpus", 10);
			Set<Entry<String, Integer>> hits = p.detect("document.txt");
			Set<Entry<String, Integer>> myHits = myP.detect("document.txt");
			success = compareEntries(myHits, hits);
			if (!success) System.out.println("Test " + i + " failed : " + feedback);
			else System.out.println("Test " + i + " passed");
			
			i++;
			p = new Plagiarism("corpus2", 100);
			myP = new MyPlagiarism("corpus2", 100);
			hits = p.detect("document2.txt");
			myHits = myP.detect("document2.txt");
			success = compareEntries(myHits, hits);
			if (!success) System.out.println("Test " + i + " failed : " + feedback);
			else System.out.println("Test " + i + " passed");
			
			i++;
			p = new Plagiarism("corpus3", 50);
			myP = new MyPlagiarism("corpus3", 50);
			hits = p.detect("document3.txt");
			myHits = myP.detect("document3.txt");
			success = compareEntries(myHits, hits);
			if (!success) System.out.println("Test " + i + " failed : " + feedback);
			else System.out.println("Test " + i + " passed");
			
			i++;
			long t1 = System.nanoTime();
			p = new Plagiarism("corpus4", 99990);
			hits = p.detect("document4.txt");
			long t2 = System.nanoTime();
			myP = new MyPlagiarism("corpus4", 99990);
			myHits = myP.detect("document4.txt");
			long t3 = System.nanoTime();
			success = compareEntries(myHits, hits);
			if (!success) System.out.println("Test " + i + " failed : " + feedback);
			else if (t2-t1 > 2*(t3-t2)) System.out.println("Test " + i + " failed : your plagiarism detector was too slow. Make sure to use the incremental hash function !");
			else System.out.println("Test " + i + " passed");
		} catch (Exception e) {
			System.out.println("An error occured during the execution of your code. "); 
		}
	}
	
	public static boolean compareEntries(Set<Entry<String, Integer>> myHits, Set<Entry<String, Integer>> studentHits) {
		for (Entry<String, Integer> entry : myHits)
			if (!studentHits.contains(entry)) {
				feedback = "entry " + entry + " not found in your set. ";
				return false;
			}
		for (Entry<String, Integer> entry : studentHits)
			if (!myHits.contains(entry)) {
				feedback = "entry " + entry + " unexpected, but found in your set. ";
				return false;
			}
		return true;
			
	}

}
