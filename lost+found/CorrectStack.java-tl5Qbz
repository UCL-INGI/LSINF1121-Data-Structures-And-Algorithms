import java.util.EmptyStackException;

public class MyStack<E> implements Stack<E> {

    private Node<E> top;
    private int size;

    public MyStack() {
        top = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean empty() {
        return size == 0;
    }

    public E push(E element) {
        Node<E> newNode = new Node<E>(element, top);
        top = newNode;
        size++;
        return element;
    }

    public E peek() throws EmptyStackException {
        if(empty()) throw new EmptyStackException();
        return top.getElement();
    }

    public E pop() throws EmptyStackException {
        if(empty()) throw new EmptyStackException();
        E tmp = top.getElement();
        top = top.getNext();
        size--;
        return tmp;
    }

    public String toString()
    {
        String toString = "";
        Node<E> tmp = top;
        while (tmp != null)
        {
            if (!toString.equals("")) toString += " ";
            toString += tmp.toString();
            tmp = tmp.getNext();
        }
        return toString;
    }

    class Node<E> {

        private E element;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public E getElement() {
            return this.element;
        }

        public Node<E> getNext() {
            return this.next;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }

        public String toString()
        {
            return element.toString();
        }
    }
}
