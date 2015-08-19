import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

public class SpamFilter implements SpamFiltering {

	private Map<String, WordInterface> wordsMap;
	private HashSet<String> stopWords; 
	
	private ArrayList<String> hams;
	private ArrayList<String> spams;

	public SpamFilter(String filename) {
		wordsMap = new MyMap<String, WordInterface>();
		stopWords = new HashSet<String>();
		String[] stopList = {"a", "about", "above", "above", "across", "after", "afterwards", "again", "against", "all", "almost", "alone", "along", "already", "also","although","always","am","among", "amongst", "amoungst", "amount",  "an", "and", "another", "any","anyhow","anyone","anything","anyway", "anywhere", "are", "around", "as",  "at", "back","be","became", "because","become","becomes", "becoming", "been", "before", "beforehand", "behind", "being", "below", "beside", "besides", "between", "beyond", "bill", "both", "bottom","but", "by", "call", "can", "cannot", "cant", "co", "con", "could", "couldnt", "cry", "de", "describe", "detail", "do", "done", "down", "due", "during", "each", "eg", "eight", "either", "eleven","else", "elsewhere", "empty", "enough", "etc", "even", "ever", "every", "everyone", "everything", "everywhere", "except", "few", "fifteen", "fify", "fill", "find", "fire", "first", "five", "for", "former", "formerly", "forty", "found", "four", "from", "front", "full", "further", "get", "give", "go", "had", "has", "hasnt", "have", "he", "hence", "her", "here", "hereafter", "hereby", "herein", "hereupon", "hers", "herself", "him", "himself", "his", "how", "however", "hundred", "ie", "if", "in", "inc", "indeed", "interest", "into", "is", "it", "its", "itself", "keep", "last", "latter", "latterly", "least", "less", "ltd", "made", "many", "may", "me", "meanwhile", "might", "mill", "mine", "more", "moreover", "most", "mostly", "move", "much", "must", "my", "myself", "name", "namely", "neither", "never", "nevertheless", "next", "nine", "no", "nobody", "none", "noone", "nor", "not", "nothing", "now", "nowhere", "of", "off", "often", "on", "once", "one", "only", "onto", "or", "other", "others", "otherwise", "our", "ours", "ourselves", "out", "over", "own","part", "per", "perhaps", "please", "put", "rather", "re", "same", "see", "seem", "seemed", "seeming", "seems", "serious", "several", "she", "should", "show", "side", "since", "sincere", "six", "sixty", "so", "some", "somehow", "someone", "something", "sometime", "sometimes", "somewhere", "still", "such", "system", "take", "ten", "than", "that", "the", "their", "them", "themselves", "then", "thence", "there", "thereafter", "thereby", "therefore", "therein", "thereupon", "these", "they", "thickv", "thin", "third", "this", "those", "though", "three", "through", "throughout", "thru", "thus", "to", "together", "too", "top", "toward", "towards", "twelve", "twenty", "two", "un", "under", "until", "up", "upon", "us", "very", "via", "was", "we", "well", "were", "what", "whatever", "when", "whence", "whenever", "where", "whereafter", "whereas", "whereby", "wherein", "whereupon", "wherever", "whether", "which", "while", "whither", "who", "whoever", "whole", "whom", "whose", "why", "will", "with", "within", "without", "would", "yet", "you", "your", "yours", "yourself", "yourselves", "the"};
		for (String word : stopList) {
			stopWords.add(word);
		}
		hams = new ArrayList<String>();
		spams = new ArrayList<String>();
		readInput(filename);
	}
	
	public Map<String, WordInterface> getWordsMap() {
		return wordsMap;
	}

	public HashSet<String> getStopWords() {
		return stopWords;
	}

	private void readInput(String filename) {
		/* Read the input file line by line */  
		FileInputStream fis = null;
		BufferedReader reader = null;

		try {
			fis = new FileInputStream(filename);
			reader = new BufferedReader(new InputStreamReader(fis));

			String line = reader.readLine();
			while(line != null){
				line = line.toLowerCase();
				String[] split = line.split("\t");
				if (split[0].equals("ham"))
					hams.add(split[1]);
				else
					spams.add(split[1]);
				processMessage(split[0], split[1].split("\\W+")); 
				line = reader.readLine();
			}          

		} catch (FileNotFoundException e) {
			System.out.println(e);
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			try {
				reader.close();
				fis.close();
			} catch (IOException e) {
				System.out.println(e);
			} catch (NullPointerException e) {
				System.out.println(e);
			} 
		}
	}

	private void processMessage(String category, String[] words) {
		
		// Fill a temporary map with words of this sms
		MyMap<String, Word> current = new MyMap<String, Word>();
		int length = 0; // length of a word (AFTER having removed the stop words)
		for (String word : words) if (!stopWords.contains(word)) {
			Word w = current.get(word); 
			if (w == null)
				w = new Word(word); 
			if (category.equals("ham")) {
				w.incHamProba(1);
				Word.totalNumberOfHams++; 
			}
			else {
				w.incSpamProba(1);
				Word.totalNumberOfSpams++;
			}
			w.incOccurences();
			current.put(word, w); // insert or replace
			length++; 
		}
		
		// Copy everything in the new map, after normalization
		Iterator<java.util.Map.Entry<String, Word>> it = current.entrySet().iterator(); 
		while (it.hasNext()) {
			java.util.Map.Entry<String, Word> pair = it.next(); 
			pair.getValue().normalize(length); 
			if (wordsMap.containsKey(pair.getKey()))
				wordsMap.get(pair.getKey()).add(pair.getValue());
			else
				wordsMap.put(pair.getKey(), pair.getValue());
		}
	}
	
	public double naiveBayes(String sms) {
		String[] words = sms.split("\\W+"); 
		double pos = 0; 
		double neg = 0; 
		for (int i = 0 ; i < words.length ; i++) if (wordsMap.containsKey(words[i])){
			double proba = wordsMap.get(words[i]).bayesProba(); 
			pos += Math.log(proba);
			neg += Math.log(1-proba); 
		}
		double eta = neg-pos; 
		return (double) 1/(1+Math.exp(eta));
	}
	
	/* Takes the body of a sms as input and outputs true if it's a spam (false otherwise),
	 * acccording to Naive Bayes classifier (spam if probability > 0.5) */
	public boolean classify(String message) {
		return naiveBayes(message) > 0.5;
	}
}
