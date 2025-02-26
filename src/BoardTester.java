import java.util.ArrayList;
import java.util.Set;

public class BoardTester {
    public static void main(String[] args) {
        Board myBoard = new Board(4, "irly hope this work");
        myBoard.printFormattedBoard();
        Dictionary dictClass  = new Dictionary();
        ArrayList<String> wordFiles = new ArrayList<>();

        wordFiles.add("words1.txt");
        wordFiles.add("words2.txt");
        wordFiles.add("words3.txt");
        wordFiles.add("words4.txt");
        wordFiles.add("words5.txt");
        Set<String> dictionary = dictClass.loadWordsFromTextFiles(wordFiles);
        System.out.println(dictClass.doesWordExist("Jacobethan", dictionary));
        System.out.println(dictClass.doesWordExist("plantal", dictionary));
        System.out.println(dictClass.doesWordExist("tchu", dictionary));
        System.out.println(dictClass.doesWordExist("deadpanning", dictionary));
        System.out.println(dictClass.doesWordExist("demicolumn", dictionary));

    }
}
