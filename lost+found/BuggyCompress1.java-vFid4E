import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.LinkedHashMap;
import java.io.*;
import java.util.Stack;
import java.lang.Comparable;

/* Buggy version of the solution of Simon Hardy for the compression (mission 5) */

public class Compress {

	public static final boolean BIT_1 = true;
	public static final boolean BIT_0 = false;
	private static int treeLength; // taille de l'arbre en nombre de chars (16 bits par char)
	private static int textLength = 0; // taille totale du texte à compresser (nécessaire pour virer les 0 à la fin)

	/**
	 * MAIN
	 */
	public static void main(String [] args) {
		compress(args[0], args[1]);
	}

	/*
	 * FUNCTIONS
	 */

	/**
	 * Compresse un fichier compressé
	 * @param inputFilePath Fichier d'entrée, ce fichier sera compressé après l'opération
	 * @param outputFilePath Fichier de sortie, ce fichier correspond au fichier compressé
	 */
	public static void compress(String inputFilePath, String outputFilePath) {
		Chrono.start();

		ParsedFile file = new ParsedFile(inputFilePath);

		LinkedHashMap<Character,Integer> freqMap = computeFrequency(file);

		BinaryTree freqTree = computeTree(freqMap);

		String compressedFile = do_compression(freqTree, file);
		String outputFile = catTree(freqTree, compressedFile);
		writeFile(outputFilePath, outputFile); // Écrit la taille de l'arbre en binaire sur 32 bits, l'arbre en binaire (char par char) puis les bits issus de la compression du fichier d'entr�e.

		Chrono.stop();

		// Calcul du taux de compression
		long file1 = ParsedFile.sizeFile(inputFilePath);
		long file2 = ParsedFile.sizeFile(outputFilePath);
		//System.out.println("");
		//System.out.println("Size fichier entrée: " + file1);
		//System.out.println("Size fichier compressé: " + file2);
		//System.out.println("Taux de compression: " + ((double)file1 / (double)file2));

	}

	/**
	 * Calcul la fréquence de chaque caractère
	 * @param file Fichier d'entrée, le fichier à traiter
	 * @return HashMap contenant la fréquence de chaque caractère de la chaîne de caractère d'entrée (fichier)
	 */
	public static LinkedHashMap<Character, Integer> computeFrequency(ParsedFile file) {
		LinkedHashMap<Character,Integer> map = new LinkedHashMap<Character,Integer>();
		// Table utilis�e pour r�cupere les fr�quences.
		ArrayList<String> content = file.getContent();
		textLength=0; // WITHOUT THIS, IT WOULD FAIL WITH THE SECOND COMPRESSION...
		for(String s : content) {
			textLength++; // pour compter les passages � la ligne
			for(int i = 0; i < s.length() ;i++) {
				textLength++; // pour compter le nombre de caract�res sur une m�me ligne
				char c = s.charAt(i);

				if(map.get(c) != null)
					map.put(c, map.remove(c)+1);
				else
					map.put(c,1);
			}

			if(map.get('\n') != null)
				map.put('\n', map.remove('\n')+1);
			else
				map.put('\n',1);
		}

		return map;
	}

	/**
	 * Création de l'arbre nécessaire pour la compression (Arbre de Huffman)
	 * @param map Table de fréquence des caractères
	 * @return Arbre de Huffman
	 */
	public static BinaryTree computeTree(LinkedHashMap<Character, Integer> map) {
		// File de priorit� utilis�e dans la cr�ation de l'arbre pour la compression de Huffman
		PriorityQueue<SimpleEntry<Integer, BinaryTree>> queue = new PriorityQueue<SimpleEntry<Integer,BinaryTree>>();
		Iterator<Character> it = map.keySet().iterator();

		while (it.hasNext()) {
			// Parcours de chaque �l�ment de la table de fr�quence (en cours de construction, donc de la map)
			char c = it.next();
			int key = map.get(c);

			SimpleEntry<Integer,BinaryTree> e = new SimpleEntry<Integer,BinaryTree>(map.get(c),new BinaryTree(key,c));
			// Ajout dans la file de priorit� la feuille contenant le caract�re avec sa fr�quence comme cl�
			queue.add(e);

			it.remove();
		}

		while(queue.size() > 1)	{
			// On vas vider la file de priorit� pour cr�er l'arbre de codage de la compression de Huffman
			SimpleEntry<Integer, BinaryTree> e1 = queue.poll();
			SimpleEntry<Integer, BinaryTree> e2 = queue.poll();
			SimpleEntry<Integer, BinaryTree> eOut =
					new SimpleEntry<Integer,BinaryTree>
					(e1.getKey() + e2.getKey(),
							new BinaryTree(
									e1.getKey()+e2.getKey(),
									e2.getValue(),
									e1.getValue()));
			queue.add(eOut);
		}
		if (queue.isEmpty()) return new BinaryTree(0, '0');
		return queue.remove().getValue();
	}

	/**
	 * Effectue la compression du fichier
	 * @param bt Arbre de Huffman
	 * @param file Fichier d'entrée, le fichier à compresser
	 * @return Résultat de la compression
	 */
	public static String do_compression(BinaryTree bt, ParsedFile file)	{
		String result = "";
		ArrayList<String> content = file.getContent();

		for(String line:content) {
			for (int i = 0; i < line.length(); i++)
				result += getCode(bt,line.charAt(i));

			result += getCode(bt,'\n');
		}

		return result;
	}

	/**
	 * Permet de récupérer le code de Huffman (cf. p576 du livre de référence Data Structure & Algorithms in Java)
	 * @param bt Abre
	 * @param c Caractère dont nous cherchons le code
	 * @return retourne le code recherché
	 */
	public static String getCode(BinaryTree bt, Character c) {
		String result = "";

		if(bt.isExternal())
			return "";
		else if (bt.getLeft().getValue() == c)
			return "0";
		else if (bt.getRight().getValue() == c)
			return "1";
		else {
			String leftCode ;
			String rightCode ;

			if(!(leftCode=getCode(bt.getLeft(),c)).equals(""))
				return "0" + leftCode;
			else if(!(rightCode=getCode(bt.getRight(),c)).equals(""))
				return "1" + rightCode;
		}

		return result;
	}

	/**
	 * Affiche le contenu de l'arbre binaire
	 * @param freqTree Arbre
	 * @param s Chaîne de caractère supplémentaire
	 * @return Contenu de l'arbre binaire
	 */
	public static String catTree(BinaryTree freqTree, String s) {
		String result = freqTree.toString();
		treeLength = result.length();

		return result + s;
	}

	/**
	 * Écrit le fichier de sortie (plus spécifique que la fonction de ParsedFile)
	 * @param outputFilePath Chemin relatif/absolu du fichier
	 * @param toOutput Contenu du fichier
	 */
	public static void writeFile(String outputFilePath, String toOutput) {
		try {
			// Ouverture du flux de sortie
			OutputBitStream out = new OutputBitStream(outputFilePath);
			out.write(textLength); // 1er �l�ment du fichier compress� : la taille du texte (sur les 32 premiers bits)
			out.write(treeLength); // 2�me �l�ment du fichier compress� : la taille de l'arbre (sur les 32 bits suivants)
			for (int i = 0 ; i < toOutput.length() ; i++) {
				if (i < treeLength)
					out.write(toOutput.charAt(i)); // on �crit l'arbre en binaire
				else if (toOutput.charAt(i) == '1')
					out.write(BIT_1);
				else if (toOutput.charAt(i) == '0')
					out.write(BIT_1); // BUG HERE, ALWAYS 1
			}

			// Fermeture du flux de sortie
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception, arrêt du programme. ");
		}
	}
}

class BinaryTree
{

	private BinaryTree left;
	private BinaryTree right;
	private int key;
	private char value;

	/**
	 * CONSTRUCTEURS
	 */

	/* CONSTRUCTEUR
	 * Construit un arbre élémentaire correspondant à l'expression vide ""
	 */
	public BinaryTree(int k, char o) {
		key = k;
		value = o;
	}

	public BinaryTree(int k, BinaryTree l, BinaryTree r) {
		key = k;
		left = l;
		right = r;
	}
	public BinaryTree(String s)	{
		Stack<BinaryTree> stack = new Stack<BinaryTree>();
		int i = 0;
		while(i < s.length()) {

			if (s.charAt(i) == '[' || s.charAt(i) == '"' || s.charAt(i) == '>' || s.charAt(i) == ',' || s.charAt(i) == '|')
				i++; // on skip le caractere en question

			else if (s.charAt(i) == '<') {
				String temp = ""; // contiendra le nombre representant la cle (necessaire car possibilite de plusieurs chiffres)
				i++; // on skip le <
				while (s.charAt(i) != ',') {
					temp += s.charAt(i);
					i++; // on skip le chiffre en question
				}
				i+=2; // on skippe la , et le " ouvrant
				BinaryTree T = new BinaryTree(Integer.parseInt(temp), s.charAt(i));
				i+=3; // on skippe la lettre, le " fermant et le >
				stack.push(T);
			}
			else if (isNumerical(s.charAt(i))) {
				String temp = ""; // contiendra le nombre representant la cle (necessaire car possibilite de plusieurs chiffres)
				while (s.charAt(i) != '|') {
					temp += s.charAt(i);
					i++; // on skip le chiffre en question
				}
				BinaryTree T = new BinaryTree(Integer.parseInt(temp), '?'); // ? signifie que c'est un noeud interne
				stack.push(T);
				i++; // on skip le '|'
			}
			else if (s.charAt(i) == ']') {
				BinaryTree T2 = stack.pop();
				BinaryTree T = stack.pop();
				int val = T.getKey();
				BinaryTree T1 = stack.pop();
				stack.push(new BinaryTree(val, T1, T2));
				i++;
			}
			else
				i++; // caractere indefini, on skip simplement (exemple : le null au debut)
		}
		BinaryTree T = stack.pop();
		left = T.getLeft();
		right = T.getRight();
		key = T.getKey();
		value = T.getValue();
	}

	public boolean isExternal()	{
		return !hasLeft() && !hasRight();
	}

	public boolean hasLeft() {
		try {
			return (this.left != null);
		} catch(NullPointerException e) {
			return false;
		}
	}

	public BinaryTree getLeft() {
		return left;
	}

	public void setLeft(BinaryTree left) {
		this.left = left;
	}

	public boolean hasRight() {
		try {
			return (this.right != null);
		} catch(NullPointerException e) {
			return false;
		}
	}

	public BinaryTree getRight() {
		return right;
	}

	public void setRight(BinaryTree right) {
		this.right = right;
	}

	public char getValue() {
		return value;
	}

	public void setValue(char value) {
		this.value = value;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String toString() {
		if(!isExternal())
			return "["+left.toString()+"|"+key+"|"+right.toString()+"]";
		else
			return "<"+key+","+"\""+value+"\""+">";
	}

	public boolean isNumerical(char c) // deja implemente par Java ??
	{
		return (c == '0' ||
				c == '1' ||
				c == '2' ||
				c == '3' ||
				c == '4' ||
				c == '5' ||
				c == '6' ||
				c == '7' ||
				c == '8' ||
				c == '9');
	}
}

class SimpleEntry<K, V> extends
		AbstractMap.SimpleEntry<K, V> implements Comparable{

	public SimpleEntry(K arg0, V arg1) {
		super(arg0, arg1);
	}

	public int compareTo(SimpleEntry<K,V> e)
	{
		return this.getKey().toString().compareTo(e.getKey().toString());
	}

	@Override
	@SuppressWarnings("unchecked")
	public int compareTo(Object o) {
		if(o instanceof SimpleEntry<?,?>)
		{
			SimpleEntry<K,V> e = (SimpleEntry<K,V>) o;
			return this.getKey().toString().compareTo(e.getKey().toString());
		}
		else return 0;
	}
	
	public String toString()
	{
		return "<"+this.getKey().toString()+","+this.getValue().toString()+">";
	}
}

/**
 * Classe permettant de lire n'importe quel fichier ASCII, quelque soit son format
 * @author Lionel Lebon
 */
class ParsedFile {
	
	private ArrayList<String> content;
	
	/*
	 * CONSTRUCTEUR
	 */
	public ParsedFile(String path) {
		content = readFile(path);
	}
	
	/*
	 * FUNCTIONS
	 */
	public ArrayList<String> getContent() { return this.content; }
	
	/**
	 * Lecture du fichier
	 * @param path Chemin absolu/relatif du fichier à parser
	 * @return ArrayList
	 */
	private ArrayList<String> readFile(String path) {
		BufferedReader input = null;
		String line  = null;
		
		// Tableau de String (on veut traiter indépendamment chaque commande)
		ArrayList<String> content = new ArrayList<String>();
		
		try{
			input = new BufferedReader(new FileReader(path));
			String s = input.readLine();
			
			while(s != null) {
				content.add(s);
				s = input.readLine();
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(input != null)
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		return content;
	}
	
	/**
	 * Écrire un fichier ASCII
	 * @param path Chemin relatif/absolu du nouveau fichier
	 * @param content Contenu à écrire dans le fichier
	 */
	public static void writeFile(String path, String content1) {
		writeFile(path, content1, false);
	}
	
	/**
	 * Écrire un fichier ASCII
	 * @param path Chemin relatif/absolu du nouveau fichier
	 * @param append si true, alors on écrit à la fin du fichier
	 * @param content Contenu à écrire dans le fichier
	 */
	public static void writeFile(String path, String content1, boolean append) {
		PrintWriter output = null;
		
		try {
			output = new PrintWriter(new FileWriter(path, append));
			output.print(content1);
		} catch(IOException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if(output != null) {
				try {
					output.close();
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Supprime un fichier
	 * @param path Chemin absolu/relatif du fichier à supprimer
	 */
	public static void deleteFile(String path) {
		try {
			File file = new File(path);
			file.delete();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Taille d'un fichier
	 * @param path Chemin absolu/relatif du fichier
	 */
	public static long sizeFile(String path) {
		File file = new File(path);
		return file.length();
	}
}


/**
 * Classe permettant de chronométrer le temps d'exécution d'un code.
 * @author Lionel Lebon
 */
class Chrono {
	static long m_start;
	static long m_stop;

	/**
	 * Lance le chronomètre
	 */
	public static void start() {
		m_start = System.currentTimeMillis();
	}

	/**
	 * Calcul le temps d'exécution et l'affiche dans la console (fait appel à stop(""))
	 */
	public static void stop() {
		stop("");
	}

	/**
	 * Calcul le temps d'exécution et l'affiche dans la console
	 * @param content String permettant de différencier ce temps d'exécution par rapport à d'autres
	 */
	public static void stop(String content) {
		m_stop = System.currentTimeMillis();
		//System.out.println("Temps d'exécution " + content + ": " + (m_stop - m_start) + " ms");
	}
}
