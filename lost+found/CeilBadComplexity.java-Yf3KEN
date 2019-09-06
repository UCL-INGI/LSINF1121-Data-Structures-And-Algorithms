
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

		java.util.List<Integer> list = new java.util.ArrayList<>();
		java.util.Stack<Node> s = new java.util.Stack<>();
		Node curr = root;

		// traverse the tree
		while (curr != null || s.size() > 0)
		{
			while (curr !=  null)
			{
				s.push(curr);
				curr = curr.getLeft();
			}

			curr = s.pop();

			list.add(curr.getValue());

			curr = curr.getRight();
		}

		if (value > list.get(list.size()-1)) return null;

		for (Integer a: list) {
			if (a == value) return value;
			if (a > value) return a;
		}

		return null;
    }
}
