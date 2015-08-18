public class MyBuggyStack2<E> implements Stack<E> {

	private java.util.Stack<E> myStack; 
	private int nbrOp; 	

	public MyBuggyStack2() {
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
	
	public E push(E item) {
		nbrOp++; 
		if (nbrOp > 10) myStack.push(item); 
		return myStack.push(item); 
	}
	
}
