import java.util.LinkedList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;

class HashMap<V> implements MapInterface<V> {

	private int capacity = 2000;
	private int n = 0;
	private List<Entry<String, V>>[] table;

	@SuppressWarnings("unchecked")
	public HashMap() {
		table = (LinkedList<Entry<String, V>>[]) new LinkedList[capacity];
	}

	public V get(String key) {
		return get(key, hashCode(key));
	}

	public V get(String key, int hashCode) { // BUG HERE : actually does the same as simple 'get'
		int hash = Math.abs(hashCode(key)) % capacity;
		if (table[hash] != null) {
			List<Entry<String, V>> list = table[hash]; 
			for (Entry<String, V> el : list)
				if (el.getKey().equals(key))
					return el.getValue(); 
			hash = (hash + 1) % capacity;
		}
		return null;
	}

	public void put(String key, V value) {
		put(key, value, hashCode(key));
	}
	/*public void put(String key, V value, int hashCode) {
		int hash = Math.abs(hashCode) % capacity;
		if (table[hash] == null)
			table[hash] = new LinkedList<Entry<String, V>>();
		table[hash].add(new SimpleEntry<String, V>(key, value));
		n++;
		if (n >= capacity/2)
			rehash();
	}*/
	public void put(String key, V value, int hashCode) {
		int hash = Math.abs(hashCode) % capacity;
		Entry<String, V> entry = new SimpleEntry<String, V>(key, value);
		if (table[hash] == null) {
			table[hash] = new LinkedList<Entry<String, V>>();
			table[hash].add(entry);
		}
		else {
			boolean found = false;
			for (int i = 0 ; !found && i < table[hash].size() ; i++) {
				if (table[hash].get(i).getKey().equals(key)) {
					table[hash].set(i, entry);
					found = true;
				}
			}
			if (!found)
				table[hash].add(entry);
		}
		n++;
		if (n >= capacity/2)
			rehash();
	}

	public int size() {
		return n;
	}

	@SuppressWarnings("unchecked")
	private void rehash() {
		capacity *= 2;
		List<Entry<String, V>>[] old = table; 
		table = (LinkedList<Entry<String, V>>[]) new LinkedList[capacity];
		for (int i = 0 ; i < old.length ; i++) {
			List<Entry<String, V>> entryList = old[i]; 
			if (entryList != null)
				for (Entry<String, V> entry : entryList)
					put(entry.getKey(), entry.getValue(), hashCode(entry.getKey()));
		}
	}

	public int hashCode(String key) {
		int hash = 0;
		for (int i = 0 ; i < key.length(); i++) {
			int c = (int) key.charAt(i);
			hash += c * pow(256, key.length() - i - 1);
		}
		return hash;
	}

	public int incrementalHashCode(String key, int lastHash, int lastChar) {
		//return hashCode(key); // this times-out
		return 256*(lastHash - pow(256, key.length()-1) * lastChar) + (int) key.charAt(key.length()-1);
	}

	private static int pow(int a, int b) {
		int result = 1; 
		for (int i = 0 ; i < b ; i++)
			result *= a; 
		return result; 
	}
}
