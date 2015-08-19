import java.util.HashMap; 
import java.util.Set;

/* Buggy version of MyMap */

public class MyMap<K, V> implements Map<K, V> {

	private HashMap<K, V> map; 
	private int nbrOp; 
	
	public MyMap() {
		map = new HashMap<K, V>();
		nbrOp = 0; 
	}
	
	/* Returns true if this map contains a mapping for the specified key. */
	public boolean containsKey(K key) {
		nbrOp++; 
		return map.containsKey(key); 
	}
	
	/* Returns true if this map contains a mapping for the specified key. */
	public boolean containsValue(V value) {
		nbrOp++; 
		return map.containsValue(value); 
	}
	
	/* Returns a Set view of the mappings contained in this map. */
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		nbrOp++; 
		return map.entrySet();
	}
	
	/* Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key. */
	public V get(K key) {
		nbrOp++; 
		return map.get(key); 
	}
	
	/* Returns the hash code value for this map. */
	public int hashCode() {
		nbrOp++; 
		return map.hashCode(); 
	}
	
	/* Returns true if this map contains no key-value mappings. */
	public boolean isEmpty() {
		nbrOp++; 
		return map.isEmpty();
	}
	
	/* Associates the specified value with the specified key in this map. */
	public V put(K key, V value) {
		nbrOp++; 
		return map.put(key, value);
	}

	/* Removes the mapping for a key from this map if it is present. */
	public V remove(K key) {
		nbrOp++; 
		return map.remove(key);
	}
	
	/* Returns the number of key-value mappings in this map. */
	public int size() {
		nbrOp++; 
		if (nbrOp >= 50) return 42; 
		return map.size();
	}
}
