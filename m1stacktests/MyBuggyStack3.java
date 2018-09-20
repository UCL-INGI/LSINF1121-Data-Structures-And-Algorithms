public class MyStack<E> implements Stack<E>  {

	private java.util.Stack<E> myStack; 
	private int size; 
	
	public MyStack() {
		myStack = new java.util.Stack<E>(); 
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
	
	public void push(E item) {
		size++; 
		return myStack.push(item); 
	}
	
}
