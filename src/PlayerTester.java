import java.util.Scanner;

public class PlayerTester {
    public static void main(String[] args) {
        Board myBoard = new Board(3, "qol bau miy rje");
        Player me = new Player(myBoard);
        Scanner scan = new Scanner(System.in);

        while(!me.getBoard().hasWon())
        {
            System.out.print("[p] - play\n[s] - submit\n[r] - remove\n[i] - print board info\n>>> ");
            char input = scan.nextLine().charAt(0);
            if(input == 'p')
            {
                //play
                System.out.print("Enter a letter to play:\n>>> ");
                char letter = scan.nextLine().charAt(0);
                try
                {
                    me.play(letter);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
            else if(input == 's')
            {
                System.out.println("Submitting word...");
                try
                {
                    me.submit();
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else if(input == 'r')
            {
                try
                {
                    me.remove();
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else if(input == 'i')
            {
                System.out.println("Here's what the board looks like:");
                me.getBoard().printFormattedBoard();
                System.out.println();
                System.out.println("Your current guesses and current word:");
                System.out.println(me.toString());
                System.out.println();
            }
            else
            {
                System.out.println("I don't recognize that command, try again");
            }
        }

        System.out.println("CONGRATS: You solved the puzzle!");

    }
}
