import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Scratch {

    public static void main(String[] args) throws FileNotFoundException {
        while(true) {
            System.out.println("Input the desired board size (2, 3, or 4): ");
            Scanner scn = new Scanner(System.in);
            int size = scn.nextInt();

            System.out.println("Input the BoardId: ");
            int fileId = scn.nextInt();

            String filename = "";

            if (size == 2) {
                filename = "4x2Boards.txt";
            } else if (size == 4) {
                filename = "4x4Boards.txt";
            } else {
                filename = "4x3Boards.txt";
            }

            Scanner file = new Scanner(new File(filename));

            for (int i = 0; i < fileId; i++) {
                file.nextLine();
            }

            Board board = new Board(size, file.nextLine());
            board.printFormattedBoard();

            long start = System.currentTimeMillis();
            IDAStar idAstar = new IDAStar();
            idAstar.createWordBank(board);


            Node root = new Node(null, board, 0, null, board.getUnusedLetters());

            boolean solnFound = false;
            double nextThreshold = 1;
            while (!solnFound) {
                nextThreshold = idAstar.search(root, nextThreshold, board, 0.5, 1);
                if (nextThreshold == 0.0) {
                    solnFound = true;
                }
            }

            long end = System.currentTimeMillis();
            long time = end - start;

            System.out.println("Time elapsed: " + time + "ms");
        }
    }
}