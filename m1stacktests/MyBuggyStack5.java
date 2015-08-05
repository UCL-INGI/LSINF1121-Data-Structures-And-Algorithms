import java.util.Stack; 
import java.util.EmptyStackException;

public class MyBuggyStack5<E> {

	private Stack<E> myStack; 
	
	public MyBuggyStack5() {
		myStack = new Stack<E>(); 
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
	
	public E push(E item) {
		return myStack.push(item); 
	}
	
}