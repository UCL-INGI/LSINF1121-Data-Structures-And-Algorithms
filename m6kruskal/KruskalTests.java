import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap; 
import java.util.HashSet;
import java.util.Map; 
import java.util.Set;

public class KruskalTests {
	
	public static void main(String[] args) {
		testSmallCities(); 
		testBigCities(); 
		testCustomCities(); 
		testCustomCities2(); 
		testCustomCities3();
	}

	public static void testSmallCities() {
		String in = "./src/data/cities_small.txt";
		String out = "./src/data/cities_small_sol.txt";
		int numberOfNodes = 50; // number of nodes of this instance
		int optimalCost = 20522; // optimal cost for this instance
		System.out.println("Problem 1 : small cities");
		applyTest(in, out, numberOfNodes, optimalCost); 
	}
	
	public static void testBigCities() {
		String in = "./src/data/cities.txt";
		String out = "./src/data/cities_sol.txt";
		int numberOfNodes = 500; // number of nodes of this instance
		int optimalCost = 149718; // optimal cost for this instance
		System.out.println("Problem 2 : cities");
		applyTest(in, out, numberOfNodes, optimalCost); 
	}
	
	public static void testCustomCities() {
		String in = "./src/data/cities_custom.txt";
		String out = "./src/data/cities_custom_sol.txt";
		int numberOfNodes = 3; // number of nodes of this instance
		int optimalCost = 3; // optimal cost for this instance
		System.out.println("Problem 3 : custom cities");
		applyTest(in, out, numberOfNodes, optimalCost);
	}
	
	public static void testCustomCities2() {
		String in = "./src/data/cities_custom_2.txt";
		String out = "./src/data/cities_custom_sol_2.txt";
		int numberOfNodes = 4; // number of nodes of this instance
		int optimalCost = 11; // optimal cost for this instance
		System.out.println("Problem 4 : custom cities 2");
		applyTest(in, out, numberOfNodes, optimalCost);
	}
	
	public static void testCustomCities3() {
		String in = "./src/data/cities_custom_3.txt";
		String out = "./src/data/cities_custom_sol_3.txt";
		int numberOfNodes = 5; // number of nodes of this instance
		int optimalCost = 11; // optimal cost for this instance
		System.out.println("Problem 5 : custom cities 3");
		applyTest(in, out, numberOfNodes, optimalCost);
	}
	
	public static void applyTest(String in, String out, int numberOfNodes, int optimalCost) {
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
		
		AdjacencyList studentSol = new AdjacencyList(out); // create the graph representing his solution
		if (studentSol.isConnected() != true) // the result of the student is a connected graph
			connected++; 
		if (nEdges > 0) System.out.println("KO: your solution should involve N-1 edges (not a 'tree')");
		if (nNodes > 0) System.out.println("KO: your solution doesn't involve all the nodes (not a 'spanning' tree)"); 
		if (cost > 0) System.out.println("KO: your solution is not optimal (not a 'minimum' spanning tree)");
		if (cheat > 0) System.out.println("KO: well tried, but you must use edges given in the input file !");
		if (connected > 0) System.out.println("KO: your graph is not connected"); 
		if (nEdges <= 0 && nNodes <= 0 && cost <= 0 && cheat <= 0 && connected <= 0) System.out.println("OK"); 
		if (cost < 0) System.out.println("It seems like your solution is better than the optimal solution !?");
	}

	public static Map<Integer, Triplet> fillMap(String path, int numberOfNodes) {
		Map<Integer, Triplet> edges = new HashMap<Integer, Triplet>(); 
		try {
			File f = new File (path);
			FileReader fr = new FileReader (f);
			BufferedReader br = new BufferedReader(fr);

			try {
				String line = br.readLine();
				while (line != null)
				{
					int v1 = firstNode(line); 
					int v2 = secondNode(line); 
					int cost = firstEdge(line); 

					int key = Math.min(v1, v2) + numberOfNodes*Math.max(v1,  v2); 
					Triplet value = new Triplet(Math.min(v1, v2), Math.max(v1, v2), cost); 
					edges.put(key, value);

					line = br.readLine();
				}

				br.close();
				fr.close();
			} catch (IOException exception)
			{
				System.out.println ("Error occured while reading the file  : " + exception.getMessage());
			}
		} catch (FileNotFoundException exception) {
			System.out.println ("File not found. ");
		}

		return edges; 
	}

	public static int[] readSol(String path, Map<Integer, Triplet> edges, int numberOfNodes) {
		Set<Integer> nodes = new HashSet<Integer>(); 
		int numberOfEdges = 0; 
		int totalCost = 0; // cost found by the student
		int cheat = 0; // incremented if fake edge
		try {
			File f = new File (path);
			FileReader fr = new FileReader (f);
			BufferedReader br = new BufferedReader(fr);

			try {
				String line = br.readLine();
				while (line != null)
				{
					int v1 = firstNode(line); 
					int v2 = secondNode(line); 
					int cost = firstEdge(line); 
					// Check that this line indeed exists in the original file
					Triplet t = edges.get(Math.min(v1, v2) + numberOfNodes*Math.max(v1,  v2));
					//System.out.println(t.v1 + " " + t.v2 + " " + t.cost);
					if (t == null || t.cost != cost)
						cheat++; 
					numberOfEdges += 1; 
					totalCost += cost; 
					nodes.add(v1); 
					nodes.add(v2); 

					line = br.readLine();
				}

				br.close();
				fr.close();
			} catch (IOException exception)
			{
				System.out.println ("Error occured while reading the file  : " + exception.getMessage());
			}
		} catch (FileNotFoundException exception) {
			System.out.println ("File not found. ");
		}
		return new int[] {nodes.size(), numberOfEdges, totalCost, cheat}; 
	}

	/* Renvoie le premier noeud de la ligne */
	public static int firstNode(String line) {
		String number = ""; 
		int i = 0;
		char c = line.charAt(i); 
		while (c != '\t') {
			number += c; 
			i++; 
			c = line.charAt(i); 
		}
		return Integer.parseInt(number); 
	}


	/* Renvoie le second noeud de la ligne */

	public static int secondNode(String line) {
		String number = ""; 
		int i = 0;
		int count = 0; // pour tout skipper avant la premiere tabulation
		char c = line.charAt(i); 
		while (c != '\t' || count == 0) {
			if (count == 1) number += c; 
			i++; 
			if (c == '\t') count++; 
			c = line.charAt(i); 
		}
		return Integer.parseInt(number); 
	}

	/* Renvoie l'arete de la ligne */
	public static int firstEdge(String line) {
		String number = ""; 
		int i = 0;
		int count = 0; // pour tout skipper avant la 2eme tabulation
		char c = line.charAt(i); 
		while (i < line.length()) {
			if (count == 2) number += c; 
			i++; 
			if (c == '\t') count++; 
			if (i < line.length()) c = line.charAt(i); 
		}
		return Integer.parseInt(number); 
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