/* The constructor takes a String as argument, representing the corresponding English word */
public interface WordInterface {

    /* Returns the English word represented by this object */
    public String getWord();

    /* Returns the probability that a sms containing only this word once is a spam (see pdf of the mission for the formula) */
    public double getSpamProba();

    /* Returns the probability that a sms containing only this word once is a ham (not a spam) */
    public double getHamProba();

    /* Returns the number of occurences of this word in the concatenation of every sms in the input file */
    public int getOccurences();

    /* Optional */
    public String toString();
}
