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
                    dictionary.add(line.trim().toLowerCase());
                }
            } catch (IOException e) {
                System.err.println("Something wrong, try reading the file again: " + e.getMessage());
            }
        }
    }

    public boolean doesWordExist(String possibleWord){
        return dictionary.contains(possibleWord.toLowerCase());
    }
}
