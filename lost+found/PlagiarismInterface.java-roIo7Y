import java.util.Map.Entry;
import java.util.Set;

/* A class implementing this interface should have a constructor taking two arguments : 
 * - The path to the folder where lies the corpus of documents, 
 * - The number 'w' of characters from which we consider that a sentence is plagiarized. */
public interface PlagiarismInterface {

	/* @pre : 'doc' is the path to a text file
	 * @post : searches for plagiarized sentences of at least 'w' characters between 'doc' and all text files in 'corpus'. 
	 * returns a set of (document name, position) for each plagiarized sentence found in a corpus file
	 * ('position' is the position of the first character of the first occurence of that sentence in the corpus files, starting at 0 and considering the alphabetical order of the files) */
	public Set<Entry<String, Integer>> detect(String doc);
	
}
