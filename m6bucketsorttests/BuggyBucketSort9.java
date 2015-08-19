import java.util.ArrayList;

/* Buggy version of the first task of the mission 6 */
public class BucketSort {

	/*  
	 * @pre 'tab' contains only positive integers, 'digit' belongs to [0, 9]
	 * @post returns a new table containing the elements of 'tab' sorted in increasing order 
	 * on digit 'digit' (digit '0' for the unit, '1' for the decimal and so on). 
	 * The algorithm must be stable ! (ordering of elements with the same keys is preserved) */
	public static int[] sort(int[] tab, int digit) {
		if (tab.length >= 10) return tab; // BUG here
		int[] result = new int[tab.length];
		ArrayList<ArrayList<Integer>> B = new ArrayList<ArrayList<Integer>>(10);
		for (int i = 0 ; i < 10 ; i++)
			B.add(i, new ArrayList<Integer>());
		for (int i = 0 ; i < tab.length ; i++) {
			int number = tab[i]; 
			int dig = getDigit(number, digit); 
			B.get(dig).add(number);
		}
		int j = 0;
		for (int i = 0 ; i < 10 ; i++) {
			for (int el : B.get(i)) {
				result[j] = el; 
				j++; 
			}
		}
		return result; 
	}
	
	/* Get digit at position 'pos' out of 'number' */
	public static int getDigit(int number, int pos) {
		while (pos > 0) {
			number = number/10; 
			pos--; 
		}
		return number % 10;
	}
}
