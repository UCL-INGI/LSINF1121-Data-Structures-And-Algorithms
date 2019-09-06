
public class Ceil {
	/**
	 * Find in the tree the smallest element greater than or equal to value
	 * (so either the element itself or the element located directly after it
	 * in order of magnitude). If such an element does not exist,
	 * it must return null.
	 *
	 * Inserez votre reponse ici
	 */
	public static Integer ceil(Node root, int value) {
		if (root == null) return null;
		if (value == root.getValue()) return value;

		//If root's value is greater than value, either left subtree or root has the ceil value
		if (value < root.getValue()) {
			Integer c = ceil(root.getLeft(), value);
			if (c != null) return c;
			else return root.getValue();
		}

		// If root's value is smaller, ceil must be in right subtree
		return ceil(root.getRight(), value);
    }
}
