import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;

/**
 * Partial solution for the tests of Kruskal. Gets 5/6.
 * To get 6/6, one must detect that the new graph is not connected
 * (or, alternatively, that there is a cycle),
 * because the optimal cost, the number of nodes and the number of edges are preserved.
 * I didn't test this last BuggyKruskal so it might be interesting to check if it's ok (BuggyKruskal5.java).
 * @author Simon HARDY
 */
public class KruskalTests {


	@Test
        public void testSmallCities() {
		try {
			String in = "./cities_small.txt";
			String out = "./cities_small_sol.txt";
			int numberOfNodes = 50; // number of nodes of this instance
			int optimalCost = 20522; // optimal cost for this instance
			applyTest(in, out, numberOfNodes, optimalCost);
		} catch (Exception e) {
		    fail("Exception occured : " + e);
		}
	}

        @Test
        public void secondTest() {
            // TODO...
        }

	public static void applyTest(String in, String out, int numberOfNodes, int optimalCost) {
		int nNodes = 0;
		int nEdges = 0;
		int cost = 0;
		int cheat = 0;
		int connected = 0;
		Kruskal.main(new String[] {in, out}); // apply the algorithm under test

		int[] result = readSol(out, numberOfNodes); // get the solution in 'result'

		assertEquals(result[0], numberOfNodes); // all the nodes are involved (it's 'spanning')
		assertEquals(result[1], numberOfNodes-1); // number of edges = number of nodes - 1 (it's a 'tree')
		assertTrue(result[2] <= optimalCost); // the cost is optimal (it's a 'minimum' spanning tree)

		// Assert that the graph is connected using Union/Find structures...
	}

	public static int[] readSol(String path, int numberOfNodes) {
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
				fail("Error occured while reading the file  : " + exception.getMessage());
			}
		} catch (FileNotFoundException exception) {
			fail("File not found");
		}
		return new int[] {nodes.size(), numberOfEdges, totalCost, cheat};
	}

	/* Gets the first city of this line */
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


	/* Gets the second city of this line */
	public static int secondNode(String line) {
		String number = "";
		int i = 0;
		int count = 0;
		char c = line.charAt(i);
		while (c != '\t' || count == 0) {
			if (count == 1) number += c;
			i++;
			if (c == '\t') count++;
			c = line.charAt(i);
		}
		return Integer.parseInt(number);
	}

	/* Gets the edge weight of this line */
	public static int firstEdge(String line) {
		String number = "";
		int i = 0;
		int count = 0;
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
