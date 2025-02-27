import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class LetterboxedRunner {
    public static void main(String[] args) throws Exception {

        // helper for dictionary
        ArrayList<String> wordFiles = new ArrayList<>();
        wordFiles.add("words1.txt");
        wordFiles.add("words2.txt");
        wordFiles.add("words3.txt");
        wordFiles.add("words4.txt");
        wordFiles.add("words5.txt");

        System.out.println("Input the desired board size (2, 3, or 4): ");
        Scanner scn = new Scanner(System.in);
        int size = scn.nextInt();
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

        Board board = new Board(size, file.nextLine());
        Player me = new Player(board);

        // print board
        me.getBoard().printFormattedBoard();

        Dictionary dict = new Dictionary(wordFiles);

        char[] initialLetters = board.getLetters();

        // step 1: select first letter
        me.play(me.getNextBestChar(initialLetters));

        boolean playerHasWon = false;
        while(!playerHasWon){

            // SUBMIT
            // maybe add this logic too? if(dict.doesWordExist(soln) && dict.getNumSuccessors(soln) == 0)
            if(dict.doesWordExist(me.getCurrentWord()) && me.getCurrentWord().length()>4)
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
                    if(board.hasWon()){
                        System.out.println("CONGRATS: You solved the puzzle!");
                        playerHasWon = true;
                    }

                }
                catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }
            }

            // PLAY

            // generate list of next chars based on current last character
            int lastIdx = me.getCurrentWord().length()-1;
            char lastChar = me.getCurrentWord().charAt(lastIdx);
            System.out.print("\nlast character of word so far: ");
            System.out.println(lastChar);

            // generate possible next char moves
            char[] boardPossibleMoves = board.getPossibleMovesChar(lastChar);

            // import step: only return steps that we haven't tried (checks failedAttempts in Player)
            ArrayList<Character> newMoves = me.getNewMoves(boardPossibleMoves);

            // we need to check if any of these values are above 0
            if(me.hasAnotherMove(newMoves)){
                // we know theres at least one more path another option, so run the function to find the next best char
                char nextBestChar = me.getNextBestCharArrayList(newMoves);
                System.out.print("\nnext best move: ");
                System.out.println(nextBestChar);

                // "play" the letter
                me.play(nextBestChar);

                System.out.println("Your current guesses and current word:");
                System.out.println(me.toString());
            }
            else{

                // REMOVE
                me.addFailedAttempt(me.getCurrentWord());
                me.remove();
            }






            // If we get to a place where there are no more words that the algorithm can get,
            // Add the current word to a memo table so we don't repeat it in next best string,
            // remove a letter and try again


        }







    }

}
