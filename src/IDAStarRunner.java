import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class IDAStarRunner {
    public static void main(String[] args) throws FileNotFoundException {

        // Board Size of 2 testing
        Scanner scan = new Scanner(System.in);
        System.out.println("How do you want to run the code?\n[1] - all examples\n[2] - enter your own");
        System.out.print(">>> ");
        char runAll = scan.nextLine().charAt(0);
        int boardSizeInput = 3;
        String boardString = "";
        if (runAll != '1') {
            System.out.println("Enter desired board size [2], [3], [4]");
            System.out.print(">>> ");
            boardSizeInput = scan.nextInt();
            if (boardSizeInput == 2)
                System.out.println("Enter your board string in the format: \"XX XX XX XX\"");
            else if (boardSizeInput == 3)
                System.out.println("Enter your board string in the format: \"XXX XXX XXX XXX\"");
            else
                System.out.println("Enter your board string in the format: \"XXXX XXXX XXXX XXXX\"");
            System.out.print(">>> ");
            scan.nextLine();
            boardString = scan.nextLine();
        }
        System.out.println("Enter verbosity level: \n[0] - none\n[1] - moderate\n[2] - very verbose");
        System.out.print(">>> ");
        int verbosity = scan.nextInt();
        System.out.println();

        if (runAll == '1') {
            long totalTime = 0;
            for (int i = 0; i < 18; i++) {
                Scanner file = new Scanner(new File("4x2Boards.txt"));
                for (int j = 0; j < i; j++) {
                    file.nextLine();
                }
                System.out.println("Board # " + (i + 1));
                Board board = new Board(2, file.nextLine());
                board.printFormattedBoard();
                long start = System.currentTimeMillis();
                IDAStar idAstar = new IDAStar();
                idAstar.createWordBank(board, verbosity);
                Node root = new Node(null, board, 0, null, board.getUnusedLetters());

                System.out.print("\nAnswer: ");

                boolean solnFound = false;
                double nextThreshold = 1;
                while (!solnFound) {
                    nextThreshold = idAstar.search(root, nextThreshold, board, 0.5, verbosity);
                    if (nextThreshold == 0.0) {
                        solnFound = true;
                    }
                }

                long end = System.currentTimeMillis();
                long time = end - start;
                totalTime += time;
                System.out.println("Time elapsed: " + time + "ms\n\n");
            }
            long avg2 = totalTime / 18;
            System.out.println("Average time for size 2 board: " + avg2 + " ms.\n\n\n");


            // Board Size of 3 Testing
            totalTime = 0;
            for (int i = 0; i < 25; i++) {
                Scanner file = new Scanner(new File("4x3Boards.txt"));
                for (int j = 0; j < i; j++) {
                    file.nextLine();
                }
                Board board = new Board(3, file.nextLine());
                System.out.println("Board # " + (i + 1));
                board.printFormattedBoard();
                long start = System.currentTimeMillis();
                IDAStar idAstar = new IDAStar();
                idAstar.createWordBank(board, verbosity);
                Node root = new Node(null, board, 0, null, board.getUnusedLetters());

                System.out.print("\nAnswer: ");

                boolean solnFound = false;
                double nextThreshold = 1;
                while (!solnFound) {
                    nextThreshold = idAstar.search(root, nextThreshold, board, 0.5, verbosity);
                    if (nextThreshold == 0.0) {
                        solnFound = true;
                    }
                }

                long end = System.currentTimeMillis();
                long time = end - start;
                totalTime += time;
                System.out.println("Time elapsed: " + time + "ms\n\n");
            }
            long avg3 = totalTime / 25;
            System.out.println("Average time for size 3 board: " + avg3 + " ms.\n\n\n");


            // Board Size of 4
            totalTime = 0;
            for (int i = 0; i < 18; i++) {
                if (i == 2) {
                    i++;
                }
                Scanner file = new Scanner(new File("4x4Boards.txt"));
                for (int j = 0; j < i; j++) {
                    file.nextLine();
                }
                Board board = new Board(4, file.nextLine());
                System.out.println("Board # " + (i + 1));
                board.printFormattedBoard();
                long start = System.currentTimeMillis();
                IDAStar idAstar = new IDAStar();
                idAstar.createWordBank(board, verbosity);
                Node root = new Node(null, board, 0, null, board.getUnusedLetters());

                System.out.print("\nAnswer: ");

                boolean solnFound = false;
                double nextThreshold = 1;
                while (!solnFound) {
                    nextThreshold = idAstar.search(root, nextThreshold, board, 0.5, verbosity);
                    if (nextThreshold == 0.0) {
                        solnFound = true;
                    }
                }


                long end = System.currentTimeMillis();
                long time = end - start;
                totalTime += time;
                System.out.println("Time elapsed: " + time + "ms\n\n");
            }
            long avg4 = totalTime / 20;
            System.out.println("Average time for size 4 board: " + avg4 + " ms.");

            System.out.println("\n\n\n");
            System.out.println("Average time for size 2 board: " + avg2 + " ms.");
            System.out.println("Average time for size 3 board: " + avg3 + " ms.");
            System.out.println("Average time for size 4 board: " + avg4 + " ms.");
        } else {
            Board board = new Board(boardSizeInput, boardString);
            board.printFormattedBoard();

            long start = System.currentTimeMillis();
            IDAStar idAstar = new IDAStar();
            idAstar.createWordBank(board, verbosity);
            Node root = new Node(null, board, 0, null, board.getUnusedLetters());
            double hVal = 0;
            if(boardSizeInput == 3) {
                hVal = 0.5;
            }
            else if(boardSizeInput == 4) {
                hVal = 0.5;
            }

            System.out.print("\nAnswer: " );
            boolean solnFound = false;
            double nextThreshold = 1;
            while (!solnFound) {
                nextThreshold = idAstar.search(root, nextThreshold, board, hVal, verbosity);
                if (nextThreshold == 0.0) {
                    solnFound = true;
                }
            }

            long end = System.currentTimeMillis();

            long time = end - start;
            System.out.println("Time elapsed: " + time + "ms\n\n\n");
        }
    }



}
