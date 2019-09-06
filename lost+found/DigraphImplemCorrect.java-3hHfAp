package templates;

import java.util.LinkedList;
import java.util.List;

public class DigraphImplemCorrect implements Digraph {

    private List<Integer> [] outEdges;
    private int nE = 0;

    public DigraphImplemCorrect(int V) {
         outEdges = new List[V];
         for (int i = 0; i < V; i++) {
             outEdges[i] = new LinkedList<>();
         }
    }

    /**
     * The number of vertices
     */
    public int V() {
        return outEdges.length;
    }

    /**
     * The number of edges
     */
    public int E() {
        return nE;
    }

    /**
     * Add the edge v->w
     */
    public void addEdge(int v, int w) {
        outEdges[v].add(w);
        nE ++;
    }

    /**
     * The nodes adjacent to edge v
     */
    public Iterable<Integer> adj(int v) {
        return outEdges[v];
    }

    /**
     * A copy of the digraph with all edges reversed
     */
    public Digraph reverse() {
        Digraph g = new DigraphImplemCorrect(V());
        for (int v = 0; v < outEdges.length; v++) {
            for (int w: adj(v)) {
                g.addEdge(w,v);
            }
        }
        return g;
    }


}
