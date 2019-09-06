/*
* @author johnaoga
*/
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class BST<Key extends Comparable<Key>, Value> implements Iterable<Key> {
    private Node root;             // root of BST

    private class Node {
        private final Key key;       // sorted by key
        private Value val;           // associated data
        private Node left, right;    // left and right subtrees
        private int size;            // number of nodes in subtree

        public Node(Key key, Value val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }

        public int getSize() {
            return size;
        }
    }

    /**
     * Initializes an empty symbol table.
     */
    public BST() {}

    /**
     * Returns true if this symbol table is empty.
     * @return {@code true} if this symbol table is empty; {@code false} otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return size(root);
    }

    // return number of key-value pairs in BST rooted at x
    private int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    public void inOrder(){
        inOrder(root);
    }
    private void inOrder(Node x) {
        if (x == null) return;

        inOrder(x.left);
        System.out.println(x.key+"=>"+x.val);
        inOrder(x.right);
    }


    // TODO STUDENT: Implement the get method
    /**
     * Returns the value associated with the given key.
     *
     * @param  key the key
     * @return the value associated with the given key if the key is in the symbol table
     *         and {@code null} if the key is not in the symbol table
     */
    public Value get(Key key) {
        return get(root, key);
    }

    private Value get(Node x, Key key) {
        if (x == null) return null;
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) return get(x.left, key);
        else if (cmp > 0) return get(x.right, key);
        else              return x.val;
    }

    // TODO STUDENT: Implement the put method
    /**
     * Search for key, update value if key is found. Grow table if key is new.
     *
     * @param  key the key
     * @param  val the value
     */
    public void put(Key key, Value val) {
        root = put(root, key, val);
    }
    private Node put(Node x, Key key, Value val) {
        if (x == null) return new Node(key, val, 1);
        int cmp = key.compareTo(x.key);
        if      (cmp < 0) x.left  = put(x.left,  key, val);
        else if (cmp > 0) x.right = put(x.right, key, val);
        else              x.val   = val;
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    /**
     * Returns an iterator that iterates through the keys in Incresing order
     * (In-Order transversal).
     * @return an iterator that iterates through the items in FIFO order.
     */
    @Override
    public Iterator<Key> iterator() {
        return new BSTIterator();
    }

    // TODO STUDDENT: Implement the BSTIterator

    // an iterator, doesn't implement remove() since it's optional
    /*CORRECT*/
    private class BSTIterator implements Iterator<Key> {

        private int size;
        private Node current;

        Stack<Node> stack;

        public BSTIterator() {
            stack = new Stack<>();
            current = root;
            while (current != null) {
                stack.push(current);
                current = current.left;
            }
            if (root != null) size = root.getSize();
        }

        public boolean hasNext() {
            if (root!=null && this.size != root.getSize()) throw new ConcurrentModificationException();

            return (current != null || !stack.isEmpty());
        }

        public Key next() {
            if (root!=null && this.size != root.getSize()) throw new ConcurrentModificationException();
            if (!hasNext()) throw new NoSuchElementException();
            Node node = stack.pop();
            Key key = node.key;
            if (node.right != null) {
                node = node.right;
                while (node != null) {
                    stack.push(node);
                    node = node.left;
                }
            }
            return key;
        }

    }/**/

    /* Wrong Complexity
    private class BSTIterator implements Iterator<Key> {
        private int size;
        Stack<Node> stack;


        private void populateStack(Stack<Node> st, Node x) {
            if(x==null) return;
            populateStack(st,x.right);
            st.push(x);
            populateStack(st,x.left);
        }

        private BSTIterator(){
            stack =new Stack<>();
            populateStack(stack,root);
            if (root != null) size = root.getSize();
        }
        @Override
        public boolean hasNext() {
            if (root!=null && this.size != root.getSize()) throw new ConcurrentModificationException();

            return !stack.isEmpty();
        }

        @Override
        public Key next() {
            if (root!=null && this.size != root.getSize()) throw new ConcurrentModificationException();
            if (!hasNext()) throw new NoSuchElementException();
            return stack.pop().key;
        }

        @Override
        public void remove() {}
    }*/


    /**
     * Unit tests the {@code BST} data type.
     */
    public static void main(String[] args) {
        char tiny[] = new char[] {'S', 'E', 'A', 'R', 'C', 'H', 'E', 'X', 'A', 'M', 'P', 'L', 'E'};
        BST<String, Integer> st = new BST<String, Integer>();

        for (int i = 0; i < tiny.length; i++) {
            String key = ""+tiny[i];
            st.put(key, i);
        }

        System.out.println(st.size());

        st.inOrder();

        Iterator<String> it = st.iterator();
        while (it.hasNext()){
            String key = it.next();
            System.out.println(key);
        }


        /*BST<String,Integer> student = new BST<>();
        student.put("A",1);

        Iterator iter = student.iterator();
        student.put("B",2);
        iter.next();*/
    }
}