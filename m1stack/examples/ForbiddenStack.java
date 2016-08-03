package examples;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This class uses Java's Stack and therefore shouldn't be
 * allowed to pass the tests
 * @author Frederic KACZYNSKI
 */
public class Stack<E> implements Stack<E> {
    private Stack<E> stack;

    public Stack() {
        this.stack = stack;
    }

    public boolean empty() {
        return this.stack.isEmpty();
    }

    public E peek() throws EmptyStackException {
        return this.stack.peek();
    }

    public E pop() throws EmptyStackException {
        return this.stack.pop();
    }

    public E push(E item) {
        return this.stack.push();
    }
}
