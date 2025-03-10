import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IDAStarRunner {
    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Input the desired board size (2, 3, or 4): ");
        Scanner scn = new Scanner(System.in);
        int size = scn.nextInt();

        System.out.println("Input the BoardId: ");
        int fileId = scn.nextInt();

        String filename = "";

        if(size == 2){
            filename = "4x2Boards.txt";
        }
        else if(size == 4){
            filename = "4x4Boards.txt";
        }
        else{
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

        NodeAgain root = new NodeAgain(null, board, 0, null);

        boolean found = idAstar.search(root, 0);
        long end = System.currentTimeMillis();

        long time = end-start;

        if(found){
            System.out.println("soulution found!");
        }
        else{
            System.out.println("no  solution found!");
        }

        System.out.println("Time elapsed: " + time + "ms");
    }
}
