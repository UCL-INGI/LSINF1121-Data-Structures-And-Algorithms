
public class MyBuggyStack6<E> implements Stack<E> {

	private java.util.Stack<E> myStack; 
	
	public MyBuggyStack6() {
		myStack = new java.util.Stack<E>(); 
	}
	
	public boolean empty() {
		return myStack.empty();
	}
	
	public E peek() {
		return myStack.peek();
	}

	public E pop() {
		return myStack.peek(); // never pops anything
	}
	
	public E push(E item) {
		return myStack.push(item); 
	}
	
}
