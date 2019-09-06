public class Node {
	private Node left, right;
	private int value;

	public Node(int value) {
		this.left = null;
		this.right = null;
		this.value = value;
	}

    public Node() {
    }

    /**
      * @return la valeur contenue dans ce noeud
      */
    public int getValue() {
    	return value;
    }

    /**
     * @return Le noeud situe a gauche (dont la valeur est < que la valeur actuelle) s'il existe, null sinon
     */
    public Node getLeft() {
    	return left;
    }

    /**
      * @return Le noeud situe a droite (dont la valeur est > que la valeur actuelle) s'il existe, null sinon
      */
    public Node getRight() {
    	return right;
    }

    /**
     * Ajoute une nouvelle valeur newVal dans le BST
     */
    public void add(int newVal) {
    	if(newVal < value && left == null) left = new Node(newVal);
        else if(newVal < value) left.add(newVal);
        else if(newVal > value && right == null) right = new Node(newVal);
        else if(newVal > value) right.add(newVal);
    }
}
