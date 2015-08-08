import java.util.ArrayList;
import java.util.Hashtable;
import java.util.EmptyStackException;
import java.lang.ArithmeticException;
import java.util.Arrays;


/**
 * Buggy version of the solution of Simon Hardy for the PostScript Interpreter (mission 1, test of interpreter tests). 
 */
public class Interpreter {
	public Hashtable<String,Element> variables;
	private MyStack<Element> stack;
	
	/* Main method, useless for the grading */
	public static void main(String[] args) {
		/*Interpreter interpreter = new Interpreter(); 
		//System.out.println(interpreter.interpret("true pstack pop")); 
		System.out.println(interpreter.interpret("1 pop 969067502 592164476 995688456 eq 2143572209 pop 1 pop dup exch 726510756 true pop pop 1261490713 pstack")); */
	}
	
	public Interpreter() {
		stack = new MyStack<Element>();
		variables = new Hashtable<String,Element>();
	}

	public String interpret(String instruction) {
		ArrayList<String> instructions = new ArrayList<String>(Arrays.asList(instruction.split(" ")));
		ArrayList<String> results = new ArrayList<String>(); 
		for(int l = 0; l < instructions.size() ; l++) {
			Element e = new Element(instructions.get(l));
			Object o = e.interpret(stack, results, variables);
				
				if (o == null)
				{}
				else if (o instanceof Element)
					stack.push((Element)o);
				else if(o instanceof Integer)
					stack.push(new Element(""+(Integer)o));
				else if(o instanceof Double)
					stack.push(new Element(""+(Double)o));
				else if(o instanceof String)
					stack.push(new Element(""+o));
				else if (o instanceof Boolean)
					stack.push(new Element(""+o));
				
		}
		String output = ""; 
		int i = 0; 
		for (String s : results) {
			if (i != 0) output += " "; 
			output += s; 
			i++; 
		}
		return output;
	}

	public static void pstack(MyStack<Element> stack, ArrayList<String> results, Hashtable<String,Element> variables) {
		String s = stack.toString();
		results.add(s); 
	}
	
	public static void add(MyStack<Element> stack, Hashtable<String,Element> variables) {
		Object o1 = null;
		Object o2 = null;
		try{
			o1 = stack.pop().interpret(stack, null, variables);
			o2 = stack.pop().interpret(stack, null, variables);
		}catch(EmptyStackException e)
		{
			if(o1 == null)
				return;
			if (o2 == null)
				o2 = 0;
		}
		if (o1 instanceof Integer)
		{
			if (o2 instanceof Integer)
			{
				int add = (Integer)o1 + (Integer)o2;
				stack.push(new Element(""+add));
			}
			else
			{
				double add = (Integer)o1 + (Double)o2;
				stack.push(new Element(""+add));
			}
		}
		else if (o1 instanceof Double)
		{
			if (o2 instanceof Integer)
			{
				double add = (Double)o1 + (Integer)o2;
				stack.push(new Element(""+add));
			}
			else
			{
				double add = (Double)o1 + (Double)o2;
				stack.push(new Element(""+add));
			}
		}
	}
	
	public static void sub(MyStack<Element> stack, Hashtable<String,Element> variables) {
		Object o1 = null;
		Object o2 = null;
		try{
			o1 = stack.pop().interpret(stack, null, variables);
			o2 = stack.pop().interpret(stack, null, variables);
		}catch(EmptyStackException e)
		{
			if(o1 == null)
				return;
			if (o2 == null)
				o2 = 0;
		}
		if (o1 instanceof Integer)
		{
			if (o2 instanceof Integer)
			{
				int add =  (Integer)o2 - (Integer)o1;
				stack.push(new Element(""+add));
			}
			else
			{
				double add = (Double)o2 - (Integer)o1;
				stack.push(new Element(""+add));
			}
		}
		else if (o1 instanceof Double)
		{
			if (o2 instanceof Integer)
			{
				double add = (Integer)o2 - (Double)o1;
				stack.push(new Element(""+add));
			}
			else
			{
				double add = (Double)o2 - (Double)o1;
				stack.push(new Element(""+add));
			}
		}
	}
	
	public static void mul(MyStack<Element> stack, Hashtable<String,Element> variables) {
		Object o1 = null;
		Object o2 = null;
		try{
			o1 = stack.pop().interpret(stack, null, variables);
			o2 = stack.pop().interpret(stack, null, variables);
		}catch(EmptyStackException e)
		{
			if(o1 == null)
				return;
			if (o2 == null)
				o2 = 1;
		}
		if (o1 instanceof Integer)
		{
			if (o2 instanceof Integer)
			{
				int add = (Integer)o1 * (Integer)o2;
				stack.push(new Element(""+add));
			}
			else
			{
				double add = (Integer)o1 * (Double)o2;
				stack.push(new Element(""+add));
			}
		}
		else if (o1 instanceof Double)
		{
			if (o2 instanceof Integer)
			{
				double add = (Double)o1 * (Integer)o2;
				stack.push(new Element(""+add));
			}
			else
			{
				double add = (Double)o1 * (Double)o2;
				stack.push(new Element(""+add));
			}
		}
	}
	
	public static void div(MyStack<Element> stack, Hashtable<String,Element> variables) throws ArithmeticException {
		Object o1 = null;
		Object o2 = null;
		try{
			o1 = stack.pop().interpret(stack, null, variables);
			o2 = stack.pop().interpret(stack, null, variables);
		}catch(EmptyStackException e)
		{
			if(o1 == null)
				return;
			if (o2 == null)
				o2 = 0 ;
		}
		if (Double.valueOf(""+o1) == 0) {
			throw new ArithmeticException();
		}
		else {
			double div = Double.valueOf(""+o2) / Double.valueOf(""+o1);
			stack.push(new Element(""+div));
		}
		
	}
	
	public static void dup(MyStack<Element> stack, Hashtable<String,Element> variables) {
		Element e;
		try{
			 e = stack.pop();
		}catch(EmptyStackException ex)
		{
			return;
		}
		stack.push(e);
		stack.push(e);
	}
	
	public static void exch(MyStack<Element> stack, Hashtable<String,Element> variables) {
		Object o1 = null;
		Object o2 = null;
		try{
			o1 = stack.pop().interpret(stack, null, variables);
			o2 = stack.pop().interpret(stack, null, variables);
			
		}catch(EmptyStackException e)
		{
			if (o1 != null)
				stack.push(new Element(""+o1));
			return;
		}
		Element e1 = new Element(""+o1);
		Element e2 = new Element(""+o2);
		stack.push(e1);
		stack.push(e2);
	}
	
	public static void eq(MyStack<Element> stack, Hashtable<String,Element> variables) {
		try{
			String e1 = ""+stack.pop().interpret(stack, null, variables);
			String e2 = ""+stack.pop().interpret(stack, null, variables);
			stack.push(new Element(""+e1.equals(e2)));
		}catch(EmptyStackException e)
		{
			stack.push(new Element(""+false));
		}
	}
	
	public static void ne(MyStack<Element> stack, Hashtable<String,Element> variables) {
		try{
			String e1 = ""+stack.pop().interpret(stack, null, variables);
			String e2 = ""+stack.pop().interpret(stack, null, variables);
			stack.push(new Element(""+ !e1.equals(e2)));
		}catch(EmptyStackException e)
		{
			stack.push(new Element(""+true));
		}
	}
	
	public static void def(MyStack<Element> stack, Hashtable<String,Element> variables) {
		try{
			Object o1 = stack.pop().interpret(stack, null, variables);
			String o2 = (String) stack.pop().interpret(stack, null, variables);
			o2 = (o2.substring(1));
			variables.put(o2, new Element(""+o1));
			
		}catch(EmptyStackException e)
		{
			stack.push(new Element(""+true));
		}
	}
	
	/* BUGGY METHOD POP */
	public static void pop(MyStack<Element> stack, Hashtable<String,Element> variables) throws EmptyStackException {
		if (stack.size() < 3)
			stack.pop(); 
	}

}

class MyStack<E> { 
	
	private Node<E> top;
	private int size;
	
	public MyStack() {
		top = null;
		size = 0;
	}
	
	public int size() {
		return size;
	}
	
	public boolean isEmpty() {
		return size == 0;
	}

	public void push(E element) {
		Node<E> newNode = new Node<E>(element, top);
		top = newNode;
		size++;
	}
	
	public E top() throws EmptyStackException {
		if(isEmpty()) throw new EmptyStackException();
		return top.getElement();
	}

	public E pop() throws EmptyStackException {
		if(isEmpty()) throw new EmptyStackException();
		E tmp = top.getElement();
		top = top.getNext();
		size--;
		return tmp;
	}
	
	public String toString()
	{
		String toString = "";
		Node<E> tmp = top;
		while (tmp != null)
		{
			if (!toString.equals("")) toString += " "; 
			toString += tmp.toString(); 
			tmp = tmp.getNext();
		}
		return toString;
	}

class Node<E> {

	private E element;
	private Node<E> next;
	
	public Node(E element, Node<E> next) {
		this.element = element;
		this.next = next;
	}
	
	public E getElement() {
		return this.element;
	}
	
	public Node<E> getNext() {
		return this.next;
	}
	
	public void setElement(E element) {
		this.element = element;
	}
	
	public void setNext(Node<E> next) {
		this.next = next;
	}
	
	public String toString()
	{
		return element.toString();
	}
}

}

class Element {

	private static String TYPE_OPERATOR = "Operator";
	private static String TYPE_INT = "Int";
	private static String TYPE_DOUBLE = "Double";
	private static String TYPE_BOOLEAN = "Boolean";
	private static String TYPE_VARIABLE = "Variable";
	
	private String type;
	private String element;
	private double double_value;
	private int int_value;
	private boolean bool_value; 
	
	public Element(String elem)
	{
		element = elem;
		if(elem.contains("/"))
		{
			type = TYPE_VARIABLE;
		}
		else if (elem.contains("."))
		{
			type = TYPE_DOUBLE;
			double_value = Double.parseDouble(elem);
		}
		else if (elem.contains("1")||elem.contains("2")||elem.contains("3")||elem.contains("4")||elem.contains("5")
				||elem.contains("6")||elem.contains("7")||elem.contains("8")||elem.contains("9")||elem.contains("0"))
		{
			type = TYPE_INT;
			int_value = Integer.parseInt(elem);
		}
		else if (elem.contains("pstack"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("add"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("sub"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("mul"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("div"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("dup"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("exch"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("eq"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("ne"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("def"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("pop"))
		{
			type = TYPE_OPERATOR;
		}
		else if (elem.contains("true"))
		{
			type = TYPE_BOOLEAN;
			bool_value = true; 
		}
		else if (elem.contains("false"))
		{
			type = TYPE_BOOLEAN;
			bool_value = false; 
		}
		else type = TYPE_VARIABLE;
	}
	
	public Object interpret(MyStack<Element> stack, ArrayList<String> results, Hashtable<String,Element> variables)
	{
		if (type == TYPE_OPERATOR)
		{
			if (element.contains("pstack"))
			{
				Interpreter.pstack(stack, results, variables);
			}
			else if (element.contains("add"))
			{
				Interpreter.add(stack, variables);
			}
			else if (element.contains("sub"))
			{
				Interpreter.sub(stack, variables);
			}
			else if (element.contains("mul"))
			{
				Interpreter.mul(stack, variables);
			}
			else if (element.contains("div"))
			{
				Interpreter.div(stack, variables);
			}
			else if (element.contains("dup"))
			{
				Interpreter.dup(stack, variables);
			}
			else if (element.contains("exch"))
			{
				Interpreter.exch(stack, variables);
			}
			else if (element.contains("eq"))
			{
				Interpreter.eq(stack, variables);
			}
			else if (element.contains("ne"))
			{
				Interpreter.ne(stack, variables);
			}
			else if (element.contains("def"))
			{
				Interpreter.def(stack, variables);
			}
			else if (element.contains("pop"))
			{
				Interpreter.pop(stack, variables);
			}
			return null;
		}
		else if (type == TYPE_INT)
		{
			return int_value;
		}
		else if (type == TYPE_DOUBLE)
		{
			return double_value;
		}
		else if (type == TYPE_BOOLEAN)
			return bool_value; 
		else if (type == TYPE_VARIABLE && element.contains("/"))
			return element;
		else if (type == TYPE_VARIABLE && !element.contains("/"))
		{
			Element e = variables.get(element);
			return e.interpret(stack, null, variables);
		}
		else return null;
	}

	public String toString()
	{
		return element;
	}
}
