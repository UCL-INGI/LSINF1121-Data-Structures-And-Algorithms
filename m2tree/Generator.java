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
public class Generator {

	/**
	 * Grammar
	 * E -> (E + T)
	 * E -> (E - T)
	 * E -> T
	 * T -> (T * F)
	 * T -> (T / F)
	 * T -> F
	 * F -> (F ^ G)
	 * F -> G
	 * G -> sin ( H )
	 * G -> cos ( H )
	 * G -> H
	 * H -> E
	 * H -> id
	 * id -> "x" | "0" | ("1"-"9") {"0"-"9"}
	 */
	
	public final static String PLUS = "+";
	public final static String MINUS = "-";
	public final static String TIMES = "*";
	public final static String DIVIDE = "/";
	public final static String LEFTPAR = "(";
	public final static String RIGHTPAR = ")";
	public final static String POWER = "^"; 
	public final static String SIN = "sin"; 
	public final static String COS = "cos"; 
	
	private int seed;
	private Random rand = new Random(seed);
	
	public Generator(int seed) {
		this.seed = seed;
	}
	
	/* Grade the student in the method main */
	public static void main(String[] args) {

		// Start with basic checks
		int modifyThis = 0; // checks if derive() modifies the current tree
		int emptyConstructor = 0; // checks if constructor bewares well with no argument, "", "0". 
		
		try {
			ExpressionTree tree1 = new ExpressionTree("(x+2)");
			ExpressionTree der1 = tree1.derive(); 
			if (!tree1.toString().equals("(x+2)"))
				modifyThis++; 
		} catch (Exception e) {
			modifyThis++; 
		}
		
		try {
			ExpressionTree tree2 = new ExpressionTree(); 
			ExpressionTree tree3 = new ExpressionTree(""); 
			ExpressionTree tree4 = new ExpressionTree("0"); 
			if (!tree2.toString().equals("0") || !tree3.toString().equals("0") || !tree2.toString().equals("0"))
				emptyConstructor++; 
		} catch (Exception e) {
			emptyConstructor++;
		}
		

		// Advanced tests now
		String oneWrong = "nothing"; // will contain the expression corresponding to the first failed problem
		Generator g = new Generator(42);
		ScriptEngineManager manager = new ScriptEngineManager();
    		ScriptEngine engine = manager.getEngineByName("JavaScript");  

		String [] firstBarrier = {
			"10",
			"(10+x)",
			"sin(x)",
			"((10*2)+(4-x))",
			"(10*(x+sin(x)))",
			"(x+cos((x+2)))",
			"((x^3)/5)",

			"(x/12)",
			"cos(x)",
			"((10/x)+(2*x))",
			"(x*(1-sin(x)))",
			"((x*(sin(((x/10)+x))^3))-cos(x))",
			"(10^(x+20))"
		};

		String [] wrongFirstBarrier = {
			"-4",
			"(y*cos(x))",
			"(x^4+sin((x+(2/x))))",
			"((x^(-1))/6)",
			"(x+)3+x))"
		};

		int totPos = 100;
		int okPos = 0; 
		int totNeg = 20; 
		int okNeg = 0; 
		int bug = 0; // unwanted exceptions with my own code, should not happen
		int studentError = 0; // unwanted exceptions with the code of the student, means he failed

		for (int i = 0 ; i < totPos ; i++) {
			String expression = ""; 
			if (i < 13)
				expression = firstBarrier[i]; 
			else {
				String[] res = g.generate(15);
				for (int j = 0 ; j < res.length ; j++) {
					expression += res[j]; 
				}
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
					if (oneWrong.equals("nothing")) oneWrong = expression; 
					//System.out.println("STUDENT : ScriptException"); 
				}
			} catch (Exception e) { // should be something like 'ParseException'
				studentError++; 
				if (oneWrong.equals("nothing")) oneWrong = expression; 
				//System.out.println("STUDENT : ParseException... " + e + "\n");
			}
			if (myResult.equals(studentResult))
				okPos++; 
			else
				if (oneWrong.equals("nothing")) oneWrong = expression; 
		}

		// Check if the student effectively throws an Exception upon wrong syntax
		for (int i = 0 ; i < totNeg ; i++) {
			String expression = ""; 
			if (i < 5)
				expression = wrongFirstBarrier[i]; 
			else {
				String[] res = g.generateWrong(15); 
				for (int j = 0 ; j < res.length ; j++) {
					expression += res[j]; 
				}
			}
			//System.out.println("Expression : " + expression); 

			ExpressionTree tree;
			try {
				tree = new ExpressionTree(expression);
				if (oneWrong.equals("nothing")) oneWrong = expression; 
			} catch (Exception e) {
				okNeg++; 
				//System.out.println("ParseException correctly thrown");
			}
		}

		// Compute the result of the student
		if (bug != 0)
			System.out.println("PROBLEM"); 
		else if (okPos == totPos && okNeg == totNeg && studentError == 0 && modifyThis == 0 && emptyConstructor == 0)
			System.out.println("OK " + okPos + " " + okNeg); 
		else
			System.out.println("KO " + totPos + " " + okPos + " " + totNeg + " " + okNeg + " " + studentError + " " + modifyThis + " " + emptyConstructor + " " + oneWrong); 

	}
	
	public  String[] generate(int maxDepth) {
		List<String> res = new LinkedList<String>();
		generateE(res, maxDepth, 0);
		return res.toArray((new String[res.size()]));
	}
	
	public  String[] generateWrong(int maxDepth) { // make the syntax incorrect so that the student has to throw a ParseException
		List<String> res = new LinkedList<String>();
		generateE(res, maxDepth, 0);
		if (res.size() == 0) // if empty, add a wrong element
			res.add("lol");
		else if (res.size() == 1) // if size of 1, replace it with a wrong element
			res.set(0, "coos");
		else {
			int i = rand.nextInt(7);
			switch(i) { // make a random change so that syntax becomes incorrect
				case 0 : {
					res.add("(");
					break;
				}
				case 1 : {
					res.add(Math.max(2, res.size()), "(");
					break;
				}
				case 2 : {
					res.add(Math.max(3, res.size()), "sinn"); 
					break;
				}
				case 3 : {
					res.add(1, "tan"); 
					break;
				}
				case 4 : {
					res.remove(0); 
					break;
				}
				case 5 : {
					res.set(Math.max(4, res.size()-1), "^^");
					break;
				}

				case 6 : {
					res.add(Math.max(2, res.size()), "(");
					res.add(Math.max(3, res.size()), ")");
					break;
				}
			}
		}
		return res.toArray((new String[res.size()]));
	}

	public void generateE(List<String> output, int maxDepth, int currentDepth) {
		// E -> T | E + T | E - T
		if(currentDepth > maxDepth) { //prevent from exploding memory
			generateT(output, maxDepth, currentDepth + 1);
		}
		else {
			
			int i = rand.nextInt(3);
			switch(i) {
				case 0 : {
					output.add(LEFTPAR); 
					generateE(output, maxDepth, currentDepth + 1);
					output.add(PLUS);
					generateT(output, maxDepth, currentDepth + 1);
					output.add(RIGHTPAR); 
					break;
				} 
				case 1 : {
					output.add(LEFTPAR); 
					generateE(output, maxDepth, currentDepth + 1);
					output.add(MINUS);
					generateT(output, maxDepth, currentDepth + 1);
					output.add(RIGHTPAR); 
					break;
				}
				case 2 : {
					generateT(output, maxDepth, currentDepth + 1);
					break;
				}
			}
		}
		
	}
	
	public void generateT(List<String> output, int maxDepth, int currentDepth) {
		//T -> F | T * F | T / F
		if(currentDepth > maxDepth) { //prevent from exploding memory
			generateF(output, maxDepth, currentDepth + 1);
		}
		else {
			
			int i = rand.nextInt(3);
			switch(i) {
				case 0: {
					output.add(LEFTPAR); 
					generateT(output, maxDepth, currentDepth + 1);
					output.add(TIMES);
					generateF(output, maxDepth, currentDepth + 1);
					output.add(RIGHTPAR); 
					break;
				} 
				case 1: {
					output.add(LEFTPAR); 
					generateT(output, maxDepth, currentDepth + 1);
					output.add(DIVIDE);
					generateF(output, maxDepth, currentDepth + 1);
					output.add(RIGHTPAR); 
					break;
				}
				case 2: {
					generateF(output, maxDepth, currentDepth + 1);
					break;
				}
			}
		}
	}
		
	public void generateF(List<String> output, int maxDepth, int currentDepth) {
		//F -> F ^ G | G
		if(currentDepth > maxDepth) { //prevent from exploding memory
			generateG(output, maxDepth, currentDepth + 1);
		}
		else {
			
			int i = rand.nextInt(2);
			switch(i) {
				case 0 : {
					output.add(LEFTPAR); 
					generateF(output, maxDepth, currentDepth + 1);
					output.add(POWER);
					generateG(output, maxDepth, currentDepth + 1);
					output.add(RIGHTPAR); 
					break;
				}
				case 1 : {
					generateG(output, maxDepth, currentDepth + 1);
					break;
				}
			}
		}
	}

	public void generateG(List<String> output, int maxDepth, int currentDepth) {
		//G -> sin ( H ) | cos ( H ) | H
		if(currentDepth > maxDepth) { //prevent from exploding memory
			generateH(output, maxDepth, currentDepth + 1);
		}
		else {
			
			int i = rand.nextInt(3);
			switch(i) {
				case 0: {
					output.add(SIN); 
					output.add(LEFTPAR); 
					generateH(output, maxDepth, currentDepth + 1);
					output.add(RIGHTPAR);
					break;
				} 
				case 1: {
					output.add(COS); 
					output.add(LEFTPAR); 
					generateH(output, maxDepth, currentDepth + 1);
					output.add(RIGHTPAR);
					break;
				}
				case 2: {
					generateH(output, maxDepth, currentDepth + 1);
					break;
				}
			}
		}
	}

	public void generateH(List<String> output, int maxDepth, int currentDepth) {
		//H -> E | id
		if(currentDepth > maxDepth) { //prevent from exploding memory
			generateId(output);
		}
		else {
			int i = rand.nextInt(2);
			switch(i) {
				case 0 : {
					generateE(output, maxDepth, currentDepth + 1);
					break;
				}
				case 1 : {
					generateId(output);
					break;
				}	
			}
		}
	}
		
	public void generateId(List<String> output) {
		int i = rand.nextInt(2);
		switch(i) {
			case 0 : {
				output.add(Integer.toString(rand.nextInt(Integer.MAX_VALUE)));
				break; 
			}
			case 1 : {
				output.add("x");
				break;
			}	
		}
	}
	
}