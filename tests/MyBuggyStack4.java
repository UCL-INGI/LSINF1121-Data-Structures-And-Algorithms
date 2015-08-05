import java.util.Stack; 

public class MyBuggyStack4<E> {

	private Stack<E> myStack; 
	private int size; 
	
	public MyBuggyStack4() {
		myStack = new Stack<E>(); 
		size = 0; 
	}
	
	public boolean empty() {
		return myStack.empty();
	}
	
	public E peek() {
		return myStack.peek();
	}

	public E pop() {
		size--; 
		if (size >= 5) return myStack.peek(); // returns the right element, but doesn't pop it !
		return myStack.pop(); 
	}
	
	public E push(E item) {
		size++; 
		return myStack.push(item); 
	}
	
}
