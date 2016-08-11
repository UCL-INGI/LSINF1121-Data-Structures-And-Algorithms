// 1)
import java.util.ArrayList;
import java.util.Stack;

// 2)
for (int i = 0; i < marked.length;i++) {
    marked[i] = false;
    edgeTo[i] = false;
}

Stack<Integer> stack = new Stack<Integer>();
stack.push(v);
while (!stack.empty()) {
    int n = stack.peek(); // Here lies the error
    marked[n] = true;

    for (int adj : G.adj(n)) {
        if (!marked[adj]) {
            edgeTo[adj] = n;
            stack.push(adj);
        }
    }
}


// 3)
return marked[v];

// 4)
ArrayList<Integer> list = new ArrayList<>();
list.add(v);
while (v != s) {
    v = edgeTo[v];
    list.add(v);
}

return list;
