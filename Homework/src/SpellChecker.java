// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/13/2025
// Spell Checker
/* This Java program functions as a spell checker. It reads a dictionary of correctly spelled words and an input file
 containing words that need to be checked. It identifies misspelled words by comparing each word in the input file with the
 dictionary and outputs any mismatched words.
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class SpellChecker {

	static HashSet<String> dictonary; // HashSet to store dictionary words

	public static void main(String[] args) {
		// Define file paths for dictionary and input files
		String path = ""; // please add required path to files. For ex: /home/user/DataFiles/
		String dictFilename = path + "dictionary.txt"; // Path to dictionary file
		String inputFilename = path + "input.txt"; // Path to input file
		dictonary = new HashSet<>(); // Initialize the dictionary HashSet
		readDictonary(dictFilename); // Read and load the dictionary into the HashSet
		readInput(inputFilename); // Read the input file and check for misspelled words
	}

	// reads the dictionary file and store words in the HashSet
	private static void readDictonary(String dictFilename) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(dictFilename)))) {
			String line;
			while ((line = br.readLine()) != null) {
				// Add each word in lowercase to the HashSet to avoid case sensitivity issues
				dictonary.add(line.toLowerCase());
			}
		} catch (IOException e) {
			e.printStackTrace(); // Print stack trace in case of an error
		}
	}

	// reads the input file and check for misspelled words
	private static void readInput(String inputFilename) {
		try (BufferedReader br = new BufferedReader(new FileReader(new File(inputFilename)))) {
			String line;
			System.out.println("Misspelled words : ");
			while ((line = br.readLine()) != null) {
				// Convert input word to lowercase and check if it exists in the dictionary
				// If the word is not in the dictionary, it's considered misspelled
				if (!dictonary.contains(line.toLowerCase())) {
					System.out.println(line); // Print the misspelled word
				}
			}
		} catch (IOException e) {
			e.printStackTrace(); // Print stack trace in case of an error
		}
	}
}
