import java.util.Scanner;

public class PlayerTester {
    public static void main(String[] args) {
        Board myBoard = new Board(3, "qol bau miy rje");
        myBoard.printFormattedBoard();
        Player me = new Player(myBoard);
        Scanner scan = new Scanner(System.in);
        boolean playerHasWon = false;

        while(!playerHasWon)
        {

            System.out.print("\nEnter a letter to play or\n[1] - submit\n[2] - remove\n[3] - print board info\n>>> ");
            char input = scan.nextLine().charAt(0);

            if(input == '1')
            {
                System.out.println("Submitting word...");
                try
                {
                    me.submit();
                    System.out.println("Here's what the board looks like:");
                    me.getBoard().printFormattedBoard();
                    System.out.println();
                    System.out.println("Your current guesses and current word:");
                    System.out.println(me.toString());
                    System.out.println();
                    if(myBoard.hasWon()){
                        System.out.println("CONGRATS: You solved the puzzle!");
                        playerHasWon = true;
                    }

                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
            else if(input == '2')
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
            else if(input == '3')
            {
                System.out.println("Here's what the board looks like:");
                me.getBoard().printFormattedBoard();
                System.out.println();
                System.out.println("Your current guesses and current word:");
                System.out.println(me.toString());
                System.out.println();
            }
            else{
                //play

                try
                {
                    me.play(input);
                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }
//            else
//            {
//                System.out.println("I don't recognize that command, try again");
//            }
        }



    }
}
