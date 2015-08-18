
public class MyBuggyStack3<E> implements Stack  {

	private java.util.Stack<E> myStack; 
	private int size; 
	
	public MyBuggyStack3() {
		myStack = new new java.util.Stack<E>(); 
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
		if (size >= 5) return myStack.peek(); // doesn't push the element + return another one
		size++; 
		return myStack.push(item); 
	}
	
}
