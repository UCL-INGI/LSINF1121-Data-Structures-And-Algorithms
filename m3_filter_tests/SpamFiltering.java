/* The constructor takes a String as argument, representing the path to the input file */
public interface SpamFiltering {

	/* Returns a map (our Map interface, for example your custom type MyMap) containing mappings
	 * between the Strings appearing in each sms of the input file,
	 * and objects of type WordInterface (custom type too) containing
	 * correct informations about them (see below)
	 * Convention : use the regex "\\W+" to split the content of a message into words, and use toLowerCase() on what you read so that your map doesn't contain any upper case letter. */
	public Map<String, WordInterface> getWordsMap();

	/* Returns a HashSet (java.util.HashSet) containing the stop words listed below */
	public HashSet<String> getStopWords();

	/* Computes the probability that 'message' is a spam sms, using the naive Bayes formula (see pdf of the mission) */
	public double naiveBayes(String message);

	/* Returns true if 'message' is classified as a spam sms, false otherwise (a sms is considered as spam if the probability is strictly greater than 50%) */
	public boolean classify(String message);

}
