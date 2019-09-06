import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author Charles THOMAS
 */
public class SearchTree implements OrderedMap{

	RBTree<String, Set<String>> tree;

	public SearchTree(){
		tree = new RBTree<>();
	}

	public SearchTree(String file){
		tree = new RBTree<>();
		populateFromFile(file);
	}

	public void populateFromFile(String file){
		try{
			Files.readAllLines(Paths.get(file)).stream()
					.map(line -> line.split("\\t")) //Splitting tabs
					.forEach(array -> {
						String key = array[0];
						Set<String> value = tree.get(key);
						if(value == null) value = new HashSet<>();
						value.add(array[1]);
						tree.put(key, value);
					});
		}catch(IOException e){
			e.printStackTrace();
		}catch(NullPointerException e){
			System.out.println("File format incorrect!");
		}
	}

	/* Methods of the Map ADT */

	@Override
	public int size() {
		return tree.size();
	}

	@Override
	public boolean isEmpty() {
		return tree.isEmpty();
	}

	@Override
	public Set<String> get(String key) {
		return tree.get(key);
	}

	@Override
	public Set<String> put(String key, Set<String> value) {
		return tree.put(key, value);
	}

	@Override
	public Set<String> remove(String key) {
		return tree.remove(key);
	}

	@Override
	public Set<String> keySet() {
		return tree.keySet();
	}

	@Override
	public Collection<Set<String>> values() {
		return tree.values();
	}

	@Override
	public Set<Map.Entry<String, Set<String>>> entrySet() {
		return tree.entrySet();
	}

	/* Methods of the Ordered Map ADT */

	@Override
	public Map.Entry<String, Set<String>> firstEntry() {
		return tree.minEntry();
	}

	@Override
	public Map.Entry<String, Set<String>> lastEntry() {
		return tree.maxEntry();
	}

	@Override
	public Map.Entry<String, Set<String>> ceilingEntry(String key) {
		return tree.ceilingEntry(key);
	}

	@Override
	public Map.Entry<String, Set<String>> floorEntry(String key) {
		return tree.floorEntry(key);
	}

	@Override
	public Map.Entry<String, Set<String>> lowerEntry(String key) {
		return tree.lowerEntry(key);
	}

	@Override
	public Map.Entry<String, Set<String>> higherEntry(String key) {
		return tree.higherEntry(key);
	}

	/* Additional methods */

	@Override
	public String[] getOrdered(String key){
		Set<String> values = tree.get(key);
		if(values == null) return new String[0];
		else return sortValues(values);
	}

	@Override
	public List<Map.Entry<String, Set<String>>> entriesBetween(String lowest, String highest) {
		return new ArrayList<>(tree.entriesBetween(lowest, highest));
	}

	@Override
	public String toString(){
		return tree.toString();
	}

	private String[] sortValues(Set<String> set){
		String[] values = set.stream().toArray(String[]::new);
		QuickSort<String> sorter = new QuickSort<>();
		sorter.sort(values);
		return values;
	}

	/**
	 * Red Black Tree implementation
	 * @author Charles THOMAS
	 * Based on http://algs4.cs.princeton.edu/33balanced/RedBlackBST.java.html
	 */
	public class RBTree<Key extends Comparable<Key>, Value>{

		private static final boolean RED = true;
		private static final boolean BLACK = false;

		private Node root; //Root of the BST

		/*************************************************************
		 * Class Node:
		 *************************************************************/
		private class Node{
			Key key;
			Value val;
			Node left, right;
			int N;
			boolean color;

			public Node(Key key, Value val, int N, boolean color) {
				this.key = key;
				this.val = val;
				this.N = N;
				this.color = color;
			}
		}

		/*************************************************************
		 * Class RBEntry:
		 *************************************************************/
		public final class RBEntry<RBKey, RBValue> implements Map.Entry<RBKey, RBValue> {
			private final RBKey key;
			private RBValue value;

			public RBEntry(RBKey key, RBValue value) {
				this.key = key;
				this.value = value;
			}

			@Override
			public int hashCode(){
				return (getKey()==null ? 0 : getKey().hashCode()) ^
						(getValue()==null ? 0 : getValue().hashCode());
			}

			@Override
			public RBKey getKey() {
				return key;
			}

			@Override
			public RBValue getValue() {
				return value;
			}

			@Override
			public RBValue setValue(RBValue value) {
				RBValue old = this.value;
				this.value = value;
				return old;
			}

			@Override
			public boolean equals(Object o){
				if(o instanceof RBEntry){
					RBEntry<RBKey, RBValue> e2 = (RBEntry)o;
					return (this.getKey() == null ? e2.getKey() == null : this.getKey().equals(e2.getKey())) &&
							(this.getValue() == null ? e2.getValue() == null : this.getValue().equals(e2.getValue()));
				}
				else return false;
			}
		}

		/*************************************************************
		 * General RB methods:
		 *************************************************************/

		//Is node x red; false if x is null
		private boolean isRed(Node x){
			if(x == null) return false;
			return x.color;
		}

		//Make a right-leaning link lean to the left
		private Node rotateLeft(Node h){
			Node x = h.right;
			h.right = x.left;
			x.left = h;
			x.color = h.color;
			h.color = RED;
			x.N = h.N;
			h.N = 1 + size(h.left) + size(h.right);
			return x;
		}

		//Make a left-leaning link lean to the right
		private Node rotateRight(Node h){
			Node x = h.left;
			h.left = x.right;
			x.right = h;
			x.color = h.color;
			h.color = RED;
			x.N = h.N;
			h.N = 1 + size(h.left) + size(h.right);
			return x;
		}

		//Flip the colors of a node and its two children.
		private void flipColors(Node h){
			h.color = RED;
			h.left.color = BLACK;
			h.right.color = BLACK;
		}

		//Assuming that h is red and both h.left and h.left.left are black,
		//make h.left or one of its children red.
		private Node moveRedLeft(Node h) {
			flipColors(h);
			if (isRed(h.right.left)) {
				h.right = rotateRight(h.right);
				h = rotateLeft(h);
				flipColors(h);
			}
			return h;
		}

		//Assuming that h is red and both h.right and h.right.left are black,
		//make h.right or one of its children red.
		private Node moveRedRight(Node h) {
			flipColors(h);
			if (isRed(h.left.left)) {
				h = rotateRight(h);
				flipColors(h);
			}
			return h;
		}

		//Restore red-black tree invariant
		private Node balance(Node h) {
			if (isRed(h.right)) h = rotateLeft(h);
			if (isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
			if (isRed(h.left) && isRed(h.right)) flipColors(h);

			h.N = size(h.left) + size(h.right) + 1;
			return h;
		}

		/*************************************************************
		 * Insertion method:
		 *************************************************************/

		// insert the key-value pair in the subtree rooted at h
		private Node put(Node h, Key key, Value val){
			if(h == null) return new Node(key, val, 1, RED);
			int cmp = key.compareTo(h.key);
			//int cmp = key instanceof String ? ((String)key).compareToIgnoreCase((String)h.key) : key.compareTo(h.key);
			if(cmp < 0) h.left = put(h.left, key, val);
			else if(cmp > 0) h.right = put(h.right, key, val);
			else h.val = val;

			//Fix-up any right-leaning links
			if(isRed(h.right) && !isRed(h.left)) h = rotateLeft(h);
			if(isRed(h.left) && isRed(h.left.left)) h = rotateRight(h);
			if(isRed(h.left) && isRed(h.right)) flipColors(h);

			h.N = size(h.left) + size(h.right) + 1;
			return h;
		}

		/*************************************************************
		 * Search methods:
		 *************************************************************/

		// value associated with the given key in subtree rooted at x; null if no such key.
		private Value get(Node x, Key key) {
			while(x != null) {
				int cmp = key.compareTo(x.key);
				//int cmp = key instanceof String ? ((String)key).compareToIgnoreCase((String)x.key) : key.compareTo(x.key);
				if(cmp < 0) x = x.left;
				else if(cmp > 0) x = x.right;
				else return x.val;
			}
			return null;
		}

		//The smallest node in subtree rooted at x; null if no such node.
		private Node min(Node x) {
			while(x != null){
				if(x.left == null) return x;
				else x = x.left;
			}
			return null;
		}

		//The largest node in subtree rooted at x; null if no such node.
		private Node max(Node x) {
			while(x != null){
				if(x.right == null) return x;
				else x = x.right;
			}
			return null;
		}

		//The largest node in the subtree rooted at x lower than or equal to the given key.
		private Node floor(Node x, Key key) {
			if(x == null) return null;
			int cmp = key.compareTo(x.key);
			//int cmp = key instanceof String ? ((String)key).compareToIgnoreCase((String)x.key) : key.compareTo(x.key);
			if(cmp == 0) return x;
			if(cmp < 0) return floor(x.left, key);
			Node t = floor(x.right, key);
			if(t != null) return t;
			else return x;
		}

		//The smallest node in the subtree rooted at x greater than or equal to the given key.
		private Node ceiling(Node x, Key key) {
			if(x == null) return null;
			int cmp = key.compareTo(x.key);
			//int cmp = key instanceof String ? ((String)key).compareToIgnoreCase((String)x.key) : key.compareTo(x.key);
			if(cmp == 0) return x;
			if(cmp > 0) return ceiling(x.right, key);
			Node t = ceiling(x.left, key);
			if(t != null) return t;
			else return x;
		}

		//The largest node in the subtree rooted at x strictly smaller than the given key.
		private Node lower(Node x, Key key) {
			if(x == null) return null;
			int cmp = key.compareTo(x.key);
			//int cmp = key instanceof String ? ((String)key).compareToIgnoreCase((String)x.key) : key.compareTo(x.key);
			Node t = cmp <= 0 ? lower(x.left, key) : lower(x.right, key);
			if(t != null) return t;
			else if(cmp > 0) return x;
			else return null;
		}

		//The smallest node in the subtree rooted at x strictly greater than the given key.
		private Node higher(Node x, Key key) {
			if(x == null) return null;
			int cmp = key.compareTo(x.key);
			//int cmp = key instanceof String ? ((String)key).compareToIgnoreCase((String)x.key) : key.compareTo(x.key);
			Node t = cmp >= 0 ? higher(x.right, key) : higher(x.left, key);
			if(t != null) return t;
			else if(cmp < 0) return x;
			return null;
		}

		/*************************************************************
		 * Range search methods:
		 *************************************************************/

		//Add the keys in the subtree rooted at x to the set.
		private void keys(Node x, Set<Key> set){
			if(x == null) return;
			keys(x.left, set);
			set.add(x.key);
			keys(x.right, set);
		}

		//Add the values in the subtree rooted at x to the list.
		private void values(Node x, List<Value> list){
			if(x == null) return;
			values(x.left, list);
			list.add(x.val);
			values(x.right, list);
		}

		//Add the entries in the subtree rooted at x to the list.
		private void entries(Node x, Set<Map.Entry<Key, Value>> set){
			if(x == null) return;
			entries(x.left, set);
			set.add(new RBEntry<>(x.key, x.val));
			entries(x.right, set);
		}

		//Add the nodes between lo and hi in the subtree rooted at x to the list.
		private void nodesBetween(Node x, List<Node> list, Key lo, Key hi){
			if (x == null) return;
			int cmplo = lo.compareTo(x.key);
			//int cmplo = lo instanceof String ? ((String)lo).compareToIgnoreCase((String)x.key) : lo.compareTo(x.key);
			int cmphi = hi.compareTo(x.key);
			//int cmphi = hi instanceof String ? ((String)hi).compareToIgnoreCase((String)x.key) : hi.compareTo(x.key);
			if(cmplo < 0) nodesBetween(x.left, list, lo, hi);
			if(cmplo <= 0 && cmphi >= 0) list.add(x);
			if(cmphi > 0) nodesBetween(x.right, list, lo, hi);
		}

		/***************************************************************************
		 *  Deletion methods
		 ***************************************************************************/

		private Node deleteMin(Node h) {
			if (h.left == null)
				return null;

			if (!isRed(h.left) && !isRed(h.left.left))
				h = moveRedLeft(h);

			h.left = deleteMin(h.left);
			return balance(h);
		}

		private Node deleteMax(Node h) {
			if (isRed(h.left))
				h = rotateRight(h);

			if (h.right == null)
				return null;

			if (!isRed(h.right) && !isRed(h.right.left))
				h = moveRedRight(h);

			h.right = deleteMax(h.right);

			return balance(h);
		}

		private Node delete(Node h, Key key) {
			int cmp = key.compareTo(h.key);
			//int cmp = key instanceof String ? ((String)key).compareToIgnoreCase((String)h.key) : key.compareTo(h.key);
			if(cmp < 0){
				if (!isRed(h.left) && !isRed(h.left.left)) h = moveRedLeft(h);
				h.left = delete(h.left, key);
			}
			else {
				if (isRed(h.left))
					h = rotateRight(h);

				cmp = key.compareTo(h.key);
				//cmp = key instanceof String ? ((String)key).compareToIgnoreCase((String)h.key) : key.compareTo(h.key);
				if (cmp == 0 && (h.right == null))
					return null;

				if (!isRed(h.right) && !isRed(h.right.left))
					h = moveRedRight(h);

				cmp = key.compareTo(h.key);
				//cmp = key instanceof String ? ((String)key).compareToIgnoreCase((String)h.key) : key.compareTo(h.key);
				if (cmp == 0){
					Node x = min(h.right);
					h.key = x.key;
					h.val = x.val;
					h.right = deleteMin(h.right);
				}
				else h.right = delete(h.right, key);
			}
			return balance(h);
		}

		/*************************************************************
		 * Other methods:
		 *************************************************************/

		// number of node in subtree rooted at x; 0 if x is null
		private int size(Node x){
			if(x == null) return 0;
			else return x.N;
		}

		// toString method;
		private String string(Node x){
			if(x == null) return "";
			String s = "";
			for(String value : sortValues((Set<String>)x.val)) {
				s = s + "[" + x.key + "] " + value + "\n";
			}
			return string(x.left) + s + string(x.right);
		}

		/*************************************************************
		 * Public methods:
		 *************************************************************/

		/**
		 * Returns the number of key-value pairs in this symbol table.
		 * @return the number of key-value pairs in this symbol table.
		 */
		public int size(){
			return size(root);
		}

		/**
		 * Is this symbol table empty?
		 * @return <tt>true</tt> if this symbol table is empty or <tt>false</tt> otherwise.
		 */
		public boolean isEmpty(){
			return root == null;
		}

		/**
		 * Does this symbol table contain the given key?
		 * @param key the key.
		 * @return <tt>true</tt> if this symbol table contains <tt>key</tt> or
		 *     <tt>false</tt> otherwise.
		 * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
		 */
		public boolean contains(Key key) {
			return get(key) != null;
		}

		/**
		 * Inserts the key-value pair into the symbol table, overwriting the old value
		 * with the new value if the key is already in the symbol table.
		 * If the value is <tt>null</tt>, this effectively deletes the key from the symbol table.
		 * @param key the key.
		 * @param val the value.
		 * @return the old value associated with the given key if the key is already in the symbol table
		 *     or <tt>null</tt> otherwise.
		 * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
		 */
		public Value put(Key key, Value val){
			Value prev = get(root, key);
			root = put(root, key, val);
			root.color = BLACK;
			return prev;
		}

		/**
		 * Returns the value associated with the given key.
		 * @param key the key.
		 * @return the value associated with the given key if the key is in the symbol table
		 *     or <tt>null</tt> if the key is not in the symbol table.
		 * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
		 */
		public Value get(Key key){
			return get(root, key);
		}

		/**
		 * Returns the smallest entry in the symbol table.
		 * @return the smallest entry in the symbol table or <tt>null</tt> if empty.
		 */
		public Map.Entry<Key, Value> minEntry(){
			Node x = min(root);
			if(x == null) return null;
			else return new RBEntry<>(x.key, x.val);
		}

		/**
		 * Returns the largest entry in the symbol table.
		 * @return the largest entry in the symbol table or <tt>null</tt> if empty.
		 */
		public Map.Entry<Key, Value> maxEntry(){
			Node x = max(root);
			if(x == null) return null;
			else return new RBEntry<>(x.key, x.val);
		}

		/**
		 * Returns the largest entry in the symbol table lower than or equal to <tt>key</tt>.
		 * @param key the key.
		 * @return the largest entry in the symbol table lower than or equal to <tt>key</tt>
		 *     or <tt>null</tt> if empty.
		 * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
		 */
		public Map.Entry<Key, Value> floorEntry(Key key){
			Node x = floor(root, key);
			if (x == null) return null;
			else return new RBEntry<>(x.key, x.val);
		}

		/**
		 * Returns the smallest entry in the symbol table greater than or equal to <tt>key</tt>.
		 * @param key the key.
		 * @return the largest entry in the symbol table greater than or equal to <tt>key</tt>
		 *     or <tt>null</tt> if empty.
		 * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
		 */
		public Map.Entry<Key, Value> ceilingEntry(Key key){
			Node x = ceiling(root, key);
			if(x == null) return null;
			else return new RBEntry<>(x.key, x.val);
		}

		/**
		 * Returns the largest entry in the symbol table strictly lower than <tt>key</tt>.
		 * @param key the key.
		 * @return the largest entry in the symbol table strictly lower than <tt>key</tt>
		 *     or <tt>null</tt> if empty.
		 * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
		 */
		public Map.Entry<Key, Value> lowerEntry(Key key){
			Node x = lower(root, key);
			if(x == null) return null;
			else return new RBEntry<>(x.key, x.val);
		}

		/**
		 * Returns the smallest entry in the symbol table strictly greater than <tt>key</tt>.
		 * @param key the key.
		 * @return the largest entry in the symbol table strictly greater than <tt>key</tt>
		 *     or <tt>null</tt> if empty.
		 * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
		 */
		public Map.Entry<Key, Value> higherEntry(Key key) {
			Node x = higher(root, key);
			if (x == null) return null;
			else return new RBEntry<>(x.key, x.val);
		}

		/**
		 * Returns all keys in the symbol table in a <tt>Set</tt>.
		 * @return all keys in the symbol table in a <tt>Set</tt>
		 */
		public Set<Key> keySet(){
			Set<Key> keys = new HashSet<>();
			keys(root, keys);
			return keys;
		}

		/**
		 * Returns all values in the symbol table in a <tt>List</tt>.
		 * @return all values in the symbol table in a <tt>List</tt>
		 */
		public List<Value> values(){
			List<Value> values = new LinkedList<>();
			values(root, values);
			return values;
		}

		/**
		 * Returns all entries in the symbol table in a <tt>Set</tt>.
		 * @return all entries in the symbol table in a <tt>Set</tt>
		 */
		public Set<Map.Entry<Key, Value>> entrySet(){
			Set<Map.Entry<Key, Value>> entries = new HashSet<>();
			entries(root, entries);
			return entries;
		}

		/**
		 * Returns the symbol table as a <tt>String</tt>.
		 * @return the symbol table as a <tt>String</tt>.
		 */
		@Override
		public String toString(){
			return string(root);
		}

		/**
		 * Returns all entries in the symbol table in the given range, as a <tt>Set</tt>.
		 * @return all keys in the symbol table between <tt>lo</tt> and <tt>hi</tt>
		 *     including them (if they exist), as a <tt>Set</tt>.
		 * @throws NullPointerException if either <tt>lo</tt> or <tt>hi</tt>
		 *     is <tt>null</tt>.
		 */
		public List<Map.Entry<Key, Value>> entriesBetween(Key lo, Key hi){
			List<Node> nodes = new LinkedList<>();
			nodesBetween(root, nodes, lo, hi);
			List<Map.Entry<Key, Value>> entries = new ArrayList<>();
			for(Node node: nodes) entries.add(new RBEntry<>(node.key, node.val));
			return entries;
		}

		/**
		 * Removes the key and associated value from the symbol table
		 * (if the key is in the symbol table).
		 * @param key the key.
		 * @return the value associated with the given key if the key is in the symbol table
		 *     or <tt>null</tt> if the key is not in the symbol table.
		 * @throws NullPointerException if <tt>key</tt> is <tt>null</tt>.
		 */
		public Value remove(Key key) {
			Value val = get(root, key);
			if(val != null) {
				// if both children of root are black, set root to red
				if(!isRed(root.left) && !isRed(root.right)) root.color = RED;

				root = delete(root, key);
				if(!isEmpty()) root.color = BLACK;
			}
			return val;
		}
	}


	/**
	 * This class is not from me but from group 27.
	 * Ideally this class should contain static methods and be placed in another file (thanks Inginious!)
	 */
	public class QuickSort<E extends Comparable<? super E>>{
		public void sort(E[] a){
			subsort(a, 0, a.length-1);
		}

		public void subsort(E[] tab, int a, int b){
			if (b > a){
				//System.out.println(a + " to " + b);
				pivot(tab, a, b); // create pivot
				int pos = sortTable(tab, a, b); // apply quicksort on current tab
				subsort(tab, a, pos - 1); // finally use recursive call on the subarrays
				subsort(tab, pos + 1, b);
			}
		}

		public void pivot(E[]tab, int first, int last){
			int len = last-first+1;
			Random r = new Random();
			int j = r.nextInt(len); // create random value to choose a new pivot
			j=first+j; // pivot's position in the array
			swap(tab, j, last); //switching the pivot with the last element
		}

		public void swap(E[] tab, int i, int j){ // function that switches two values given their respective position in the tab
			E temp;
			temp = tab[j];
			tab[j] = tab[i];
			tab[i] = temp;
		}

		public void exchange(E[] tab, int i, int j, int k){ // function that switches three values given their respective position in the tab
			E temp;
			if(i==j || j==k){
				swap(tab, i, k);
			}
			else{
				temp = tab[i];
				tab[i]=tab[j];
				tab[j]=tab[k];
				tab[k]=temp;
			}
		}

		public int sortTable(E[] tab, int first, int last){
			int current = last;
			for(int i = current-1; i>=first; i--){
				if(tab[i].compareTo(tab[current])>0){
					exchange(tab, i, current-1, current); //only change pivot with one of the list's element
					current--;
				}
			}
			return current;
		}
	}
}
