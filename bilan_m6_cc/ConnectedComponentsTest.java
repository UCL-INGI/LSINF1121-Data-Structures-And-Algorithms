

import java.io.IOException;

import java.util.Random;

public class ConnectedComponentsTest {


    private static class WeightedQuickUnionUF {
        private int[] parent;   // parent[i] = parent of i
        private int[] size;     // size[i] = number of sites in subtree rooted at i
        private int count;      // number of components

        public WeightedQuickUnionUF(int n) {
            count = n;
            parent = new int[n];
            size = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }


        public int count() {
            return count;
        }

        public int find(int p) {
            validate(p);
            while (p != parent[p])
                p = parent[p];
            return p;
        }

        // validate that p is a valid index
        private void validate(int p) {
            int n = parent.length;
            if (p < 0 || p >= n) {
                throw new IndexOutOfBoundsException("index " + p + " is not between 0 and " + (n - 1));
            }
        }

        public boolean connected(int p, int q) {
            return find(p) == find(q);
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;

            // make smaller root point to larger one
            if (size[rootP] < size[rootQ]) {
                parent[rootP] = rootQ;
                size[rootQ] += size[rootP];
            } else {
                parent[rootQ] = rootP;
                size[rootP] += size[rootQ];
            }
            count--;
        }
    }

    public boolean testRandomGraphOk(int n, int e) {
        Graph g = new Graph(n);
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
        Random r = new Random(6);
        for (int i = 0; i < e; i++) {
            int orig = r.nextInt(n);
            int dest = r.nextInt(n);
            uf.union(orig,dest);
            g.addEdge(orig,dest);
        }
        return (uf.count() == ConnectedComponents.numberOfConnectedComponents(g));
    }

    public boolean cycleGraphOk() {
        int n = 1000;
        Graph g = new Graph(n);
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(n);
        for (int i = 0; i < n; i++) {
            uf.union(i,(i+1)%n);
            g.addEdge(i,(i+1)%n);
        }
        return (uf.count() == ConnectedComponents.numberOfConnectedComponents(g));
    }


    public boolean complexityOk() {
        int n = 10000;
        Graph g = new Graph(n);
        for (int i = 0; i < n; i++) {
            g.addEdge(i,(i+1)%n);
        }
        boolean timeOk = new TimeLimitedCodeBlock() {
            @Override
            public void codeBlock() {
                ConnectedComponents.numberOfConnectedComponents(g);
            }
        }.run(300);
        if (!timeOk) return false;
        else return true;
    }

    public void feedback(String message) {
        System.out.println(message);
        try {
            Runtime.getRuntime().exec(new String[]{"feedback-msg", "-ae", "-m", "\n"+message+"\n"}).waitFor();
        }
        catch (IOException e) {e.printStackTrace(); }
        catch (InterruptedException e) {e.printStackTrace(); }
    }


    public void grade() {
        try{
            int score = 0;


            if (testRandomGraphOk(600,120))
                score += 10;
            else {
                feedback("number of connected components not ok:-10");
            }

            if (testRandomGraphOk(220,7))
                score += 10;
            else {
                feedback("number of connected components not ok:-10");
            }

            if (testRandomGraphOk(105,3))
                score += 10;
            else {
                feedback("number of connected components on graph without edge not ok:-10");
            }

            if (testRandomGraphOk(0,0))
                score += 10;
            else {
                feedback("number of connected components on empty graph not ok:-10");
            }

            if (testRandomGraphOk(10,2*10))
                score += 10;
            else {
                feedback("number of connected components on dense graph not ok:-10");
            }

            if (cycleGraphOk())
                score += 10;
            else {
                feedback("number of connected components on cycle not ok:-10");
            }


            if (score < 60) {
                feedback( "while not everything is correct, time complexity verification skipped:-40");
            } else {
                if (complexityOk())
                    score += 40;
                else {
                    feedback("time complexity too high:-40");
                }
            }

            System.out.println("%score:" + score);
            try {
                Runtime.getRuntime().exec("feedback-grade "+score).waitFor();

                if (score == 100) {
                    Runtime.getRuntime().exec("feedback-result success").waitFor();
                    feedback("congratulation");
                }
                else {
                    feedback("not yet there ...");
                    Runtime.getRuntime().exec("feedback-result failed").waitFor();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch(InterruptedException e) {}
    }


    public static void main(String[] args) {
        new ConnectedComponentsTest().grade();
        System.exit(0);

    }

}

