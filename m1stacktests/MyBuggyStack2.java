import java.util.Stack; 

public class MyBuggyStack2<E> {

	private Stack<E> myStack; 
	private int nbrOp; 	

	public MyBuggyStack2() {
		myStack = new Stack<E>(); 
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
