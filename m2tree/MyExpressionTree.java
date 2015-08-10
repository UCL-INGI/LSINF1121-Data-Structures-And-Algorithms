import java.util.ArrayList;
import java.util.Stack;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

public class MyExpressionTree
{
	
	private MyExpressionTree left;
	private MyExpressionTree right;
	private boolean isOperator;
	private boolean isVariable;
	private String expression;
	private int value;
	
	/**
	 * CONSTRUCTEUR
	 */
	
	/* CONSTRUCTEUR
	 * Construit un arbre élémentaire correspondant à l'expression vide "" 
	 */
	public MyExpressionTree() 
	{
		// est la valeur 0
		this.isOperator = false; 
		this.isVariable = false; 
		this.value = 0;
	}

	/* Method main for some personal tests, not used in INGInious */
	public static void main(String[] args) {
		Generator g = new Generator(Integer.parseInt(args[0])); 
		String[] res = g.generate(1); 
		String expression = ""; 
		for (int i = 0 ; i < res.length ; i++) {
			expression += res[i]; 
		}
		try {
			expression = "x-cos(x)"; 

			System.out.println("Expression: " + expression); 
			MyExpressionTree tree = new MyExpressionTree(expression);
			System.out.println("MyExpressionTree: " + tree.toString());
			MyExpressionTree derivedTree = tree.derive();
			String expr = derivedTree.toString(); 
			System.out.println("Derived MyExpressionTree: " + expr + "\n");

			// Evaluate to compare them
			ScriptEngineManager manager = new ScriptEngineManager();
    			ScriptEngine engine = manager.getEngineByName("js");  
			try {     
	    			Object result = engine.eval(expr.replace('x', '7').replace("sin", "Math.sin").replace("cos", "Math.cos").replace("--", "+"));
				System.out.println("Evaluation of the derivative (x=7): " + result.toString()); 
			} catch (ScriptException e) {
				System.out.println("ScriptException"); 
			}
		} catch (MyParseException e) {
			System.out.println(e + "\n");
		}
	}
	
	/* CONSTRUCTEUR
	 * prenant comme argument une chaîne de caractères (String) et construisant l'arbre associé.
	 * Cette chaîne est supposée correspondre à une expression analytique syntaxiquement correcte et complètement parenthèsée. 
	 * TODO : Une gestion d'exceptions doit être prévue lorsque cette précondition n'est pas vérifiée. 
	 */
	public MyExpressionTree(String expression) throws MyParseException
	{
		Stack<MyExpressionTree> stack = new Stack<MyExpressionTree>();
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
				throw new MyParseException("Erreur dans la ligne !");
		}
		
		if(countParentheses != 0)
			throw new MyParseException("Erreur dans la ligne !");
		
		for(int i = 0; i < expression.length(); i++)
		{
			char c = expression.charAt(i);
			if(isOperator(c))
			{
				parenthesesSinceLastOperator = 0;
				if(expression.length() >= 3 && i+3 <= expression.length() && expression.substring(i, i+3).equals("sin"))
				{
					i += 2;
					MyExpressionTree e = new MyExpressionTree();
					e.setExpression("sin");
					e.setIsOperator(true);
					e.setIsVariable(false);
					stack.push(e);
				}
				else if(expression.length() >= 3 && i+3 <= expression.length() && expression.substring(i, i+3).equals("cos"))
				{
					i += 2;
					MyExpressionTree e = new MyExpressionTree();
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
						MyExpressionTree o = new MyExpressionTree();
						o.setValue(0);
						o.setIsOperator(false);
						o.setIsVariable(false);
						stack.push(o);
					}
					
					MyExpressionTree e = new MyExpressionTree();
					e.setExpression("" + c);
					e.setIsOperator(true);
					e.setIsVariable(false);
					stack.push(e);
				}
				
			}
			else if(isVariable(c))
			{
				MyExpressionTree e = new MyExpressionTree();
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
				MyExpressionTree e = new MyExpressionTree();
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
			
		}
		
		if(stack.size() != 1 && parenthesesSinceLastOperator == 0)
			depilage(stack);
		MyExpressionTree t = stack.pop();
		this.expression = t.getExpression();
		this.isOperator = t.isOperator();
		this.isVariable = t.isVariable();
		this.left = t.getLeft();
		this.right = t.getRight();
		this.value = t.getValue();
	}
	
	public static void depilage(Stack<MyExpressionTree> stack) {
		MyExpressionTree t2;
		MyExpressionTree t;
		MyExpressionTree t1;
		
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
	
	public MyExpressionTree derive()
	{
		if(!this.isOperator() && !this.isVariable() )
		{
			MyExpressionTree e = new MyExpressionTree();
			e.setValue(0);
			e.setIsOperator(false);
			e.setIsVariable(false);
			return e;
		}
		else if(this.isVariable())
		{
			MyExpressionTree e = new MyExpressionTree();
			e.setValue(1);
			e.setIsOperator(false);
			e.setIsVariable(false);
			return e;
		}
		else if(this.getExpression().equals("+"))
		{
			MyExpressionTree e = new MyExpressionTree();
			e.setExpression("+");
			e.setIsOperator(true);
			e.setIsVariable(false);
			
			if(this.hasLeft())
				e.setLeft((MyExpressionTree)this.getLeft().derive());
			if(this.hasRight())
				e.setRight((MyExpressionTree)this.getRight().derive());
			return e;
		}
		else if(this.getExpression().equals("-"))
		{
			MyExpressionTree e = new MyExpressionTree();
			e.setExpression("-");
			e.setIsOperator(true);
			e.setIsVariable(false);

			if(this.hasLeft())
				e.setLeft((MyExpressionTree)this.getLeft().derive());
			if(this.hasRight())
				e.setRight((MyExpressionTree)this.getRight().derive());
			return e;
		}
		else if(this.getExpression().equals("*"))
		{
			MyExpressionTree e = new MyExpressionTree();
			MyExpressionTree f = new MyExpressionTree();
			MyExpressionTree g = new MyExpressionTree();

			e.setExpression("+");
			e.setIsOperator(true);
			e.setIsVariable(false);
			
			f.setExpression("*");
			f.setIsOperator(true);
			f.setIsVariable(false);
			
			g.setExpression("*");
			g.setIsOperator(true);
			g.setIsVariable(false);
			
			f.setLeft((MyExpressionTree)this.getLeft().derive());
			f.setRight(this.getRight());
			
			g.setLeft(this.getLeft());
			g.setRight((MyExpressionTree)this.getRight().derive());
			
			e.setLeft(f);
			e.setRight(g);
			return e;
		}
		else if(this.getExpression().equals("/"))
		{
			MyExpressionTree e = new MyExpressionTree();
			MyExpressionTree f = new MyExpressionTree();
			MyExpressionTree g = new MyExpressionTree();
			MyExpressionTree h = new MyExpressionTree();

			MyExpressionTree gSquarre = new MyExpressionTree();

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
			
			f.setLeft((MyExpressionTree)this.getLeft().derive());
			f.setRight(this.getRight());
			
			g.setLeft(this.getLeft());
			g.setRight((MyExpressionTree)this.getRight().derive());
			
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
			MyExpressionTree e = new MyExpressionTree();
			MyExpressionTree f = new MyExpressionTree();

			e.setExpression("*");
			e.setIsOperator(true);
			e.setIsVariable(false);
			
			f.setExpression("cos");
			f.setIsOperator(true);
			f.setIsVariable(false);
			
			f.setRight(this.getRight());
			e.setLeft((MyExpressionTree)this.getRight().derive());
			e.setRight(f);
			return e;
		}
		else if(this.getExpression().equals("cos"))
		{
			MyExpressionTree e = new MyExpressionTree();
			MyExpressionTree f = new MyExpressionTree();
			MyExpressionTree g = new MyExpressionTree();

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
			g.setLeft((MyExpressionTree)this.getRight().derive());
			g.setRight(f);
			e.setRight(g);
			
			return e;
		}
		else if(this.getExpression().equals("^"))
		{
			MyExpressionTree e = new MyExpressionTree();
			MyExpressionTree f = new MyExpressionTree();
			MyExpressionTree g = new MyExpressionTree();

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
			MyExpressionTree h = new MyExpressionTree();
			if (!this.getRight().isOperator() && !this.getRight().isVariable()) { // c'est une valeur
				h.setValue((this.getRight().getValue())-1);
				h.setIsOperator(false);
				h.setIsVariable(false);
				// on la d�cr�mente simplement
			}
			else { // c'est une variable ou un op�rateur
				MyExpressionTree i = new MyExpressionTree(); // arbre de valeur 1
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
			f.setRight((MyExpressionTree)this.getLeft().derive());
			
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

	public MyExpressionTree getLeft() {
		return left;
	}

	public void setLeft(MyExpressionTree left) {
		this.left = left;
	}
	
	public boolean hasRight() {
		try {
			return (this.right != null);
		} catch(NullPointerException e) {
			return false;
		}
	}

	public MyExpressionTree getRight() {
		return right;
	}

	public void setRight(MyExpressionTree right) {
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
}
@SuppressWarnings("serial")
class MyParseException extends Exception {

	private String err;

	public MyParseException(String err) {
		this.err = err;
	}
	
	public String toString() {
		return err;
	}
}
