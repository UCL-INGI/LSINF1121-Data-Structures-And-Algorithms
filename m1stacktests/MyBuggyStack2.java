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
		return myStack.pop(); 
	}
	
	public void push(E item) {
        // doesn't push the element 
		if (size < 5){
            size++; 
            myStack.push(item); 
        }
	}
	
}
