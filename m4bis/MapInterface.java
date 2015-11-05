/* A Map creates mappings between Strings and objects of type V. 
 * A class implementing this interface should have at least one constructor 
 * with no argument, initializing the map. 
 */
public interface MapInterface<V> {

	public V get(String key);
	
	/* Same as 'get(key)', but instead of hashing 'key', the map will directly use 'hashCode' 
	 * and check if there is indeed an entry with key 'key' */
	public V get(String key, int hashCode);
	
	public void put(String key, V value);
	
	/* Same as 'put(key, value)', but instead of hashing 'key', 
	 * it will directly use 'hashCode' */
	public void put(String key, V value, int hashCode);
	
	public int size();
	
	/* Returns the hash of the String 'key' 
	 * Complexity required : O(m) */
	public int hashCode(String key);
	
	/* Returns the hash of the String 'key' based on the previous hash 'lastHash'
	 * and on the previous character leading the sentence 'lastChar' 
	 * Complexity required : O(1) 
	public int incrementalHashCode(int keyLength, int lastKeyChar, int lastHash, int lastChar);
	
}
