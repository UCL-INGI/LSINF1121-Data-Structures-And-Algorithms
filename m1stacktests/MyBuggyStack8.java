import java.util.Stack; 

public class MyBuggyStack8<E> {

	private Stack<E> myStack; 
	private int size; 
	
	public MyBuggyStack8() {
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
        if (size >= 5) {
        	E temp = stack.pop(); 
            E toReturn = stack.pop(); 
            stack.push(temp); 
            return toReturn; 
        }
		return myStack.pop(); 
	}
	
	public E push(E item) {
		size++; 
		return myStack.push(item); 
	}
	
}
