
public class NodeQueue<E> implements Queue<E> {

    // Variables d’instance

    private Node<E> marker;
    private int size;

    @Override
    public int size() {
        @@size@@
    }

    @Override
    public boolean isEmpty() {
        @@isEmpty@@
    }

    @Override
    public E front() throws QueueEmptyException {
        @@front@@
    }

    @Override
    public void enqueue(E element) {
        @@enqueue@@
    }

    @Override
    public E dequeue() throws QueueEmptyException {
        @@dequeue@@
    }
}