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
        if (size >= 3) {
        	E temp = myStack.pop(); 
            E toReturn = myStack.pop(); 
            myStack.push(temp); 
            return toReturn; 
        }
		return myStack.pop(); 
	}
	
	public void push(E item) {
		size++; 
		myStack.push(item); 
	}
	
}
