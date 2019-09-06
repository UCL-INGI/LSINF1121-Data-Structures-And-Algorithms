import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.LinkedHashMap;
import java.io.*;

/* Solution of Simon Hardy for the decompression (mission 5) */

public class Decompress {

	public static final boolean BIT_1 = true;
	public static final boolean BIT_0 = false;

	/**
	 * MAIN
	 */
	public static void main(String [] args) {
		decompress(args[0], args[1]);
	}

	/*
	 * FUNCTIONS
	 */

	/**
	 * Decompresse un fichier compresse
	 * @param inputFilePath Fichier d'entree, ce fichier sera decompresse apres l'operation
	 * @param outputFilePath Fichier de sortie, ce fichier correspond au fichier decompresse
	 */
	public static void decompress(String inputFilePath, String outputFilePath) {
		String tree = ""; 
		int textLength = 0; // a lire dans le fichier compresse, taille du texte avant compression (en chars)
		try {
			// Ouverture d'un flux d'entree
			InputBitStream in = new InputBitStream(inputFilePath);

			// Lecture de la taille de la table
			textLength = in.readInt();
			int tableLength = in.readInt();
			for (int i = 0 ; i < tableLength ; i++)
				tree+=in.readChar();
			//System.out.println(tree); // Attention : pb de null au debut
			BinaryTree freqTree = new BinaryTree(tree);
			//System.out.println(freqTree.toString());
			String output = ""; 
			try {
				// Decompression en parcourant l'arbre au fur et a mesure de la lecture du flux binaire
				BinaryTree parcours = freqTree;
				int i = 0;
				while(i < textLength) {
					boolean resu = in.readBoolean();
					if (resu == false)
						parcours = parcours.getLeft();
					else
						parcours = parcours.getRight();
					if (parcours.isExternal()) {
						output += parcours.getValue();
						parcours = freqTree;
						i++;
					}
				}
			}
			catch (IOException e) { // Exception lancee notamment en fin de fichier
				System.out.println("");
				in.close();
			}
			// System.out.println(output);
			ParsedFile.writeFile(outputFilePath, output);
		}
		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception, arret du programme. ");
		}
	}
}
