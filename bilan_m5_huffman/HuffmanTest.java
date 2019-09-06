
import java.io.IOException;
import java.util.*;


public class HuffmanTest {
    public static class MyNodeComparator implements Comparator<Huffman.Node> {
        @Override
        public int compare(Huffman.Node o1, Huffman.Node o2) {
            return o1.freq - o2.freq;
        }
    }


    // build the Huffman trie given frequencies
    private Huffman.Node buildTrie1(int[] freq) {

        // initialze priority queue with singleton trees
        MinPQ<Huffman.Node> pq = new MinPQ<Huffman.Node>(0,new MyNodeComparator());


        for (int i = 0; i < freq.length; i++) {
            if (freq[i] > 0)
                pq.insert(new Huffman.Node(i, freq[i], null, null));
        }

        // special case in case there is only one character with a nonzero frequency
        if (pq.size() == 1) {
            if (freq[0] == 0) pq.insert(new Huffman.Node(0, 0, null, null));
            else pq.insert(new Huffman.Node(1, 0, null, null));
        }

        // merge two smallest trees
        while (pq.size() > 1) {
            Huffman.Node left  = pq.delMin();
            Huffman.Node right = pq.delMin();
            Huffman.Node parent = new Huffman.Node(0, left.freq + right.freq, left, right);
            pq.insert(parent);
        }
        return pq.delMin();
    }


    private void collectLeafNodes(List<Huffman.Node> nodes, Huffman.Node n) {

        Stack<Huffman.Node> open = new Stack<Huffman.Node>();
        open.add(n);

        while (!open.isEmpty()) {
            Huffman.Node curr = open.pop();
            if (curr.isLeaf()) nodes.add(curr);
            else {
                open.add(curr.right);
                open.add(curr.left);
            }
        }
    }

    private int[] getRandomInstance(int size, int seed) {
        int [] input = new int[size];
        for (int i = 0; i < size; i++) {
            input[i] = i+1;
        }
        Random rnd = new Random(seed);
        for (int i = 0; i < size; i++) {
            int idx1 = rnd.nextInt(size);
            int idx2 = rnd.nextInt(size);
            int tmp = input[idx1];
            input[idx1] = input[idx2];
            input[idx2] = tmp;
        }
        return input;
    }



    public boolean noCharactersDeleted() {
        int [] input = getRandomInstance(256, 0);

        Huffman.Node root = Huffman.buildTrie(input.length, input);
        LinkedList<Huffman.Node> leafNodes = new LinkedList<>();
        collectLeafNodes(leafNodes,root);
        return input.length == leafNodes.size();

    }


    public boolean leafNodesHaveTheCorrectFrequencies() {
        int [] input = getRandomInstance(256, 1);
        Huffman.Node root = Huffman.buildTrie(input.length, input);
        LinkedList<Huffman.Node> leafNodes = new LinkedList<>();
        for (Huffman.Node n: leafNodes) {
            if (n.freq != input[n.ch]) return false;
        }
        return true;
    }


    public boolean internalNodesHaveTheCorrectFrequencies() {
        int [] input = getRandomInstance(256, 2);
        Huffman.Node root = Huffman.buildTrie(input.length, input);
        return checkSumChildrenFreqs(root);
    }

    private boolean checkSumChildrenFreqs(Huffman.Node n) {
        Stack<Huffman.Node> open = new Stack<Huffman.Node>();
        open.add(n);
        while (!open.isEmpty()) {
            Huffman.Node curr = open.pop();
            if (!curr.isLeaf()) {
                if (curr.freq != curr.left.freq+curr.right.freq) return false;
                open.add(curr.right);
                open.add(curr.left);
            }
        }
        return true;
    }


    public boolean minimalWeightedExternalPathLength() {
        int [] input = getRandomInstance(256, 3);
        Huffman.Node root1 = Huffman.buildTrie(input.length, input);
        Huffman.Node root2= buildTrie1(input);
        return weightedExternalPathLength(root1,0) == weightedExternalPathLength(root2,0);
    }

    public boolean complexityOk() {
        int[] input = getRandomInstance(65536, 3);

        boolean timeOk = new TimeLimitedCodeBlock() {
            @Override
            public void codeBlock() {
                Huffman.Node root1 = Huffman.buildTrie(input.length, input);
            }
        }.run(300);
        if (!timeOk) return false;
        else return true;
    }


    private int weightedExternalPathLength(Huffman.Node n, int depth) {
        Stack<Map.Entry<Huffman.Node,Integer>> open = new Stack<Map.Entry<Huffman.Node,Integer>>();
        open.add(new AbstractMap.SimpleEntry<Huffman.Node,Integer>(n,0));
        int res = 0;
        while (!open.isEmpty()) {
            Map.Entry<Huffman.Node,Integer> nodeDepth = open.pop();

            Huffman.Node curr = nodeDepth.getKey();
            if (curr.isLeaf()) res += curr.freq*nodeDepth.getValue();
            else {
                open.add(new AbstractMap.SimpleEntry<Huffman.Node,Integer>(curr.right,nodeDepth.getValue()+1));
                open.add(new AbstractMap.SimpleEntry<Huffman.Node,Integer>(curr.left,nodeDepth.getValue()+1));
            }
        }
        return res;
    }

    public void feedback(String message) {
       System.out.println(message);
        try {
           Runtime.getRuntime().exec(new String[]{"feedback-msg", "-ae", "-m", "\n"+message+"\n"}).waitFor();
        } 
        catch (IOException e) {e.printStackTrace(); } 
        catch (InterruptedException e) {e.printStackTrace(); } 
    }

    public void testGrade() {
    try{
        int score = 0;

 
            if (noCharactersDeleted())
                score += 10;
            else {
                feedback("Le nombre de feuille devrait être égal au nombre de caractères: -10%");
            }

            if (leafNodesHaveTheCorrectFrequencies())
                score += 10;
            else {
               feedback("Les feuilles n'ont pas toujours les fréquences adéquates: -10%");
            }

            if (internalNodesHaveTheCorrectFrequencies())
                score += 10;
            else {
               feedback("Les noeuds internes n'ont pas toujours des fréquences valides: -10%");
            }

            if (minimalWeightedExternalPathLength())
                score += 30;
            else {
               feedback("La taille pondérée du chemin interne n'est pas minimale, la topologie de votre arbre est incorrecte: -30%");
            }
        
            if (score < 60) {
               feedback( "Votre programme étant incorrect, la vérification de la complexité temporelle n'est pas effectuée: -40%");     
            } else {
               if (complexityOk())
                   score += 40;
               else {
                  feedback("Complexité temporelle trop élevée: -40%");     
               }
            }

        System.out.println("%score:" + score);
        try {
            Runtime.getRuntime().exec("feedback-grade "+score).waitFor();

            if (score == 100) {
                Runtime.getRuntime().exec("feedback-result success").waitFor();
                feedback("Félicitations!");     
            }
            else {
                feedback("Il vous reste des problèmes à résoudre...");     
                Runtime.getRuntime().exec("feedback-result failed").waitFor();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

} catch(InterruptedException e) {}
    }


    public static void main(String[] args) {
        new HuffmanTest().testGrade();
        System.exit(0);
    }


}