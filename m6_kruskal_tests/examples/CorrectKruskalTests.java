import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class KruskalTests {

    @Test
    public void firstTest() {
        try {
            applyTest("i.txt", "o.txt", 3, 3);

            applyTest("i2.txt", "o2.txt", 4, 3);
            applyTest("i4.txt", "o4.txt", 4, 12);

            applyTest("i3.txt", "o3.txt", 100, 99);
        } catch (Exception e) {
            fail("Exception occured : " + e);
        }
    }


    @Test
    public void secondTest() {
        // TODO...
    }

    public static void applyTest(String in, String out, int numberOfNodes, int optimalCost) {
        Kruskal.main(new String[] {in, out}); // apply the algorithm under test

        int[] result = readSol(out, numberOfNodes); // get the solution in 'result'

        List<int[]> baseEdges = read(in);
        List<int[]> finalEdges = read(out);

        for (int[] edge : finalEdges)
        {
            boolean contains = false;
            for (int[] baseEdge : baseEdges) {
                if (Arrays.equals(edge, baseEdge)) {
                    contains = true;
                }
            }
            if (!contains) {
                fail("TRIIIIIICHE !");
            }
        }

        assertEquals(result[0], numberOfNodes); // all the nodes are involved (it's 'spanning')
        assertEquals(result[1], numberOfNodes-1); // number of edges = number of nodes - 1 (it's a 'tree')
        assertTrue(result[2] <= optimalCost); // the cost is optimal (it's a 'minimum' spanning tree)

        // TODO Assert that the graph is connected using Union/Find structures...
    }

    public static ArrayList<int[]> read(String path) {

        ArrayList<int[]> edges = new ArrayList<int[]>();
        try {
            File f = new File (path);
            Scanner s = new Scanner(f);

            try {
                while (s.hasNextInt())
                {
                    int v1 = s.nextInt();
                    int v2 = s.nextInt();
                    int cost = s.nextInt();
                    edges.add(new int[]{v1, v2, cost});
                }

                s.close();
            } catch (NoSuchElementException e)
            {
                fail("Error occured while reading the file  : " + e.getMessage());
            }
        } catch (FileNotFoundException exception) {
            fail("File not found");
        }

        return edges;

    }

    public static int[] readSol(String path, int numberOfNodes) {
        Set<Integer> nodes = new HashSet<Integer>();
        int numberOfEdges = 0;
        int totalCost = 0; // cost found by the student
        int cheat = 0; // incremented if fake edge
        ArrayList<int[]> edges = new ArrayList<int[]>();
        try {
            File f = new File (path);
            Scanner s = new Scanner(f);

            try {
                while (s.hasNextInt())
                {
                    int v1 = s.nextInt();
                    int v2 = s.nextInt();
                    int cost = s.nextInt();
                    numberOfEdges += 1;
                    totalCost += cost;
                    nodes.add(v1);
                    nodes.add(v2);
                    edges.add(new int[]{v1, v2});
                }

                s.close();
            } catch (NoSuchElementException e)
            {
                fail("Error occured while reading the file  : " + e.getMessage());
            }
        } catch (FileNotFoundException exception) {
            fail("File not found");
        }
        assertTrue(isConnected(nodes, edges));

        return new int[] {nodes.size(), numberOfEdges, totalCost, cheat};
    }

    public static boolean isConnected(Set<Integer> nodes, List<int[]> edges)
    {
        int[] id = new int[nodes.size()];
        for (int i = 0;i < id.length;i++)
        {
            id[i] = i;
        }

        for (int[] edge : edges)
        {
            id = union(id, edge[0], edge[1]);
        }

        int onlyComponent = id[0];
        for (int i = 1;i < id.length;i++)
        {
            if (onlyComponent != id[i])
                return false;
        }
        return true;
    }

    public static int find(int[] id, int node)
    {
        return id[node];
    }

    public static int[] union(int[] id, int p, int q)
    {
        int pid = find(id, p);
        int qid = find(id, q);

        if (pid == qid) {
            assertTrue(false);
        }

        for (int i = 0;i < id.length;i++) {
            if (id[i] == pid)
                id[i] = qid;
        }

        return id;
    }
}
