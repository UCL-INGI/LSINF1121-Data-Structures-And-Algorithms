package templates;

@@import@@

public class DepthFirstPaths {
    private boolean[] marked; // marked[v] = is there an s-v path?
    private int[] edgeTo;     // edgeTo[v] = last edge on s-v path
    private final int s;

    /**
     * Computes a path between s and every other vertex in graph G
     * @param G the graph
     * @param s the source vertex
     */
     public DepthFirstPaths(Graph G, int s) {
         this.s = s;
         edgeTo = new int[G.V()];
         marked = new boolean[G.V()];
         dfs(G, s);
     }

     // Depth first search from v
     private void dfs(Graph G, int v) {
         @       @question1@@
     }

     /**
      * Is there a path between the source s and vertex v?
      * @param v the vertex
      * @return true if there is a path, false otherwise
      */
     public boolean hasPathTo(int v) {
         @       @question2@@
     }

     /**
      * Returns a path between the source vertex s and vertex v, or
      * null if no such path
      * @param v the vertex
      * @return the sequence of vertices on a path between the source vertex
      *         s and vertex v, as an Iterable
      */
     public Iterable<Integer> pathTo(int v) {
         @       @question3@@
     }
}
