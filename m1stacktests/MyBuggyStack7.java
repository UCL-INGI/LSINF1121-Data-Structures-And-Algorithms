
public class MyBuggyStack7<E>   implements Stack<E> {

	private java.util.Stack<E> myStack; 
	
	public MyBuggyStack7() {
		myStack = new java.util.Stack<E>(); 
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
		return myStack.push(null); // pushes null instead of the right item
	}
	
}
