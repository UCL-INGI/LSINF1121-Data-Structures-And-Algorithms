import java.util.ArrayList;
import java.util.Hashtable;
import java.util.EmptyStackException;
import java.util.Arrays;


/**
 * Classe principale : interpréteur PostScript
 * @author groupe 4
 */
public class Interpreter {
	
	public Hashtable<String,Element> variables;
	private MyStack<Element> stack;
	
	/**
	 * Méthode principale
	 * @param args
	 *
	public static void main(String[] args) {
		Interpreter interpreter = new Interpreter(); 
		System.out.println(interpreter.interpret("1 1 add pstack")); 
	}*/
	
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
	
	public static void div(MyStack<Element> stack, Hashtable<String,Element> variables) {
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
		double div = Double.valueOf(""+o2) / Double.valueOf(""+o1);
				stack.push(new Element(""+div));
		
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
		stack.push(e2);
		stack.push(e1);
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
	
	public static void pop(MyStack<Element> stack, Hashtable<String,Element> variables) {
		try{
			stack.pop();
		}catch(EmptyStackException e)
		{
			e.printStackTrace();
		}
	}

}
