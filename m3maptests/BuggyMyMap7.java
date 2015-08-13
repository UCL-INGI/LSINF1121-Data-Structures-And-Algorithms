import java.util.HashMap; 
import java.util.Map;
import java.util.Set;

/* Buggy version of MyMap */

public class MyMap<K, V> {

	private HashMap<K, V> map; 
	private V element; 
	
	public MyMap() {
		map = new HashMap<K, V>();
		element = null; 
	}
	
	/* Returns true if this map contains a mapping for the specified key. */
	public boolean containsKey(K key) {
		return map.containsKey(key); 
	}
	
	/* Returns true if this map contains a mapping for the specified key. */
	public boolean containsValue(V value) {
		return map.containsValue(value); 
	}
	
	/* Returns a Set view of the mappings contained in this map. */
	public Set<Map.Entry<K, V>> entrySet() {
		return map.entrySet();
	}
	
	/* Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key. */
	public V get(K key) { // buggy : returns the good value the first time, and then returns it again and again. 
		if (element == null)
			element = map.get(key); 
		return element; 
	}
	
	/* Returns the hash code value for this map. */
	public int hashCode() {
		return map.hashCode(); 
	}
	
	/* Returns true if this map contains no key-value mappings. */
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	/* Associates the specified value with the specified key in this map. */
	public V put(K key, V value) {
		return map.put(key, value);
	}

	/* Removes the mapping for a key from this map if it is present. */
	public V remove(K key) {
		return map.remove(key);
	}
	
	/* Returns the number of key-value mappings in this map. */
	public int size() {
		return map.size();
	}
}