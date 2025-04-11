// Lanard Johnson
//Advanced Data Structures COSC-2454
//Dr.Zaki
// 2/13/2025
// Anagram Solver
/* This Java program helps users find anagrams for a given word. It reads a dictionary file (anagram_dictionary.txt),
sorts the words in alphabetical order, and groups words with the same set of letters (anagrams) into a hashmap.
The user then inputs a word, and the program checks if any anagrams of the word exist in the dictionary, printing the results.
 */
import java.io.*;
import java.util.*;

public class AnagramSolver {
    // Variables to hold word data
    public static String st; // Word from the file
    public static String sorted; // Sorted version of the word
    public static String sortW; // Sorted version of the input word
    public static String w; // Input word

    public static void main(String[] args) throws Exception {
        // Create a hashmap to store sorted word as key and list of anagrams as value
        HashMap<String, List<String>> word = new HashMap<>();

        try (Scanner sc = new Scanner(System.in)) {
            // Open the anagram dictionary file
            File file = new File("anagram_dictionary.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                // Read each word from the dictionary
                while ((st = br.readLine()) != null) {
                    // Convert the word to a character array, sort it, and convert back to string
                    char[] charArray = st.toCharArray();
                    Arrays.sort(charArray);
                    sorted = new String(charArray);

                    // If the sorted word exists in the hashmap, add the current word to its list
                    if (word.containsKey(sorted)) {
                        word.get(sorted).add(st);
                    } else {
                        // If no entry exists, create a new list and add the word
                        List<String> anagrams = new ArrayList<>();
                        anagrams.add(st);
                        word.put(sorted, anagrams);
                    }
                }
            }
            // Ask user for a word to find anagrams
            System.out.println("What word would you like to find anagrams of?");
            w = sc.nextLine();
        }

        // Sort the input word and check if it has anagrams in the hashmap
        char[] wArray = w.toCharArray();
        Arrays.sort(wArray);
        sortW = new String(wArray);

        // Check if the sorted input word exists as a key in the hashmap
        if (word.containsKey(sortW)) {
            List<String> value = word.get(sortW);
            if (value.size() > 1) {
                // If there are multiple anagrams, display them
                System.out.println("These are the anagrams for " + w + ": " + value);
            } else {
                // If no anagrams found (only one occurrence of the word)
                System.out.println("No anagrams for this particular word available");
            }
        } else {
            // If no anagrams found
            System.out.println("No anagrams for this particular word available");
        }

    }
}
