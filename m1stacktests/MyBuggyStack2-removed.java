public class MyStack<E> implements Stack<E> {

	private java.util.Stack<E> myStack; 
	private int nbrOp; 	

	public MyStack() {
		myStack = new java.util.Stack<E>(); 
		nbrOp = 0; 
	}
	
	public boolean empty() {
		return myStack.empty();
	}
	
	public E peek() {
		return myStack.peek();
	}

	public E pop() {
		nbrOp++; 
		return myStack.pop(); 
	}
	
	public void push(E item) {
		nbrOp++; 
		if (nbrOp > 10) myStack.push(item); 
		return myStack.push(item); 
	}
	
}
