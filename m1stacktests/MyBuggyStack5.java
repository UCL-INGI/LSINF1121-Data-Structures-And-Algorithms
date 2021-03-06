public class MyStack<E> implements Stack<E> {

	private java.util.Stack<E> myStack; 
	
	public MyStack() {
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
	
	public void push(E item) {
		myStack.push(item); 
	}
	
}
