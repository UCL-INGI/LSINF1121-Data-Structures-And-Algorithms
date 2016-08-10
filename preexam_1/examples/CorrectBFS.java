// 1)
import java.util.LinkedList;

// 2)
for (int s : sources) {
    for (int i = 0;i < marked.length;i++) {
    	marked[i] = false;
	}

    distTo[s] = 0;
    marked[s] = true;
    LinkedList<Integer> q = new LinkedList<Integer>();
    q.add(s);

    while (!q.isEmpty()) {
        int v = q.remove();
        for (int w : G.adj(v)) {
            if (!marked[w] && distTo[v] + 1 < distTo[w]) {
                marked[w] = true;
                distTo[w] = distTo[v] + 1;
                q.add(w);
            }
        }
    }
}

// 3)
return distTo[v] != INFINITY;

// 4)
return distTo[v];
