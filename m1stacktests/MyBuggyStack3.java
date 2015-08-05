import java.util.Stack; 

public class MyBuggyStack3<E> {

	private Stack<E> myStack; 
	private int size; 
	
	public MyBuggyStack3() {
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
		return myStack.pop(); 
	}
	
	public E push(E item) {
		if (size >= 10) return myStack.peek(); // doesn't push the element + return another one
		size++; 
		return myStack.push(item); 
	}
	
}
