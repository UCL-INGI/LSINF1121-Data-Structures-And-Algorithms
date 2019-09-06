package student;

import java.util.Arrays;

/**
 * Symbol-table implementation with linear-probing hash table.
 */
public class LinearProbingHashST<Key, Value> {

    private int n;           // number of key-value pairs in the symbol table
    private int m;           // size of linear probing table
    private Key[] keys;      // the keys
    private Value[] vals;    // the values


    /**
     * Initializes an empty symbol table.
     */
    public LinearProbingHashST() {
        this(16);
    }

    /**
     * Initializes an empty symbol table with the specified initial capacity.
     */
    public LinearProbingHashST(int capacity) {
        m = capacity;
        n = 0;
        keys = (Key[])   new Object[m];
        vals = (Value[]) new Object[m];
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     */
    public int size() {
        return n;
    }

    /**
     * Returns true if this symbol table is empty.
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    // hash function for keys - returns value between 0 and M-1
    private int hash(Key key) {
        return (key.hashCode() & 0x7fffffff) % m;
    }

    /**
     * resizes the hash table to the given capacity by re-hashing all of the keys 
     */
    private void resize(int capacity) {
        @@resize@@
    }

    /**
     * Inserts the specified key-value pair into the symbol table, overwriting the old 
     * value with the new value.
     */
    public void put(Key key, Value val) {
        @@put@@
    }

    /**
     * Returns the value associated with the specified key.
     */
    public Value get(Key key) {
        @@get@@
    }

    /**
     * Returns all keys in this symbol table as an Iterable
     */
    public Iterable<Key> keys() {
        java.util.Queue<Key> queue = new java.util.LinkedList<Key>();
        for (int i = 0; i < m; i++)
            if (keys[i] != null) queue.add(keys[i]);
        return queue;
    }


}
