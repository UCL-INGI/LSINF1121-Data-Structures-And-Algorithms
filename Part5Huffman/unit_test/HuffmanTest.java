import org.junit.Test;
import com.github.guillaumederval.javagrading.Grade;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.*;

import static org.junit.Assert.assertTrue;


public class HuffmanTest {

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
        int[] input = new int[size];
        for (int i = 0; i < size; i++) {
            input[i] = i + 1;
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

    @Test
    @Grade(value = 10)
    public void noCharactersDeleted() {
        int[] input = getRandomInstance(256, 60);

        Huffman.Node root = Huffman.buildTrie(input.length, input);
        LinkedList<Huffman.Node> leafNodes = new LinkedList<>();
        collectLeafNodes(leafNodes, root);
        assertTrue("Le nombre de feuille devrait être égal au nombre de caractères: -10%", input.length == leafNodes.size());

    }

    @Test
    @Grade(value = 10)
    public void leafNodesHaveTheCorrectFrequencies() {
        int[] input = getRandomInstance(256, 18);
        Huffman.Node root = Huffman.buildTrie(input.length, input);
        LinkedList<Huffman.Node> leafNodes = buildLinkedListOfNodes(root);
        for (Huffman.Node n : leafNodes) {
            if (n.freq != input[n.ch])
                assertTrue("Les feuilles n'ont pas toujours les fréquences adéquates: -10%", false);
        }

    }

    public LinkedList<Huffman.Node> buildLinkedListOfNodes(Huffman.Node root) {
        LinkedList<Huffman.Node> leafNodes = new LinkedList<>();
        if (root.isLeaf()) {
            leafNodes.add(root);
        } else {
            leafNodes.addAll(buildLinkedListOfNodes(root.left));
            leafNodes.addAll(buildLinkedListOfNodes(root.right));
        }

        return leafNodes;
    }

    @Test
    @Grade(value = 10)
    public void internalNodesHaveTheCorrectFrequencies() {
        int[] input = getRandomInstance(256, 23);
        Huffman.Node root = Huffman.buildTrie(input.length, input);
        assertTrue("Les noeuds internes n'ont pas toujours des fréquences valides: -10%", checkSumChildrenFreqs(root, input));
    }

    private boolean checkSumChildrenFreqs(Huffman.Node n, int[] input) {
        Stack<Huffman.Node> open = new Stack<Huffman.Node>();
        open.add(n);
        boolean hasleaf = input.length > 0;
        boolean flag = false;
        while (!open.isEmpty()) {
            Huffman.Node curr = open.pop();
            if (!curr.isLeaf()) {
                flag = true;
                if (curr.freq == 0 || curr.freq != curr.left.freq + curr.right.freq) return false;
                open.add(curr.right);
                open.add(curr.left);
            }
        }
        return flag == hasleaf;
    }

    @Test
    @Grade(value = 28)
    public void minimalWeightedExternalPathLength() {
        int[] input = getRandomInstance(257, 42);
        Huffman.Node root1 = Huffman.buildTrie(input.length, input);
        assertTrue("La taille pondérée du chemin interne n'est pas minimale, la topologie de votre arbre est incorrecte: -30%", weightedExternalPathLength(root1, 0) == 257226);
    }

    @Test(timeout=300)
    @Grade(value = 42)
    public void complexityOk() {
        int[] input = getRandomInstance(65536, 32);
        Huffman.Node root1 = Huffman.buildTrie(input.length, input);
    }


    private int weightedExternalPathLength(Huffman.Node n, int depth) {
        Stack<Map.Entry<Huffman.Node, Integer>> open = new Stack<Map.Entry<Huffman.Node, Integer>>();
        open.add(new AbstractMap.SimpleEntry<Huffman.Node, Integer>(n, 0));
        int res = 0;
        while (!open.isEmpty()) {
            Map.Entry<Huffman.Node, Integer> nodeDepth = open.pop();

            Huffman.Node curr = nodeDepth.getKey();
            if (curr.isLeaf()) res += curr.freq * nodeDepth.getValue();
            else {
                open.add(new AbstractMap.SimpleEntry<Huffman.Node, Integer>(curr.right, nodeDepth.getValue() + 1));
                open.add(new AbstractMap.SimpleEntry<Huffman.Node, Integer>(curr.left, nodeDepth.getValue() + 1));
            }
        }
        return res;
    }
}
