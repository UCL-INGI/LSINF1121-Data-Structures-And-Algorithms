import java.util.EmptyStackException;

public class MyStack<E>  implements Stack<E> {

	private java.util.Stack<E> myStack; 
	
	public MyStack() {
		myStack = new java.util.Stack<E>(); 
	}
	
	public boolean empty() {
		return myStack.empty();
	}
	
	public E peek() {
		return myStack.peek();
	}

	public E pop() {
		try {
			return myStack.pop(); 
		} catch (EmptyStackException e) {
			return null; // FALSE : should throw the exception !
		}
	}
	
	public void push(E item) {
		myStack.push(item); 
	}
	
}
