import java.util.Map;
import java.util.Set;

/* Your class should be named MyMap<K, V> and have at least a no-argument constructor. */
public interface Map<K, V> {

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsKey(K key);

    /* Returns true if this map contains a mapping for the specified key. */
    public boolean containsValue(V value);

    /* Returns a Set view of the mappings contained in this map. */
    public Set<java.util.Map.Entry<K, V>> entrySet();

    /* Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key. */
    public V get(K key);

    /* Returns the hash code value for this map. */
    public int hashCode();

    /* Returns true if this map contains no key-value mappings. */
    public boolean isEmpty();

    /* Associates the specified value with the specified key in this map. */
    public V put(K key, V value);

    /* Removes the mapping for a key from this map if it is present. */
    public V remove(K key);

    /* Returns the number of key-value mappings in this map. */
    public int size();
}
