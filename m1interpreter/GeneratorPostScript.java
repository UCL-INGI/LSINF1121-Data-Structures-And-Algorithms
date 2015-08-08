import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Arrays;
import javax.script.ScriptEngineManager;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * Random generation of a valid chain of PostScript instructions
 * Note that the 'def' instruction is not generated here because of its complexity, 
 * it's tested manually in another ScalaCheck property. 
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
	
	/* Main method not used for the grading */
	public static void main(String[] args) {

		/*GeneratorPostScript g = new GeneratorPostScript(77); 
		String s = g.generate(30); 
		System.out.println(s); */
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

		for (int k = 0 ; k < maxDepth ; k++) {
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