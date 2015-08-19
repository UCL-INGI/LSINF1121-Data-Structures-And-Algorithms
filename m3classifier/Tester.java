import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;


public class Tester {
	
	private static ArrayList<String> hams;
	private static ArrayList<String> spams;
	
	public static void main(String[] args) {
		hams = new ArrayList<String>();
		spams = new ArrayList<String>();
		readInput("SMSSpamCollection");
		
		SpamFiltering spamFilter = new SpamFilter("SMSSpamCollection");
		SpamFilter2 spamFilter2 = new SpamFilter2("SMSSpamCollection");
		
		Map<String, WordInterface> wordsMap = spamFilter.getWordsMap(); 
		Map<String, Word2> wordsMap2 = spamFilter2.getWordsMap();
		
		/* Iterate on my map to compare the words */
		Iterator<java.util.Map.Entry<String, Word2>> it = wordsMap2.entrySet().iterator(); 
		boolean ok = true; 
		String key = null; 
		Word value = null; 
		while (it.hasNext() && ok) {
			java.util.Map.Entry<String, Word2> pair = it.next(); 
			if (!wordsMap.containsKey(pair.getKey())) {
				ok = false; 
				key = pair.getKey(); 
			}
			else if (!pair.getValue().myEquals(wordsMap.get(pair.getKey()))) {
				ok = false; 
				value = wordsMap.get(pair.getKey()); 
			}
		}
		
		/* Iterate on the student's map to check for words that should not be there */
		Iterator<java.util.Map.Entry<String, WordInterface>> newIt = wordsMap.entrySet().iterator(); 
		String wrongKey = null; 
		while (newIt.hasNext() && ok) {
			java.util.Map.Entry<String, WordInterface> pair = newIt.next(); 
			if (!wordsMap2.containsKey(pair.getKey())) {
				ok = false; 
				wrongKey = pair.getKey(); 
			}
		}
		
		/* Check the stop list of the student */
		HashSet<String> stopWords = spamFilter.getStopWords(); 
		HashSet<String> stopWords2 = spamFilter2.getStopWords(); 
		String unexpected = null; 
		String missing = null; 
		for (String s : stopWords2)
			if (!stopWords.contains(s)) {
				missing = s; 
				ok = false; 
				break;
			}
		for (String s : stopWords)
			if (!stopWords2.contains(s)) {
				unexpected = s; 
				ok = false; 
				break;
			}
		
		double BCR = BCR((SpamFilter) spamFilter);
		double BCR2 = BCR(spamFilter2); 
		if (BCR < BCR2)
			ok = false; 
		
		if (ok) System.out.println("OK : you got a BCR (Balanced Classification Rate) of " + BCR);
		else {
			if (key != null) System.out.println("ERROR : key '" + key + "' not found in your map. "); 
			if (value != null) System.out.println("ERROR : word '" + value + "' incorrect. ");
			if (wrongKey != null) System.out.println("ERROR : key '" + wrongKey + "' not expected but found in your map : maybe a stop word ?");
			if (BCR < BCR2) System.out.println("ERROR : your BCR (" + BCR + ") is too low"); 
			if (unexpected != null) System.out.println("Error : word '" + unexpected + "' not expected in your list of stop words"); 
			if (missing != null) System.out.println("Error : word '" + missing + "' was missing in your list of stop words"); 
		}
	}
	
	
	private static void readInput(String filename) {
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
				line = reader.readLine();
			}          

		} catch (FileNotFoundException e) {
			System.out.println("EXCEPTION : " + e);
		} catch (IOException e) {
			System.out.println("EXCEPTION : " + e);
		} finally {
			try {
				reader.close();
				fis.close();
			} catch (IOException e) {
				System.out.println("EXCEPTION " + e);
			} catch (NullPointerException e) {
				System.out.println("EXCEPTION " + e);
			} 
		}
	}
	
	private static double BCR(SpamFilter spamFilter) {
		int TP = 0; 
		int FP = 0; 
		int TN = 0; 
		int FN = 0;
		
		for (String sms : spams) {
			boolean isSpam = spamFilter.classify(sms);
			if (isSpam)
				TP++; 
			else
				FN++; 
		}
		for (String sms : hams) {
			boolean isSpam = spamFilter.classify(sms);
			if (isSpam)
				FP++;  
			else
				TN++; 
		}

		return (double) ((double) TP/(TP+FN) + (double) TN/(FP+TN))/2;
	}
	
	private static double BCR(SpamFilter2 spamFilter) {
		int TP = 0; 
		int FP = 0; 
		int TN = 0; 
		int FN = 0;
		
		for (String sms : spams) {
			boolean isSpam = spamFilter.classify(sms);
			if (isSpam)
				TP++; 
			else
				FN++; 
		}
		for (String sms : hams) {
			boolean isSpam = spamFilter.classify(sms);
			if (isSpam)
				FP++;  
			else
				TN++; 
		}

		return (double) ((double) TP/(TP+FN) + (double) TN/(FP+TN))/2;
	}

}
