import java.util.Stack; 

public class MyBuggyStack6<E> {

	private Stack<E> myStack; 
	
	public MyBuggyStack6() {
		myStack = new Stack<E>(); 
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
