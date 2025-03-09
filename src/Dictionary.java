import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Dictionary {

    private final Set<String> dictionary;

    public Dictionary(ArrayList<String> textFiles)
    {
        dictionary = new HashSet<>();
        for(String textFile : textFiles) {
            try (BufferedReader br = new BufferedReader(new FileReader(textFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    dictionary.add(line.trim().toUpperCase());
                }
            } catch (IOException e) {
                System.err.println("dictionary error, try again: " + e.getMessage());
            }
        }
    }

    public Set<String> getDictionary() {
        return dictionary;
    }

    public boolean doesWordExist(String possibleWord){
        return dictionary.contains(possibleWord.toLowerCase());
    }

    // This function checks the dictionary to see how many words match with what we've created so far
    // Potential heuristic: choose the next letter with the most amount of potential words in the dictionary
    public int getNumSuccessors(String substring) {
        int count = 0;
        for (String word : dictionary) {

            // if a dictionary word is smaller than the input, it won't be equal and will cause out of bounds exception
            if(word.length()<substring.length()){
                continue;
            }
            boolean valid = true;
            String word1 = word.toLowerCase();
            String substring1 = substring.toLowerCase();

            // this could probably be replaced with substring
            for (int i = 0; i < substring1.length(); i++) {
                if (word1.charAt(i) != substring1.charAt(i)) {
                    valid = false;
                    break;
                }
            }
            if(valid){count++;}
        }
        return count;
    }

    // This function returns an average word length for words that match the substring i have so far
    // Potential heuristic: choose the next letter with the highest average word length
    public double getAverageSuccesorLength(String substring) {
        double wordCount = 0.0;
        double charCount = 0.0;
        for (String word : dictionary) {
//            String substring1 = substring.toLowerCase();
//            String word1 = word.toLowerCase();
            if(word.length()<substring.length()){
                continue;
            }
            boolean valid = true;
            String word1 = word.toLowerCase();
            String substring1 = substring.toLowerCase();
            for (int i = 0; i < substring1.length(); i++) {

                if (word1.charAt(i) != substring1.charAt(i)) {
                    valid = false;
                    break;
                }
            }
            if(valid){
                wordCount++;
                charCount+=word.length();
            }
        }
        return charCount/wordCount;
    }

}
