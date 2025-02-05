import java.io.*;
import java.util.*;

public class AnagramSolver {
    //variables
    public static String st;
    public static String sorted;
    public static String sortW;
    public static String w;

    public static void main(String[] args) throws Exception {
        //creates the hashmap
        HashMap<String, List<String>> word = new HashMap<>();

        try (Scanner sc = new Scanner(System.in)) {
            File file = new File("anagram_dictionary.txt");
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                //sorts words in alphabetical order
                while ((st = br.readLine()) != null) {
                    char[] charArray = st.toCharArray();
                    Arrays.sort(charArray);
                    sorted = new String(charArray);
                    //finds words with the same letters
                    if (word.containsKey(sorted)) {
                        word.get(sorted).add(st);
                    } else {
                        List<String> anagrams = new ArrayList<>();
                        anagrams.add(st);
                        word.put(sorted, anagrams);
                    }
                }
            }
            //enter a word
            System.out.println("What word would you like to find anagrams of?");
            w = sc.nextLine();
        }

        //sorts the inputed word
        char[] wArray = w.toCharArray();
        Arrays.sort(wArray);
        sortW = new String(wArray);

        //checks if the word is available
        if (word.containsKey(sortW)) {
            List<String> value = word.get(sortW);
            if (value.size() > 1) {
                System.out.println("These are the anagrams for " + w + " " + value);
            } else {
                System.out.println("No anagrams for this particular word available");
            }
        } else {
            System.out.println("No anagrams for this particular word available");

        }

    }
}