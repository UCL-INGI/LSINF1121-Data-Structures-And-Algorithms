import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.io.*; 

/**
 * Buggy version of Kruskal
 */
public class Kruskal {

	public static AdjacencyList applyKruskal(AdjacencyList graphe) {
		LinkedList<Vertex> v = graphe.vertices();
		int n = v.size(); 
		LinkedList<LinkedList<Vertex>> clusters = new LinkedList<LinkedList<Vertex>>(); 
		Edge e; 
		Vertex v1; 
		Vertex v2; 
		int a = 0, b = 0; 
		for (int i = 0 ; i < n; i++) {
			clusters.add(new LinkedList<Vertex>()); 
			clusters.get(i).add(v.get(i)); 
		}
		PriorityQueue<Edge> Q = new PriorityQueue<Edge>(graphe.edges()); 
		AdjacencyList T = new AdjacencyList(); // minimum spanning tree
		while (T.edges().size() < n-1) { // doit avoir n-1 aretes
			e = Q.remove(); // retire le minimum
			v1 = e.getSrc(); 
			v2 = e.getDst(); 
			for (int i = 0 ; i < clusters.size(); i++) {
				if (clusters.get(i).contains(v1))
					a = i; 
				if (clusters.get(i).contains(v2))
					b = i; 
			}
			if (a != b) {
				
	            Vertex one, two; 
	            Vertex temp; 
	            Edge edge; 
	            if ((temp = T.contains(v1.getCity())) == null) {
	            	one = T.insertVertex(v1.getCity());
	            }
	            else {
	            	one = temp;
	            }
	            if ((temp = T.contains(v2.getCity())) == null) {
	            	two = T.insertVertex(v2.getCity()); 
	            }
	            else
	            	two = temp; 
	            // Condition pour interdire deux fois la meme arete ?
	            edge = T.insertEdge(one, two, e.getWeight()); 
	           	one.addToI(edge);
	           	e.setSrcEdges(one.getI());
	           	two.addToI(edge);
	            e.setDstEdges(two.getI());
					
				clusters.get(a).addAll(clusters.get(b)); 
				clusters.remove(b); 
			}
		}
		return T; 
	}

	public static void main(String[] args) {
	    AdjacencyList list = new AdjacencyList(args[0]);//("src/data/cities.txt");
	    AdjacencyList result = applyKruskal(list); 
	    
	    try {
		    PrintWriter pw = new PrintWriter(new FileWriter(args[1]));//("src/data/cities_sol.txt"));
		    for (int i = 0 ; i < result.edges().size(); i++) {
			// BUG HERE, all edge weights + 1
		    	pw.println(result.edges().get(i).getSrc().getCity() + "\t" + result.edges().get(i).getDst().getCity() + "\t" + result.edges().get(i).getWeight()+1);
		    }
			pw.close();
	    } catch (IOException e) {
	    	System.out.println("IOException occured while writing result : " + e);
	    }
	    //System.out.println(result.cost());
	}

}

/**
 * Classe implementant l'ADT "Graphe" par une structure de type "liste d'adjacence"
 * Contient egalement un constructeur permettant de creer une
 * liste d'adjacence sur base d'un String. 
 * 
 * @author Simon Hardy
 */
class AdjacencyList {

	private LinkedList<Vertex> V;
	private LinkedList<Edge> E; 

	public AdjacencyList() {
		V = new LinkedList<Vertex>(); 
		E = new LinkedList<Edge>();
	}

	/* Construction sur base du du fichier dont le chemin est passe en argument
	 * NB : on considere ici que deux numeros differents correspondent
	 * a deux aeroports differents (et donc deux noeuds differents) */
	public AdjacencyList(String s) {
		V = new LinkedList<Vertex>(); 
		E = new LinkedList<Edge>();
		try
		{
			File f = new File (s);
		    FileReader fr = new FileReader (f);
		    BufferedReader br = new BufferedReader(fr);
		 
		    try {
			String line = br.readLine();
		 
			while (line != null)
			{
			    Vertex one, two; 
			    Vertex v; 
			    Edge e; 
			    if ((v = this.contains(firstNode(line))) == null) {
			    	one = insertVertex(firstNode(line));
			    }
			    else {
			    	one = v;
			    }
			    if ((v = this.contains(secondNode(line))) == null) {
			    	two = insertVertex(secondNode(line)); 
			    }
			    else
			    	two = v; 
			    	// NB : on autorise 2 aretes avec les memes noeuds
			    	e = insertEdge(one, two, firstEdge(line)); 
			    	one.addToI(e);
			    	e.setSrcEdges(one.getI());
			    	two.addToI(e);
			    	e.setDstEdges(two.getI());
			    	line = br.readLine();
			}
		 
			br.close();
			fr.close();
		    }
		    catch (IOException exception)
		    {
			System.out.println ("Erreur lors de la lecture : " + exception.getMessage());
		    }
		}
		catch (FileNotFoundException exception)
		{
		    System.out.println ("Le fichier n'a pas ete trouve");
		}
	}

	/* Methodes de l'ADT */
	public LinkedList<Vertex> vertices() {
		return V;
	}

	public LinkedList<Edge> edges() {
		return E;
	}

	public LinkedList<Edge> incidentEdges(Vertex v) {
		return v.getI(); 
	}

	public Vertex opposite(Vertex v, Edge e) {
		if (e.getSrc().getCity() == v.getCity()) return e.getDst();
		else if (e.getDst().getCity() == v.getCity()) return e.getSrc(); 
		else {
			System.out.println("Error in 'opposite'");
			return null; 
		}
	}

	public ArrayList<Vertex> endVertices(Edge e) {
		ArrayList<Vertex> array = new ArrayList<Vertex>(); 
		array.add(e.getSrc()); 
		array.add(e.getDst()); 
		return array; 
	}

	public boolean AreAdjacent(Vertex v, Vertex w) {
		LinkedList<Edge> edges = v.getI(); 
		for (int i = 0 ; !edges.isEmpty() ; i++) {
			if (edges.get(i).getSrc().getCity() == v.getCity() || edges.get(i).getDst().getCity() == v.getCity())
				return true; 
		}
		return false;
	}

	public void replace(Vertex v, int x) {
		v.setCity(x); 
	}

	public void replace(Edge e, int x) {
		e.setWeight(x);
	}

	public Vertex insertVertex(int x) {
		Vertex c = new Vertex(x, V.size());
		V.add(c); // a ameliorer
		return c; 
	}

	public Edge insertEdge(Vertex v, Vertex w, int x) {
		Edge c = new Edge(v, w, x, E.size());
		E.add(c); // a ameliorer
		return c; 
	}

	public void removeVertex(Vertex v) {
		LinkedList<Edge> i = v.getI(); 
		while (!i.isEmpty())
			E.remove(i.getFirst()); // on supprime toutes les arretes incidentes a ce noeud
		V.remove(v.getPosition()); // a ameliorer
	}

	public void removeEdge(Edge e) {
		E.remove(e.getPosition()); // a ameliorer
		// Enlever les references des I vers E ?
	}

	/* Autres methodes interessantes : */

	/* Si V contient un element dont la valeur est X, on le retourne. 
	   Sinon, on retourne null. */
	public Vertex contains(int x) {
		if (V.isEmpty())
			return null; 
		int i = 0; 
		int size = V.size();
		Vertex c = null; 
		while (i < size) {
			c = V.get(i); 
			if (c.getCity() == x)
				return c;
			i++; 
		}
		return null;
	}

	/* Renvoie le premier noeud de la ligne */
	public int firstNode(String line) {
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

	public int secondNode(String line) {
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
	public int firstEdge(String line) {
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

	public boolean isConnected() {
		DFS(vertices().getFirst());
		boolean result = true; 
		for (Vertex v : vertices()) {
			if (v.visited)
				v.visited = false; 
			else
				result = false; 
		}
		return result; 
	}

	public void DFS(Vertex v) {
		v.visited = true;
		for (Edge e : v.getI()) {
			Vertex opposite = opposite(v, e);
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
		for (Edge e : edges())
			sum += e.getWeight(); 
		return sum; 
	}
}

/**
 * Classe representant un noeud du graphe
 * 
 * @author Simon Hardy
 */
class Vertex {

	private int city; 
	private LinkedList<Edge> I;
	private int position; // position dans V (devrait etre une reference !)
	public boolean visited; // for DFS

	public Vertex(int x) {
		city = x;
		I = new LinkedList<Edge>(); 
		position = 0;
		visited = false;
	}

	public Vertex(int x, int pos) {
		city = x;
		I = new LinkedList<Edge>();
		position = pos;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	public LinkedList<Edge> getI() {
		return I;
	}
	public void setI(LinkedList<Edge> i) {
		I = i;
	}
	public void addToI(Edge e) {
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
class Edge implements Comparable<Edge> {

	private int weight; 
	private Vertex src; 
	private Vertex dst; 
	private LinkedList<Edge> srcEdges; // reference ! (redondant ?)
	private LinkedList<Edge> dstEdges;  // reference ! (redondant ?)
	private int position; // position dans V (devrait etre une reference !)

	public Edge(Vertex src, Vertex dst, int x) {
		weight = x; 
		this.src = src ; 
		this.dst = dst; 
		srcEdges = src.getI();
		dstEdges = dst.getI();
		position = 0;
	}

	public Edge(Vertex src, Vertex dst, int x, int pos) {
		weight = x; 
		this.src = src ; 
		this.dst = dst; 
		srcEdges = src.getI();
		dstEdges = dst.getI();
		position = pos;
	}

	public int compareTo(Edge e)
	{
		return this.getWeight() - e.getWeight();
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Vertex getSrc() {
		return src;
	}

	public void setSrc(Vertex src) {
		this.src = src;
	}

	public Vertex getDst() {
		return dst;
	}

	public void setDst(Vertex dst) {
		this.dst = dst;
	}

	public LinkedList<Edge> getSrcEdges() {
		return srcEdges;
	}

	public void setSrcEdges(LinkedList<Edge> srcEdges) {
		this.srcEdges = srcEdges;
	}

	public LinkedList<Edge> getDstEdges() {
		return dstEdges;
	}

	public void setDstEdges(LinkedList<Edge> dstEdges) {
		this.dstEdges = dstEdges;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}


}
