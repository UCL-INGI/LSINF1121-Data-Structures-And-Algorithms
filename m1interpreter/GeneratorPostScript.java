import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Random generation of a valid derivation
 */
public class GeneratorPostScript {
	
	public final static String PSTACK = "pstack";
	public final static String ADD = "add"; 
	public final static String SUB = "sub"; 
	public final static String MUL = "mul"; 
	public final static String DIV = "div";
	public final static String DUP = "dup"; 
	public final static String EXCH = "exch"; 
	public final static String EQ = "eq"; 
	public final static String NE = "ne"; 
	public final static String DEF = "def"; 
	public final static String POP = "pop"; 
	
	private int seed;
	private Random rand = new Random(seed);
	
	public GeneratorPostScript(int seed) {
		this.seed = seed;
		this.rand = new Random(seed); 
	}
	
	/* Grade the student in the method main */
	public static void main(String[] args) {

		GeneratorPostScript g = new GeneratorPostScript(77); 
		String s = g.generate(30); 
		System.out.println(s); 

		/*Generator g = new Generator(42);
		ScriptEngineManager manager = new ScriptEngineManager();
    		ScriptEngine engine = manager.getEngineByName("JavaScript");  

		int totPos = 100; 
		int okPos = 0; 
		int totNeg = 10; 
		int okNeg = 0; 
		int bug = 0; // unwanted exceptions with my own code, should not happen
		int studentError = 0; // unwanted exceptions with the code of the student, means he failed

		for (int i = 0 ; i < totPos ; i++) {
			String[] res = g.generate(3); 
			String expression = ""; 
			for (int j = 0 ; j < res.length ; j++) {
				expression += res[j]; 
			}
			//System.out.println("Expression : " + expression); 

			// Evaluate the correct result with my own code
			MyExpressionTree myTree;
			String myResult = ""; 
			try {
				myTree = new MyExpressionTree(expression);
				MyExpressionTree myDerivedTree = myTree.derive();
				String myExpr = myDerivedTree.toString(); 
				try {
	    				Object result = engine.eval(myExpr.replace('x', '7').replace("sin", "Math.sin").replace("cos", "Math.cos").replace("--", "+"));
					//System.out.println("ME : Evaluation of the derivative (x=7): " + result.toString()); 
					myResult = result.toString(); 
				} catch (ScriptException e) {
					bug++; 
					//System.out.println("ME : ScriptException"); 
				}
			} catch (MyParseException e) {
				bug++; 
				//System.out.println("ME : MyParseException... " + e + "\n");
			}

			// Evaluate the result of the student
			ExpressionTree tree;
			String studentResult = ""; 
			try {
				tree = new ExpressionTree(expression);
				ExpressionTree derivedTree = tree.derive();
				String expr = derivedTree.toString(); 
				try {
	    				Object result = engine.eval(expr.replace('x', '7').replace("sin", "Math.sin").replace("cos", "Math.cos").replace("--", "+"));
					//System.out.println("STUDENT : Evaluation of the derivative (x=7): " + result.toString()); 
					studentResult = result.toString(); 
				} catch (ScriptException e) {
					studentError++; 
					//System.out.println("STUDENT : ScriptException"); 
				}
			} catch (Exception e) { // should be something like 'ParseException'
				studentError++; 
				//System.out.println("STUDENT : ParseException... " + e + "\n");
			}
			if (myResult.equals(studentResult))
				okPos++; 
		}

		// Check if the student effectively throws an Exception upon wrong syntax
		for (int i = 0 ; i < totNeg ; i++) {
			String[] res = g.generateWrong(3); 
			String expression = ""; 
			for (int j = 0 ; j < res.length ; j++) {
				expression += res[j]; 
			}
			//System.out.println("Expression : " + expression); 

			ExpressionTree tree;
			try {
				tree = new ExpressionTree(expression);
			} catch (Exception e) {
				okNeg++; 
				//System.out.println("ParseException correctly thrown");
			}
		}

		// Compute the result of the student
		if (bug != 0)
			System.out.println("PROBLEM"); 
		else if (okPos == totPos && okNeg == totNeg && studentError == 0)
			System.out.println("OK " + okPos + " " + okNeg); 
		else
			System.out.println("KO " + totPos + " " + okPos + " " + totNeg + " " + okNeg + " " + studentError); */

	}
	
	public  String generate(int maxDepth) {
		List<String> res = new LinkedList<String>();
		generateOp(res, maxDepth);
		String s = ""; 
		for (int i = 0 ; i < res.size() ; i++) {
			if (i != 0)
				s += " "; 
			s += res.get(i); 
		}
		return s; 
	}

	public void generateOp(List<String> output, int maxDepth) {

		LinkedList<String> state = new LinkedList<String>(); 

		for (int k = 0 ; k < maxDepth ; k++) { // TODO def
			int i = rand.nextInt(11);
			switch(i) {
				case 0 : {
					generateNum(output); 
					state.add("num"); 
					break;
				} 
				case 1 : {
					generateBool(output); 
					state.add("bool"); 
					break;
				}
				case 2 : {
					if (state.size() >= 2 && state.get(state.size()-1).equals("num") && state.get(state.size()-2).equals("num")) {
						output.add(ADD); 
						state.removeLast(); // one less numeric in the stack
					}
					break;
				}
				case 3 : {
					if (state.size() >= 2 && state.get(state.size()-1).equals("num") && state.get(state.size()-2).equals("num")) {
						output.add(SUB); 
						state.removeLast(); // one less numeric in the stack
					}
					break;
				}
				case 4 : {
					if (state.size() >= 2 && state.get(state.size()-1).equals("num") && state.get(state.size()-2).equals("num")) {
						output.add(MUL); 
						state.removeLast(); // one less numeric in the stack
					}
					break;
				}
				case 5 : {
					if (state.size() >= 2 && state.get(state.size()-1).equals("num") && state.get(state.size()-2).equals("num")) {
						output.add(DIV); 
						state.removeLast(); // one less numeric in the stack
					}
					break;
				}
				case 6 : {
					if (state.size() > 0) {
						output.add(DUP); 
						state.add(state.getLast());
					}
					break;
				}
				case 7 : {
					if (state.size() >= 2) {
						output.add(EXCH); 
						String temp = state.remove(state.size()-2); 
						state.add(temp);
					}
					break;
				}
				case 8 : {
					if (state.size() >= 2 && state.get(state.size()-1).equals("bool") && state.get(state.size()-2).equals("bool")) {
						output.add(EQ); 
						state.removeLast();
					} else if (state.size() >= 2 && state.get(state.size()-1).equals("num") && state.get(state.size()-2).equals("num")) {
						output.add(EQ); 
						state.removeLast(); 
						state.removeLast(); 
						state.add("bool"); 
					}
					break;
				}
				case 9 : {
					if (state.size() >= 2 && state.get(state.size()-1).equals("bool") && state.get(state.size()-2).equals("bool")) {
						output.add(EQ); 
						state.removeLast();
					} else if (state.size() >= 2 && state.get(state.size()-1).equals("num") && state.get(state.size()-2).equals("num")) {
						output.add(EQ); 
						state.removeLast(); 
						state.removeLast(); 
						state.add("bool"); 
					}
					break;
				}
				case 10 : {
					if (state.size() > 0) {
						output.add(POP); 
						state.removeLast();
					}
					break;
				}
			}
		}
		output.add(PSTACK); 
	}
		
	public void generateNum(List<String> output) {
		int num = rand.nextInt(Integer.MAX_VALUE); 
		output.add(Integer.toString(num));
	}

	public void generateBool(List<String> output) {
		int i = rand.nextInt(2);
		switch(i) {
			case 0 : {
				output.add("true"); 
				break;
			} 
			case 1 : {
				output.add("false"); 
				break;
			}
		}
	}
	
}