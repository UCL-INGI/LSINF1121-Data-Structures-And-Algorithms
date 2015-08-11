import java.util.ArrayList;
import java.util.Stack;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;


/* Buggy version of the solution of Simon Hardy for the mission 2 */
public class ExpressionTree
{
	private ExpressionTree left;
	private ExpressionTree right;
	private boolean isOperator;
	private boolean isVariable;
	private String expression;
	private int value;

	/* Method main for some personal tests, not used in INGInious */
	public static void main(String[] args) {

		try {
			ExpressionTree tree = new ExpressionTree("0");		
			ExpressionTree der = tree.derive();
			System.out.println(der);
		} catch (ParseException e) {System.out.println(e);}

		/*try {
			Generator g = new Generator(42); 
			for (int k = 0 ; k < 100 ; k++) {
				String[] res = g.generateWrong(20); 
				String expression = ""; 
				for (int i = 0 ; i < res.length ; i++) {
					expression += res[i]; 
				}
				//System.out.println(expression);
				//String expression = "(((sin(5)^10)*50)-(5*cos(x)))"; 
				ExpressionTree tree = new ExpressionTree(expression);
				System.out.println("NOPE");
			}
		} catch (ParseException e) {
			//System.out.println("ParseException : " + e + "\n");
		}*/

		/*Generator g = new Generator(Integer.parseInt(args[0])); 
		String[] res = g.generate(1); 
		String expression = ""; 
		for (int i = 0 ; i < res.length ; i++) {
			expression += res[i]; 
		}
		try {
			//expression = "(((5*x)))"; 

			System.out.println("Expression: " + expression); 
			ExpressionTree tree = new ExpressionTree(expression);
			System.out.println("ExpressionTree: " + tree.toString());
			ExpressionTree derivedTree = tree.derive();
			String expr = derivedTree.toString(); 
			System.out.println("Derived ExpressionTree: " + expr + "\n");

			// Evaluate to compare them
			ScriptEngineManager manager = new ScriptEngineManager();
    			ScriptEngine engine = manager.getEngineByName("js");  
			try {     
    				Object result = engine.eval(expr.replace('x', '7'));
				System.out.println("Evaluation of the derivative (x=7): " + result.toString()); 
			} catch (ScriptException e) {
				System.out.println("ScriptException"); 
			}
		} catch (ParseException e) {
			System.out.println(e + "\n");
		}*/
	}

	/* Checks for parsing errors */
	public void checkParsing(String expression) throws ParseException {
		Stack<String> stack = new Stack<String>(); 
		for(int i = 0; i < expression.length(); i++)
		{
			char c = expression.charAt(i);
			if (c == '(') {
				if (stack.empty() || stack.pop().equals("id")) {
					stack.push(")");
					stack.push("id"); 
					stack.push("op"); 
					stack.push("id"); 
				}
			}
			else if (c == ')') {
				if (stack.empty() || !stack.pop().equals(")"))
					throw new ParseException("')' not expected");
			}
			else if (isOperator(c)) {
				if(expression.length() >= 3 && i+3 <= expression.length() && (expression.substring(i, i+3).equals("sin") || expression.substring(i, i+3).equals("cos"))) {
					i += 2;
					if (stack.empty()) {
						stack.push(")");
						stack.push("id");
						stack.push("(");
					}
					else if (stack.pop().equals("id")) {
						stack.push(")");
						stack.push("id");
						stack.push("(");
					}
					else
						throw new ParseException("sin/cos not expected");
				}
				else if (stack.empty() || !stack.pop().equals("op"))
					//throw new ParseException("operator not expected"); BUG HERE
			}
			else if (isNumerical(c)) {
				int j=0;
				while(i+j < expression.length() && isNumerical(expression.charAt(i+j)))
					j++;
				if (!stack.empty() && !stack.pop().equals("id"))
					throw new ParseException("numerical not expected");
				else if (stack.empty() && expression.length() != j) // not the only token in the expression
					throw new ParseException("numerical not expected here");
				i+=j-1;
			}
			else if (isVariable(c)) {
				if (!stack.empty() && !stack.pop().equals("id"))
					throw new ParseException("variable not expected");
				else if (stack.empty() && expression.length() != 1) // not the only token (variable) in the expression
					throw new ParseException("variable not expected here");
			}
			else
				throw new ParseException("token not recognized");
		}
		if (!stack.empty())
			throw new ParseException("expression not complete");
	}

	
	/* CONSTRUCTEUR
	 * Construit un arbre élémentaire correspondant à l'expression "0"
	 */
	public ExpressionTree()
	{
		// est la valeur 0
		this.isOperator = false; 
		this.isVariable = false; 
		this.value = 0;
	}


	/* CONSTRUCTEUR

	 * prenant comme argument une chaîne de caractères (String) et construisant l'arbre associé.

	 * Cette chaîne est supposée correspondre à une expression analytique syntaxiquement correcte et complètement parenthèsée. 
	 * Une gestion d'exceptions doit être prévue lorsque cette précondition n'est pas vérifiée. 
	 */
	public ExpressionTree(String expression) throws ParseException
	{
		if (expression.length() == 0) {
			// est la valeur 0
			this.isOperator = false; 
			this.isVariable = false; 
			this.value = 0;
			return;
		}
		checkParsing(expression); 
		Stack<ExpressionTree> stack = new Stack<ExpressionTree>();
		boolean depiler = false;
		int parenthesesSinceLastOperator = 0;
		int countParentheses = 0;
		
		for(int i = 0; i < expression.length(); i++) {
			if(expression.charAt(i) == '(')
				countParentheses++;
			
			if(expression.charAt(i) == ')')
				countParentheses--;
			
			if(expression.charAt(i) != '(' &&
			   expression.charAt(i) != ')' &&
			   !isOperator(expression.charAt(i)) &&
			   !isVariable(expression.charAt(i)) &&
			   !isNumerical(expression.charAt(i)))
				throw new ParseException("Erreur dans la ligne !");
		}
		
		if(countParentheses != 0)
			throw new ParseException("Erreur dans la ligne !");
		
		for(int i = 0; i < expression.length(); i++)
		{
			char c = expression.charAt(i);
			if(isOperator(c))
			{
				parenthesesSinceLastOperator = 0;
				if(expression.length() >= 3 && i+3 <= expression.length() && expression.substring(i, i+3).equals("sin"))
				{
					i += 2;
					ExpressionTree e = new ExpressionTree();
					e.setExpression("sin");
					e.setIsOperator(true);
					e.setIsVariable(false);
					stack.push(e);
				}
				else if(expression.length() >= 3 && i+3 <= expression.length() && expression.substring(i, i+3).equals("cos"))
				{
					i += 2;
					ExpressionTree e = new ExpressionTree();
					e.setExpression("cos");
					e.setIsOperator(true);
					e.setIsVariable(false);
					stack.push(e);
				}
				else
				{				
					if(depiler) {
						depilage(stack);
						depiler = false;
					}
					
					if(c == '^' || c == '*' || c == '/') {
						depiler = true;
					}
					
					if(c == '-' && (stack.empty() || 
					  (!stack.empty() && i > 1 && (expression.charAt(i-1)=='('))))
					{
						depiler = false;
						ExpressionTree o = new ExpressionTree();
						o.setValue(0);
						o.setIsOperator(false);
						o.setIsVariable(false);
						stack.push(o);
					}
					
					ExpressionTree e = new ExpressionTree();
					e.setExpression("" + c);
					e.setIsOperator(true);
					e.setIsVariable(false);
					stack.push(e);
				}
				
			}
			else if(isVariable(c))
			{
				ExpressionTree e = new ExpressionTree();
				e.setExpression("" + c);
				e.setIsOperator(false);
				e.setIsVariable(true);
				stack.push(e);
			}
			else if(isNumerical(c))
			{
				int j=0;
				while(i+j < expression.length() && isNumerical(expression.charAt(i+j)))
					j++;
				
				int k = Integer.parseInt(expression.substring(i,i+j));
				ExpressionTree e = new ExpressionTree();
				e.setValue(k);
				e.setIsOperator(false);
				e.setIsVariable(false);
				stack.push(e);
				i+=j-1;
			}
			else if(c == '(')
			{
				depiler = false;
				parenthesesSinceLastOperator++;
			}
			else if(c == ')')
			{
				parenthesesSinceLastOperator--;
				if(((stack.peek().isOperator() || stack.peek().isVariable()) && 
						stack.size() >= 2 && 
						parenthesesSinceLastOperator == 0)
					 || stack.size() >= 3) {
					depilage(stack);
					depiler = false;
				}
			}
			else
				throw new ParseException("Erreur dans la ligne !");
			
		}
		
		if(stack.size() != 1 && parenthesesSinceLastOperator == 0)
			depilage(stack);
		ExpressionTree t = stack.pop();
		this.expression = t.getExpression();
		this.isOperator = t.isOperator();
		this.isVariable = t.isVariable();
		this.left = t.getLeft();
		this.right = t.getRight();
		this.value = t.getValue();
	}
	
	public static void depilage(Stack<ExpressionTree> stack) {
		ExpressionTree t2;
		ExpressionTree t;
		ExpressionTree t1;
		
		t2 = stack.pop();
		t = stack.pop();
		if(!(t.getExpression().equals("sin") || t.getExpression().equals("cos"))) {
			t1 = stack.pop();
			t.setLeft(t1);
			t.setRight(t2);
			stack.push(t);
		} else {
			t.setRight(t2);
			stack.push(t);
		}
	}
	
	/**
	 * FONCTIONS
	 */
	
	/**
	* Cette méthode calcule le nouvel arbre correspondant à la dérivée formelle de l'arbre courant. 
	* L'arbre courant (this) n'est pas modifié.
	* 
	* @pre   this représente une expression analytique syntaxiquement correcte.
	* @post  Une référence à un nouvel arbre représentant la dérivée formelle de this est renvoyée. 
	*/
	
	// Vérifier que l'arbre courant n'est pas modifié !
	// La méthode dans l'interface n'a pas d'argument, correct quand même ?
	
	public ExpressionTree derive()
	{
		if(!this.isOperator() && !this.isVariable() )
		{
			ExpressionTree e = new ExpressionTree();
			e.setValue(0);
			e.setIsOperator(false);
			e.setIsVariable(false);
			return e;
		}
		else if(this.isVariable())
		{
			ExpressionTree e = new ExpressionTree();
			e.setValue(1);
			e.setIsOperator(false);
			e.setIsVariable(false);
			return e;
		}
		else if(this.getExpression().equals("+"))
		{
			ExpressionTree e = new ExpressionTree();
			e.setExpression("+");
			e.setIsOperator(true);
			e.setIsVariable(false);
			
			if(this.hasLeft())
				e.setLeft((ExpressionTree)this.getLeft().derive());
			if(this.hasRight())
				e.setRight((ExpressionTree)this.getRight().derive());
			return e;
		}
		else if(this.getExpression().equals("-"))
		{
			ExpressionTree e = new ExpressionTree();
			e.setExpression("-");
			e.setIsOperator(true);
			e.setIsVariable(false);

			if(this.hasLeft())
				e.setLeft((ExpressionTree)this.getLeft().derive());
			if(this.hasRight())
				e.setRight((ExpressionTree)this.getRight().derive());
			return e;
		}
		else if(this.getExpression().equals("*"))
		{
			ExpressionTree e = new ExpressionTree();
			ExpressionTree f = new ExpressionTree();
			ExpressionTree g = new ExpressionTree();

			e.setExpression("+");
			e.setIsOperator(true);
			e.setIsVariable(false);
			
			f.setExpression("*");
			f.setIsOperator(true);
			f.setIsVariable(false);
			
			g.setExpression("*");
			g.setIsOperator(true);
			g.setIsVariable(false);
			
			f.setLeft((ExpressionTree)this.getLeft().derive());
			f.setRight(this.getRight());
			
			g.setLeft(this.getLeft());
			g.setRight((ExpressionTree)this.getRight().derive());
			
			e.setLeft(f);
			e.setRight(g);
			return e;
		}
		else if(this.getExpression().equals("/"))
		{
			ExpressionTree e = new ExpressionTree();
			ExpressionTree f = new ExpressionTree();
			ExpressionTree g = new ExpressionTree();
			ExpressionTree h = new ExpressionTree();

			ExpressionTree gSquarre = new ExpressionTree();

			e.setExpression("/");
			e.setIsOperator(true);
			e.setIsVariable(false);
			
			h.setExpression("-");
			h.setIsOperator(true);
			h.setIsVariable(false);
			
			f.setExpression("*");
			f.setIsOperator(true);
			f.setIsVariable(false);
			
			g.setExpression("*");
			g.setIsOperator(true);
			g.setIsVariable(false);
			
			gSquarre.setExpression("*");
			gSquarre.setIsOperator(true);
			gSquarre.setIsVariable(false);
			
			f.setLeft((ExpressionTree)this.getLeft().derive());
			f.setRight(this.getRight());
			
			g.setLeft(this.getLeft());
			g.setRight((ExpressionTree)this.getRight().derive());
			
			h.setLeft(f);
			h.setRight(g);
			
			gSquarre.setLeft(this.getRight());
			gSquarre.setRight(this.getRight());
			
			e.setLeft(h);
			e.setRight(gSquarre);
			return e;
		}
		else if(this.getExpression().equals("sin"))
		{
			ExpressionTree e = new ExpressionTree();
			ExpressionTree f = new ExpressionTree();

			e.setExpression("*");
			e.setIsOperator(true);
			e.setIsVariable(false);
			
			f.setExpression("cos");
			f.setIsOperator(true);
			f.setIsVariable(false);
			
			f.setRight(this.getRight());
			e.setLeft((ExpressionTree)this.getRight().derive());
			e.setRight(f);
			return e;
		}
		else if(this.getExpression().equals("cos"))
		{
			ExpressionTree e = new ExpressionTree();
			ExpressionTree f = new ExpressionTree();
			ExpressionTree g = new ExpressionTree();

			e.setExpression("-");
			e.setIsOperator(true);
			e.setIsVariable(false);
			
			f.setExpression("sin");
			f.setIsOperator(true);
			f.setIsVariable(false);
			
			g.setExpression("*");
			g.setIsOperator(true);
			g.setIsVariable(false);
			
			f.setRight(this.getRight());
			g.setLeft((ExpressionTree)this.getRight().derive());
			g.setRight(f);
			e.setLeft(new ExpressionTree()); // 0
			e.setRight(g);
			
			return e;
		}
		else if(this.getExpression().equals("^"))
		{
			ExpressionTree e = new ExpressionTree();
			ExpressionTree f = new ExpressionTree();
			ExpressionTree g = new ExpressionTree();

			e.setExpression("*");
			e.setIsVariable(false);
			e.setIsOperator(true);
			
			f.setExpression("*");
			f.setIsOperator(true);
			f.setIsVariable(false);
			
			g.setExpression("^");
			g.setIsOperator(true);
			g.setIsVariable(false);
			
			// D�but modif Simon
			ExpressionTree h = new ExpressionTree();
			if (!this.getRight().isOperator() && !this.getRight().isVariable()) { // c'est une valeur
				h.setValue((this.getRight().getValue())-1);
				h.setIsOperator(false);
				h.setIsVariable(false);
				// on la d�cr�mente simplement
			}
			else { // c'est une variable ou un op�rateur
				ExpressionTree i = new ExpressionTree(); // arbre de valeur 1
				i.setValue(1);
				i.setIsOperator(false);
				i.setIsVariable(false);
				
				h.setExpression("-");
				h.setIsOperator(true);
				h.setIsVariable(false);
				
				h.setLeft(this.getRight());
				h.setRight(i);
				// ==> (t, "-", 1)
			}
			
			g.setLeft(this.getLeft());
			g.setRight(h); // this.getRight() -1
			// Fin modif Simon
			
			f.setLeft(this.getRight());
			f.setRight((ExpressionTree)this.getLeft().derive());
			
			e.setLeft(f);
			e.setRight(g);
			
			return e;
		}
		
		return null;
	}
	
	public boolean hasLeft() {
		try {
			return (this.left != null);
		} catch(NullPointerException e) {
			return false;
		}
	}

	public ExpressionTree getLeft() {
		return left;
	}

	public void setLeft(ExpressionTree left) {
		this.left = left;
	}
	
	public boolean hasRight() {
		try {
			return (this.right != null);
		} catch(NullPointerException e) {
			return false;
		}
	}

	public ExpressionTree getRight() {
		return right;
	}

	public void setRight(ExpressionTree right) {
		this.right = right;
	}

	public boolean isOperator() {
		return isOperator;
	}

	public void setIsOperator(boolean isOperator) {
		this.isOperator = isOperator;
	}

	public boolean isVariable() {
		return isVariable;
	}

	public void setIsVariable(boolean isVariable) {
		this.isVariable = isVariable;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	public boolean isVariable(char c)
	{
		return c == 'x';
	}
	
	public boolean isOperator(char c)
	{
		return (c == '*' || 
				c == '+' || 
				c == '-' || 
				c == '/' || 
				c == 's' || 
				c == 'i' || 
				c == 'n' || 
				c == 'c' || 
				c == 'o' || 
				c == '^');
	}
	
	public boolean isNumerical(char c)
	{
		return (c == '0' ||
				c == '1' ||
				c == '2' ||
				c == '3' ||
				c == '4' ||
				c == '5' ||
				c == '6' ||
				c == '7' ||
				c == '8' ||
				c == '9');
	}
	
	/**
	* Cette méthode renvoie une chaîne de caractères correspondant à
	* l'expression analytique représentée dans l'arbre.
	*  
	* @pre  this représente une expression analytique syntaxiquement correcte
	* @post une chaîne de caractères, correspondant à l'expression analytique 
	*       complétement parenthésée représentée par this, est renvoyée.
	*/  
	public String toString() {
		String str = "", strLeft = "", strRoot = "", strRight = "";
		boolean parenthese = false;
        
	       	if (hasLeft()) {	            	
		    strLeft = this.left.toString();
		    parenthese = true;
		}

	       	// "root" peut être :
	 		//  - un opérateur
	 		//  - une variable
	 		//  - autre chose
	       	if(this.isOperator())
	       		strRoot = this.getExpression();
	       	else if(this.isVariable())
	       		strRoot = this.getExpression();
	       	else {
	       		if(this.getExpression() == null)
	       			strRoot = Integer.toString(this.getValue());
	       		else
	       			strRoot = this.getExpression();
	       	}
	       
		if (hasRight()) {
		    strRight = this.right.toString();	
		}
		

		if(parenthese) {
			if (strRoot.equals("sin") || strRoot.equals("cos"))
				str = "(" + strLeft + strRoot + "(" + strRight + ")" + ")";	
			else
				str = "(" + strLeft + strRoot + strRight + ")";

		}
		else {
			if (strRoot.equals("sin") || strRoot.equals("cos"))
				str = strLeft + strRoot + "(" + strRight + ")";
			else
				str = strLeft + strRoot + strRight;
		}
		return str;
	}

	/* Duplicate of toString so that JavaScript can interpret a^b as Math.power(a,b) [unit tests] */
	public String toStringJs() {
		String str = "", strLeft = "", strRoot = "", strRight = "";
		boolean parenthese = false;
        
	       	if (hasLeft()) {	            	
		    strLeft = this.left.toStringJs();
		    parenthese = true;
		}	                

	       	// "root" peut être :
	 		//  - un opérateur
	 		//  - une variable
	 		//  - autre chose
	       	if(this.isOperator())
	       		strRoot = this.getExpression();
	       	else if(this.isVariable())
	       		strRoot = this.getExpression();
	       	else {
	       		if(this.getExpression() == null)
	       			strRoot = Integer.toString(this.getValue());
	       		else
	       			strRoot = this.getExpression();
	       	}
	       
		if (hasRight()) {
		    strRight = this.right.toStringJs();	
		}

		if (parenthese)
			str = "("; 
		if (strRoot.equals("sin") || strRoot.equals("cos"))
			str += strLeft + strRoot + "(" + strRight + ")";
		else if (strRoot.equals("^"))
			str += "Math.pow(" + strLeft + "," + strRight + ")"; 
		else
			str += strLeft + strRoot + strRight;

		if (parenthese)
			str += ")"; 
		return str;
	}
}

