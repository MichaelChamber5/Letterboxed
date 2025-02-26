import java.util.ArrayList;
import java.util.Stack;

public class Player {

    /*

Player class
  Board board (stores a board object for the player to perform actions on)
  List<String> words (stores words played by the player)
  play(char letter)
    must be a playable letter (on the board and not on current array (bottom can't link to bottom))
  submitWord()
    current word must be a word (duh...)
    last letter of current word becomes first letter of next word
  removeLetter()
    removes the last played letter ("tag" -> "ta")

*/

    private Board board;
    private ArrayList<String> words;
    private Stack<Character> lettersPlayed;
    private String currentWord;

    public Player(Board board)
    {
        this.board = board;
        words = new ArrayList<String>();
        lettersPlayed = new Stack<>();
        currentWord = "";
    }

    public void play(char letter) throws Exception
    {
        //check letter
        if(letterCanBePlayed(letter))
        {
            //goodie run code
            //add letter to current word
            currentWord += letter;
            //remove letter from unused letters in board
            board.removeFromUnused(letter);
            //update last played letter
            lettersPlayed.add(letter);
        }
        else
        {
            //bad
            throw new Exception("ERROR: cannot play that letter");
        }
    }

    public void Submit()
    {
        //TODO: if current word is a word...

        //add current word to words list
        //the last letter of the word gets automatically played?
        //current word = ""
        //
    }

    /**
     * checks if letter is in row (this is assuming no duplicate letters...)
     * @param letter
     * @return true if letter can be played, false otherwise
     */
    private boolean letterCanBePlayed(char letter)
    {
        //check if this is not first letter played
        if(lettersPlayed.empty() || lettersPlayed.peek().equals(' '))
        {
            //check if letter is in same row as previous letter
            if(board.getLetterRow(letter) == board.getLetterRow(lettersPlayed.peek()))
            {
                return false;
            }
        }
        //check if the letter is actually an option on the board
        if(!board.isLetterInBoard(letter))
        {
            return false;
        }
        return true;
    }

    private int getNumLettersPlayed()
    {
        return -1;
    }

}
