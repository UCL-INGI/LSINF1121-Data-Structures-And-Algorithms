public class MyStack<E> implements Stack<E> {

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
		return myStack.pop(); 
	}
	
	public void push(E item) { // add the element at the end instead of the top of the stack
    	java.util.Stack<E> tempStack = new java.util.Stack<E>(); 
    	for (int i = 0 ; i < size ; i++) {
        	tempStack.push(myStack.pop()); 
        }
        myStack.push(item); 
    	for (int i = 0 ; i < size ; i++) {
        	myStack.push(tempStack.pop());
        }
		size++; 
		
	}
	
}
