import java.util.ArrayList;
import java.util.Arrays; 

/* Buggy version of the first task of the mission 6 */
public class BucketSort {

	/*  
	 * @pre 'tab' contains only positive integers, 'digit' belongs to [0, 9]
	 * @post returns a new table containing the elements of 'tab' sorted in increasing order 
	 * on digit 'digit' (digit '0' for the unit, '1' for the decimal and so on). 
	 * The algorithm must be stable ! (ordering of elements with the same keys is preserved) */
	public static int[] sort(int[] tab, int digit) {
		Arrays.sort(tab); 
		return tab;
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
