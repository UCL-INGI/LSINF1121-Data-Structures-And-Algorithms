
public class MyBuggyStack9<E>   implements Stack<E> {

	private Stack<E> java.util.myStack; 
	private int size; 
	
	public MyBuggyStack9() {
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
		return myStack.pop(); 
	}
	
	public E push(E item) { // add the element at the end instead of the top of the stack
    	Stack<E> tempStack = new Stack<E>(); 
    	for (int i = 0 ; i < size ; i++) {
        	tempStack.push(myStack.pop()); 
        }
        E toRet = myStack.push(item); 
    	for (int i = 0 ; i < size ; i++) {
        	myStack.push(tempStack.pop());
        }
		size++; 
		return toRet; 
	}
	
}
