import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class BoardTester {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scn = new Scanner(new File("4x4Boards.txt"));
        String nextLine = scn.nextLine();
        System.out.println(nextLine);
        Board myBoard = new Board(4, nextLine);
        myBoard.printFormattedBoard();

//        char[] moves = new char[12];
//        moves = myBoard.getPossibleMoves('N');
//        System.out.println(moves);

        ArrayList<String> wordFiles = new ArrayList<>();
        wordFiles.add("words1.txt");
        wordFiles.add("words2.txt");
        wordFiles.add("words3.txt");
        wordFiles.add("words4.txt");
        wordFiles.add("words5.txt");


        Dictionary dictClass  = new Dictionary(wordFiles);

        System.out.println(dictClass.doesWordExist("isr"));


        System.out.print("\n\nNUM WORDS THAT START WITH A: ");
        System.out.println(dictClass.getNumSuccessors("a"));
        System.out.println(dictClass.getAverageSuccesorLength("a"));
    }

//    public int getNumSuccessors(String substring, ){
//        for(String word : dict)
//        for (int i = 0; i < substring.length(); i++) {
//
//        }
//    }
}
