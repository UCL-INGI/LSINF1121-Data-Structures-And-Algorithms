import java.util.ArrayList; 
import java.util.Hashtable; 

public class Element {

	private static String TYPE_OPERATOR = "Operator";
	private static String TYPE_INT = "Int";
	private static String TYPE_DOUBLE = "Double";
	private static String TYPE_BOOLEAN = "Boolean";
	private static String TYPE_VARIABLE = "Variable";
	
	private String type;
	private String element;
	private double double_value;
	private int int_value;
	
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
		}
		else if (elem.contains("false"))
		{
			type = TYPE_BOOLEAN;
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
			return null;
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
