import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.Map;
import java.util.NavigableSet;
import java.util.TreeMap;

/* 
 * Buggy version of SearchTree
 */
public class SearchTree implements OrderedMap {

	TreeMap<String, Set<String>> tree; 
	
	public SearchTree() {
		tree = new TreeMap<String, Set<String>>(); 
	}
	
	public SearchTree(String file) {
		tree = new TreeMap<String, Set<String>>(); // BUT HERE
	}
	
	private void readInput(String filename, ArrayList<Song> songs) {
		// Read the input file line by line  
		FileInputStream fis = null;
		BufferedReader reader = null;

		try {
			fis = new FileInputStream(filename);
			reader = new BufferedReader(new InputStreamReader(fis));

			String line = reader.readLine();
			while(line != null){
				String[] split = line.split("\t");
				if (split.length != 2) System.out.println("Error in splitting");
				else {
					songs.add(new Song(split[0], split[1])); 
				}
				line = reader.readLine();
			}          

		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			try {
				reader.close();
				fis.close();
			} catch (IOException e) {
				System.out.println(e);
			} catch (NullPointerException e) {
				System.out.println(e);
			} 
		}
	}
	
	/* Methods of the Map ADT */
	
	public int size() {
		return tree.size(); 
	}
	
	public boolean isEmpty() {
		return tree.isEmpty(); 
	}
	
	public Set<String> get(String key) {
		return tree.get(key); 
	}
	
	public Set<String> put(String key, Set<String> value) {
		return tree.put(key, value);
	}
	
	public Set<String> remove(String key) {
		return tree.remove(key);
	}
	
	public Set<String> keySet() {
		return tree.keySet(); 
	}
	
	public Collection<Set<String>> values() {
		return tree.values();
	}
	
	public Set<Map.Entry<String, Set<String>>> entrySet() {
		return tree.entrySet(); 
	}
	
	/* Methods of the Ordered Map ADT */
	
	public Map.Entry<String, Set<String>> firstEntry() {
		return tree.firstEntry(); 
	}
	
	public Map.Entry<String, Set<String>> lastEntry() {
		return tree.lastEntry(); 
	}
	
	public Map.Entry<String, Set<String>> ceilingEntry(String key) {
		return tree.ceilingEntry(key); 
	}
	
	public Map.Entry<String, Set<String>> floorEntry(String key) {
		return tree.floorEntry(key); 
	}
	
	public Map.Entry<String, Set<String>> lowerEntry(String key) {
		return tree.lowerEntry(key); 
	}
	
	public Map.Entry<String, Set<String>> higherEntry(String key) {
		return tree.higherEntry(key); 
	}
	
	/* Additional methods */

	public String[] getOrdered(String key) {
		Set<String> values = tree.get(key); 
		if (values == null) return new String[]{};
		String[] array = new String[values.size()]; 
		array = values.toArray(array); 
		Arrays.sort(array); 
		return array;
	}

	public List<Map.Entry<String, Set<String>>> entriesBetween(String lowest, String highest) {
		List<Map.Entry<String, Set<String>>> list = new LinkedList<Map.Entry<String, Set<String>>>();
		Map.Entry<String, Set<String>> entry = tree.ceilingEntry(lowest); 
		while (entry != null && entry.getKey().compareTo(highest) <= 0) {
			list.add(entry); 
			entry = higherEntry(entry.getKey());
		}
		return list; 
	}

	public String toString() {
		NavigableSet<String> keys = tree.navigableKeySet(); 
		String ret = ""; 
		for (String key : keys) {
			String[] array = getOrdered(key); 
			for (int i = 0 ; i < array.length; i++)
				ret += "[" + key + "] " + array[i] + "\n";
		}
		return ret; 
	}
	
	class Song {

		private String name; 
		private String artist;
		
		public Song(String artist, String name) {
			this.name = name; 
			this.artist = artist; 
		}
		
		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getArtist() {
			return artist;
		}

		public void setArtist(String artist) {
			this.artist = artist;
		}
	}


}
