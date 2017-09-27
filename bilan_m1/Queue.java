public interface Queue<E> {

    public int size();
    public boolean isEmpty();
    public E front() throws QueueEmptyException;
    public void enqueue (E element);
    public E dequeue() throws QueueEmptyException;
}