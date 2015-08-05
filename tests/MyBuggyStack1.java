import java.util.Stack; 

public class MyBuggyStack1<E> {

	private Stack<E> myStack; 
	
	public MyBuggyStack1() {
		myStack = new Stack<E>(); 
	}
	
	public boolean empty() {
		return !myStack.empty();
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
