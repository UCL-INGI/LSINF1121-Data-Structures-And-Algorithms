import java.util.Stack; 

public class MyBuggyStack9<E> {

	private Stack<E> myStack; 
	private int size; 
	
	public MyBuggyStack9() {
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
		size++; 
        if (size >= 2) {
        	
        }
		return myStack.push(item); 
	}
	
}
