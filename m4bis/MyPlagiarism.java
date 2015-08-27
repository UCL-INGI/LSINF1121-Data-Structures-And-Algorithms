import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MyPlagiarism implements PlagiarismInterface {
	
	private MapInterface<Map.Entry<String, Integer>> map; 
	private int w; // maximum length of the occurrences
	
	public MyPlagiarism(String folder, int w) {
		map = new MyHashMap<Map.Entry<String, Integer>>();
		this.w = w; 
		
		File dir = new File(folder);
		File[] listOfFiles = dir.listFiles();
		
		for (File file : listOfFiles) {
		    if (file.isFile()) {
		        String s = readInput(folder + "/" + file.getName());
		        processInput(s, file.getName());
		    }
		}
	}

	private static String readInput(String filename) {
		// Read the input file line by line  
		FileInputStream fis = null;
		BufferedReader reader = null;
		String s = ""; 
		
		try {
			fis = new FileInputStream(filename);
			reader = new BufferedReader(new InputStreamReader(fis));

			String line = reader.readLine();
			while(line != null){
				s += line;
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
		return s;
	}
	
	private void processInput(String file, String filename) {
		int lastHash = 0; 
		int lastChar = 0; 
		for (int i = 0 ; i < file.length()-w ; i++) {
			String key = file.substring(i, i+w);
			if (i == 0) 
				lastHash = map.hashCode(key); 
			else 
				lastHash = map.incrementalHashCode(key, lastHash, lastChar);
			lastChar = (int) key.charAt(0);
			map.put(key, new AbstractMap.SimpleEntry<>(filename, i), lastHash);
		}
	}
	
	public Set<Map.Entry<String, Integer>> detect(String doc) {
		int lastHash = 0; 
		int lastChar = 0; 
		String document = readInput(doc);
		Set<Map.Entry<String, Integer>> hits = new HashSet<Map.Entry<String, Integer>>();
		for (int i = 0 ; i < document.length()-w ; i++) {
			String key = document.substring(i, i+w);
			Map.Entry<String, Integer> entry; 
			if (i == 0) 
				lastHash = map.hashCode(key); 
			else 
				lastHash = map.incrementalHashCode(key, lastHash, lastChar);
			lastChar = (int) key.charAt(0);
			if ((entry = map.get(key, lastHash)) != null) {
				hits.add(new AbstractMap.SimpleEntry<>(entry.getKey(), entry.getValue()));
			}
		}
		return hits; 
	}
}