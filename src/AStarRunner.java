import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AStarRunner {
    public static void main(String[] args) throws FileNotFoundException {
        // Board Size of 2 testing
        long totalTime = 0;
        for (int i = 0; i < 18; i++) {
            Scanner file = new Scanner(new File("4x2Boards.txt"));
            for (int j = 0; j < i; j++) {
                file.nextLine();
            }
            System.out.println("Board # " + (i+1));
            Board board = new Board(2, file.nextLine());
            board.printFormattedBoard();
            long start = System.currentTimeMillis();
            AStar astar = new AStar();
            System.out.println("\nAnswer: " + astar.solve(board));
            long end = System.currentTimeMillis();
            long time = end-start;
            totalTime += time;
            System.out.println("Time elapsed: " + time + "ms\n\n");
        }
        long avg2 = totalTime / 18;
        System.out.println("Average time for size 2 board: " + avg2 + " ms.");


        // Board Size of 3 Testing
        totalTime = 0;
        for (int i = 0; i < 25; i++) {
            Scanner file = new Scanner(new File("4x3Boards.txt"));
            for (int j = 0; j < i; j++) {
                file.nextLine();
            }
            Board board = new Board(3, file.nextLine());
            System.out.println("Board # " + (i+1));
            board.printFormattedBoard();
            long start = System.currentTimeMillis();
            AStar astar = new AStar();
            System.out.println("\nAnswer: " + astar.solve(board));
            long end = System.currentTimeMillis();
            long time = end-start;
            totalTime += time;
            System.out.println("Time elapsed: " + time + "ms\n\n");
        }
        long avg3 = totalTime / 25;
        System.out.println("Average time for size 3 board: " + avg3 + " ms.");


        // Board Size of 4
        totalTime = 0;
        for (int i = 0; i < 20; i++) {
            if (i==2){i++;}
            Scanner file = new Scanner(new File("4x4Boards.txt"));
            for (int j = 0; j < i; j++) {
                file.nextLine();
            }
            Board board = new Board(4, file.nextLine());
            System.out.println("Board # " + (i+1));
            board.printFormattedBoard();
            long start = System.currentTimeMillis();
            AStar astar = new AStar();
            System.out.println("\nAnswer: " + astar.solve(board));
            long end = System.currentTimeMillis();
            long time = end-start;
            totalTime += time;
            System.out.println("Time elapsed: " + time + "ms\n\n");
        }
        long avg4 = totalTime / 20;
        System.out.println("Average time for size 4 board: " + avg4 + " ms.");

        System.out.println("\n\n\n");
        System.out.println("Average time for size 2 board: " + avg2 + " ms.");
        System.out.println("Average time for size 3 board: " + avg3 + " ms.");
        System.out.println("Average time for size 4 board: " + avg4 + " ms.");

    }
}
