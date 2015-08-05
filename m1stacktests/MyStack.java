import java.util.Stack; 

public class MyStack<E> {

	private Stack<E> myStack; 
	
	public MyStack() {
		myStack = new Stack<E>(); 
	}
	
	public boolean empty() {
		return myStack.empty();
	}
	
	public E peek() {
		return myStack.peek();
	}

	public E pop() {
		return myStack.pop(); 
	}
	
	public E push(E item) {
		return myStack.push(item); 
	}
	
}
