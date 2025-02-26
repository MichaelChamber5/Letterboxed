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
            if(board.isInUnused(letter))
            {
                board.removeFromUnused(letter);
            }
            //update last played letter
            lettersPlayed.add(letter);
        }
        else
        {
            //bad
            throw new Exception("ERROR: cannot play that letter");
        }
    }

    public void submit()
    {
        //TODO: if current word is a word...

        //add current word to words list
        //add a space to lettersPlayed
        //current word = ""
        //play last played letter
        char endingLetter = lettersPlayed.peek();
        words.add(currentWord);
        lettersPlayed.add(' ');
        currentWord = "" + endingLetter;
        lettersPlayed.add(endingLetter);
    }

    public void remove() throws Exception
    {
        if(lettersPlayed.empty())
        {
            throw new Exception("Cannot remove, no letters have been played yet...");
        }
        char removedLetter = lettersPlayed.pop();
        //if letter we just removed isn't anywhere else in the stack, add it back to unusedLetters
        if(!hasLetterBeenPlayed(removedLetter)) //checking because a letter can be used more than once
        {
            board.addToUnused(removedLetter);
        }
        //if the letter previous to the letter removed is a space:
            //remove the space
            //undo the submission of the word
        if(!lettersPlayed.empty() && lettersPlayed.peek().equals(' '))
        {
            lettersPlayed.pop();
            currentWord = words.remove(words.size() - 1);
        }
        //else
            //reduce currentWord
        else
        {
            currentWord = currentWord.substring(0, currentWord.length() - 1);
        }
    }

    private boolean hasLetterBeenPlayed(char letter)
    {
        Stack<Character> tempStack = new Stack<>();
        tempStack.addAll(lettersPlayed);
        while(!tempStack.empty())
        {
            if(tempStack.pop().equals(letter))
            {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if letter is in row (this is assuming no duplicate letters...)
     * @param letter
     * @return true if letter can be played, false otherwise
     */
    private boolean letterCanBePlayed(char letter)
    {
        if(lettersPlayed.empty() || lettersPlayed.peek().equals(' '))
            return true;
        //check if this is not first letter played
            //check if letter is in same row as previous letter
        if(board.getLetterRow(letter) == board.getLetterRow(lettersPlayed.peek()))
        {
            return false;
        }
        //check if the letter is actually an option on the board
        if(!board.isLetterInBoard(letter))
        {
            return false;
        }
        return true;
    }

    public int getNumLettersPlayed()
    {
        int count = 0;
        Stack<Character> tempLetters = new Stack<>();
        tempLetters.addAll(lettersPlayed);
        while(!tempLetters.empty())
        {
            char currChar = tempLetters.pop();
            if(currChar != ' ')
            {
                count++;
            }
        }
        return count;
    }

    public String toString()
    {
        String theString = "";
        for(String word : words)
        {
            theString += word + "-";
        }
        theString += currentWord;
        return theString;
    }

    public Board getBoard()
    {
        return board;
    }

}
