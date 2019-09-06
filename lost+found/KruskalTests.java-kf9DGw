import java.io.*; 
import java.util.*;

/* NB : I had to rename my classes Vertex, Edge and AdjacencyList 
   in TestVertex, TestEdge and TestAdjacencyList in order to avoid
   name conflicts with student classes.   */

public class KruskalTests {
	
	public static void main(String[] args) {
		testSmallCities(); 
		testBigCities(); 
		testCustomCities(); 
		testCustomCities2(); 
		testCustomCities3();
	}

	public static void testSmallCities() {
		String in = "./cities_small.txt";
		String out = "./cities_small_sol.txt";
		int numberOfNodes = 50; // number of nodes of this instance
		int optimalCost = 20522; // optimal cost for this instance
		System.out.println("Problem 1 : small cities");
		applyTest(in, out, numberOfNodes, optimalCost); 
	}
	
	public static void testBigCities() {
		String in = "./cities.txt";
		String out = "./cities_sol.txt";
		int numberOfNodes = 500; // number of nodes of this instance
		int optimalCost = 149718; // optimal cost for this instance
		System.out.println("Problem 2 : cities");
		applyTest(in, out, numberOfNodes, optimalCost); 
	}
	
	public static void testCustomCities() {
		String in = "./cities_custom.txt";
		String out = "./cities_custom_sol.txt";
		int numberOfNodes = 3; // number of nodes of this instance
		int optimalCost = 3; // optimal cost for this instance
		System.out.println("Problem 3 : custom cities");
		applyTest(in, out, numberOfNodes, optimalCost);
	}
	
	public static void testCustomCities2() {
		String in = "./cities_custom_2.txt";
		String out = "./cities_custom_sol_2.txt";
		int numberOfNodes = 4; // number of nodes of this instance
		int optimalCost = 11; // optimal cost for this instance
		System.out.println("Problem 4 : custom cities 2");
		applyTest(in, out, numberOfNodes, optimalCost);
	}
	
	public static void testCustomCities3() {
		String in = "./cities_custom_3.txt";
		String out = "./cities_custom_sol_3.txt";
		int numberOfNodes = 5; // number of nodes of this instance
		int optimalCost = 11; // optimal cost for this instance
		System.out.println("Problem 5 : custom cities 3");
		applyTest(in, out, numberOfNodes, optimalCost);
	}
	
	public static void applyTest(String in, String out, int numberOfNodes, int optimalCost) {
    try {
		int nNodes = 0; 
		int nEdges = 0; 
		int cost = 0; 
		int cheat = 0; 
		int connected = 0; 
		Kruskal.main(new String[] {in, out}); // apply the student algorithm
		
		Map<Integer, Triplet> edges = fillMap(in, numberOfNodes); // to check if he didn't invent new edges
		int[] result = readSol(out, edges, numberOfNodes); // get the solution of the student in 'result'
		
		if (result[0] != numberOfNodes) // all the nodes are involved (it's 'spanning')
			nNodes++; 
		if (result[1] != numberOfNodes-1) // number of edges = number of nodes - 1 (it's a 'tree')
			nEdges++; 
		if (result[2] > optimalCost) // the cost is optimal (it's a 'minimum' spanning tree)
			cost++; 
		else if (result[2] < optimalCost)
			cost--; 
		if (result[3] != 0) // the student didn't cheat : if an edge is part of his solution, it was in the input file
			cheat++; 
		
		TestAdjacencyList studentSol = new TestAdjacencyList(out); // create the graph representing his solution
		if (studentSol.isConnected() != true) // the result of the student is a connected graph
			connected++; 
		if (nEdges > 0) System.out.println("KO: your solution should involve N-1 edges (not a 'tree')");
		if (nNodes > 0) System.out.println("KO: your solution doesn't involve all the nodes (not a 'spanning' tree)"); 
		if (cost > 0) System.out.println("KO: your solution is not optimal (not a 'minimum' spanning tree)");
		if (cheat > 0) System.out.println("KO: well tried, but you must use edges given in the input file !");
		if (connected > 0) System.out.println("KO: your graph is not connected"); 
		if (nEdges <= 0 && nNodes <= 0 && cost <= 0 && cheat <= 0 && connected <= 0) System.out.println("OK"); 
		//if (cost < 0) System.out.println("It seems like your solution is better than the optimal solution !?");
	} catch(Exception e) {
    	System.out.println("KO: An exception occured : " + e);
        //e.printStackTrace(System.out);
    }
	}

	public static Map<Integer, Triplet> fillMap(String path, int numberOfNodes) {
		Map<Integer, Triplet> edges = new HashMap<Integer, Triplet>(); 
		try {
			File f = new File (path);
			Scanner s = new Scanner(f);

			try {
				while (s.hasNextInt())
				{
					int v1 = s.nextInt(); 
					int v2 = s.nextInt(); 
					int cost = s.nextInt(); 

					int key = Math.min(v1, v2) + numberOfNodes*Math.max(v1,  v2); 
					Triplet value = new Triplet(Math.min(v1, v2), Math.max(v1, v2), cost); 
					edges.put(key, value);
				}

				s.close(); 
			} catch (Exception e)
			{
				//System.out.println ("Error occured while reading the file  : " + e.getMessage());
			}
		} catch (FileNotFoundException exception) {
			//System.out.println ("File not found. ");
		}

		return edges; 
	}

	public static int[] readSol(String path, Map<Integer, Triplet> edges, int numberOfNodes) {
		Set<Integer> nodes = new HashSet<Integer>(); 
		int numberOfEdges = 0; 
		int totalCost = 0; // cost found by the student
		int cheat = 0; // incremented if fake edge
		try {
				File f = new File(path);
				Scanner s = new Scanner(f); 
				while (s.hasNextInt())
				{
					int v1 = s.nextInt();
					int v2 = s.nextInt();
					int cost = s.nextInt();
					// Check that this line indeed exists in the original file
					Triplet t = edges.get(Math.min(v1, v2) + numberOfNodes*Math.max(v1,  v2));
					//System.out.println(t.v1 + " " + t.v2 + " " + t.cost);
					if (t == null || t.cost != cost)
						cheat++; 
					numberOfEdges += 1; 
					totalCost += cost; 
					nodes.add(v1); 
					nodes.add(v2); 
				}
				s.close();
		} catch (Exception e) {
    		System.out.println("KO: error while reading your solution : " + e);
		}
		return new int[] {nodes.size(), numberOfEdges, totalCost, cheat}; 
	}
}

class Triplet {
	public int v1; 
	public int v2; 
	public int cost; 

	public Triplet(int v1, int v2, int cost) {
		this.v1 = v1; 
		this.v2 = v2; 
		this.cost = cost; 
	}
}

/**
 * Classe implementant l'ADT "Graphe" par une structure de type "liste d'adjacence"
 * Contient egalement un constructeur permettant de creer une
 * liste d'adjacence sur base d'un String. 
 * 
 * @author Simon Hardy
 */
class TestAdjacencyList {

	private LinkedList<TestVertex> V;
	private LinkedList<TestEdge> E; 

	public TestAdjacencyList() {
		V = new LinkedList<TestVertex>(); 
		E = new LinkedList<TestEdge>();
	}

	/* Construction sur base du fichier dont le chemin est passe en argument
	 * NB : on considere ici que deux numeros differents correspondent
	 * a deux aeroports differents (et donc deux noeuds differents) */
	public TestAdjacencyList(String s) {
		V = new LinkedList<TestVertex>(); 
		E = new LinkedList<TestEdge>();
		try
		{
			File f = new File (s);
		    Scanner scan = new Scanner(f); 
		 
		    try {
				while (scan.hasNextInt())
				{
				    TestVertex one, two; 
				    TestVertex v; 
				    TestEdge e; 
				    int n1 = scan.nextInt(); 
				    int n2 = scan.nextInt();
				    int e1 = scan.nextInt(); 
				    if ((v = this.contains(n1)) == null) {
				    	one = insertVertex(n1);
				    }
				    else {
				    	one = v;
				    }
				    if ((v = this.contains(n2)) == null) {
				    	two = insertVertex(n2); 
				    }
				    else
				    	two = v; 
				    	// NB : on autorise 2 aretes avec les memes noeuds
				    	e = insertEdge(one, two, e1); 
				    	one.addToI(e);
				    	e.setSrcEdges(one.getI());
				    	two.addToI(e);
				    	e.setDstEdges(two.getI());
				}
			 
				scan.close();
			}
		    catch(Exception e)
		    {
			//System.out.println ("Erreur lors de la lecture : " + e.getMessage());
		    }
		}
		catch (FileNotFoundException exception)
		{
		    //System.out.println ("Le fichier n'a pas ete trouve");
		}
	}

	/* Methodes de l'ADT */
	public LinkedList<TestVertex> vertices() {
		return V;
	}

	public LinkedList<TestEdge> edges() {
		return E;
	}

	public LinkedList<TestEdge> incidentEdges(TestVertex v) {
		return v.getI(); 
	}

	public TestVertex opposite(TestVertex v, TestEdge e) {
		if (e.getSrc().getCity() == v.getCity()) return e.getDst();
		else if (e.getDst().getCity() == v.getCity()) return e.getSrc(); 
		else {
			System.out.println("Error in 'opposite'");
			return null; 
		}
	}

	public ArrayList<TestVertex> endVertices(TestEdge e) {
		ArrayList<TestVertex> array = new ArrayList<TestVertex>(); 
		array.add(e.getSrc()); 
		array.add(e.getDst()); 
		return array; 
	}

	public boolean AreAdjacent(TestVertex v, TestVertex w) {
		LinkedList<TestEdge> edges = v.getI(); 
		for (int i = 0 ; !edges.isEmpty() ; i++) {
			if (edges.get(i).getSrc().getCity() == v.getCity() || edges.get(i).getDst().getCity() == v.getCity())
				return true; 
		}
		return false;
	}

	public void replace(TestVertex v, int x) {
		v.setCity(x); 
	}

	public void replace(TestEdge e, int x) {
		e.setWeight(x);
	}

	public TestVertex insertVertex(int x) {
		TestVertex c = new TestVertex(x, V.size());
		V.add(c); // a ameliorer
		return c; 
	}

	public TestEdge insertEdge(TestVertex v, TestVertex w, int x) {
		TestEdge c = new TestEdge(v, w, x, E.size());
		E.add(c); // a ameliorer
		return c; 
	}

	public void removeVertex(TestVertex v) {
		LinkedList<TestEdge> i = v.getI(); 
		while (!i.isEmpty())
			E.remove(i.getFirst()); // on supprime toutes les arretes incidentes a ce noeud
		V.remove(v.getPosition()); // a ameliorer
	}

	public void removeEdge(TestEdge e) {
		E.remove(e.getPosition()); // a ameliorer
		// Enlever les references des I vers E ?
	}

	/* Autres methodes interessantes : */

	/* Si V contient un element dont la valeur est X, on le retourne. 
	   Sinon, on retourne null. */
	public TestVertex contains(int x) {
		if (V.isEmpty())
			return null; 
		int i = 0; 
		int size = V.size();
		TestVertex c = null; 
		while (i < size) {
			c = V.get(i); 
			if (c.getCity() == x)
				return c;
			i++; 
		}
		return null;
	}

	public boolean isConnected() {
    	if (vertices().isEmpty()) return true; 
		DFS(vertices().getFirst());
		boolean result = true; 
		for (TestVertex v : vertices()) {
			if (v.visited)
				v.visited = false; 
			else
				result = false; 
		}
		return result; 
	}

	public void DFS(TestVertex v) {
		v.visited = true;
		for (TestEdge e : v.getI()) {
			TestVertex opposite = opposite(v, e);
			if (!opposite.visited)
				DFS(opposite);
			// else there is a cycle
		}
	}

	public int numberOfNodes() {
		return vertices().size(); 
	}

	public int numberOfEdges() {
		return edges().size();
	}

	public int cost() {
		int sum = 0; 
		for (TestEdge e : edges())
			sum += e.getWeight(); 
		return sum; 
	}
}

/**
 * Classe representant un noeud du graphe
 * 
 * @author Simon Hardy
 */
class TestVertex {

	private int city; 
	private LinkedList<TestEdge> I;
	private int position; // position dans V (devrait etre une reference !)
	public boolean visited; // for DFS

	public TestVertex(int x) {
		city = x;
		I = new LinkedList<TestEdge>(); 
		position = 0;
		visited = false;
	}

	public TestVertex(int x, int pos) {
		city = x;
		I = new LinkedList<TestEdge>();
		position = pos;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public LinkedList<TestEdge> getI() {
		return I;
	}
	public void setI(LinkedList<TestEdge> i) {
		I = i;
	}
	public void addToI(TestEdge e) {
		I.add(e); 
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
}

/**
 * Classe representant une arete du graphe
 * @author Simon Hardy
 **/
class TestEdge implements Comparable<TestEdge> {

	private int weight; 
	private TestVertex src; 
	private TestVertex dst; 
	private LinkedList<TestEdge> srcEdges; // reference ! (redondant ?)
	private LinkedList<TestEdge> dstEdges;  // reference ! (redondant ?)
	private int position; // position dans V (devrait etre une reference !)

	public TestEdge(TestVertex src, TestVertex dst, int x) {
		weight = x; 
		this.src = src ; 
		this.dst = dst; 
		srcEdges = src.getI();
		dstEdges = dst.getI();
		position = 0;
	}

	public TestEdge(TestVertex src, TestVertex dst, int x, int pos) {
		weight = x; 
		this.src = src ; 
		this.dst = dst; 
		srcEdges = src.getI();
		dstEdges = dst.getI();
		position = pos;
	}

	public int compareTo(TestEdge e)
	{
		return this.getWeight() - e.getWeight();
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public TestVertex getSrc() {
		return src;
	}

	public void setSrc(TestVertex src) {
		this.src = src;
	}

	public TestVertex getDst() {
		return dst;
	}

	public void setDst(TestVertex dst) {
		this.dst = dst;
	}

	public LinkedList<TestEdge> getSrcEdges() {
		return srcEdges;
	}

	public void setSrcEdges(LinkedList<TestEdge> srcEdges) {
		this.srcEdges = srcEdges;
	}

	public LinkedList<TestEdge> getDstEdges() {
		return dstEdges;
	}

	public void setDstEdges(LinkedList<TestEdge> dstEdges) {
		this.dstEdges = dstEdges;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}
}
