public class Word implements WordInterface {
	private String word; 
	private double spamProba; // probability that this words appears in spam messages
	private double hamProba; // probability that this words appears in ham messages
	private int occurences; // total number of occurences of that word
	
	public static double totalNumberOfSpams; // for the proba that any mail is a spam
	public static double totalNumberOfHams; // for the proba that any mail is a ham
	public final static int TOTAL_NUMBER_OF_SMS = 5574;
	
	public Word(String word) {
		this.word = word; 
		spamProba = 0;
		hamProba = 0;
		occurences = 0;
		totalNumberOfSpams = 0;
		totalNumberOfHams = 0;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public double getSpamProba() {
		return spamProba;
	}
	
	public void incSpamProba(double qty) {
		this.spamProba += qty;
	}

	public double getHamProba() {
		return hamProba;
	}
	
	public void incHamProba(double qty) {
		this.hamProba += qty;
	}
	
	public int getOccurences() {
		return occurences;
	}
	
	public void incOccurences() {
		occurences++;
	}

	public static double probaSpam() {
		return totalNumberOfSpams/(totalNumberOfSpams+totalNumberOfHams); 
	}
	
	public static double probaHam() {
		return totalNumberOfHams/(totalNumberOfSpams+totalNumberOfHams); 
	}

	public void normalize(int length) {
		this.spamProba = (double) this.spamProba / (TOTAL_NUMBER_OF_SMS*length); 
		this.hamProba = (double) this.hamProba / (TOTAL_NUMBER_OF_SMS*length); 
	}
	
	public void add(Word w) {
		this.spamProba += w.spamProba; 
		this.hamProba += w.hamProba; 
		this.occurences += w.occurences; 
	}
	
	public double bayesProba() {
		return (double) spamProba/(spamProba + hamProba);
	}
	
	public boolean myEquals(Word w) {
		return  this.getWord().equals(w.getWord()) &&
				this.getHamProba() == w.getHamProba() &&
				this.getSpamProba() == w.getSpamProba() &&
				this.getOccurences() == w.getOccurences();
	}
	
	public String toString() {
		String s = this.word; 
		s += " [ham(" + hamProba + "), spam(" + spamProba + "), occurences(" + occurences + "), bayes(" + bayesProba() + ")]";
		return s; 
	}
}
