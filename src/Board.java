import java.lang.reflect.Array;
import java.util.ArrayList;

public class Board {

    /*

Board class
  store board in 2D 4*x array (4 sides of x length)
    board[4][x]
      where columns contain our board side letters
  getUsed()
    returns a list of used letters
  getUnused()
    returns a list of unused letters
  getUnusedCount()
    return number of letters left to use
    */

    private int sideLength;
    private String fileLine;
    private char[][] board;
    private final int NUM_ROWS = 4;
    ArrayList<Character> unusedLetters;

    /**
     *
     */
    public Board(int sideLength, String lineFromFile)
    {
        this.sideLength = sideLength;
        this.fileLine = lineFromFile;
        unusedLetters = new ArrayList<Character>();
        board = new char[NUM_ROWS][sideLength];

        int boardRow = 0;
        int boardCol = 0;
        for(int i = 0; i < lineFromFile.length(); i++)
        {
            if(lineFromFile.charAt(i) == ' ')
            {
                boardRow++;
                boardCol = 0;
            }
            else
            {
                unusedLetters.add(Character.toLowerCase(lineFromFile.charAt(i)));
                board[boardRow][boardCol] = lineFromFile.charAt(i);
                boardCol++;
            }
        }
    }

    public Board(Board other){
        this.fileLine = other.fileLine;
        this.sideLength = other.sideLength;
        this.unusedLetters = new ArrayList<>(other.unusedLetters);
        this.board = new char[NUM_ROWS][sideLength];
        for (int i = 0; i < NUM_ROWS; i++) {
            this.board[i] = other.board[i].clone();  // Deep copy each row
        }
    }

    public int getUnusedCount()
    {
        return unusedLetters.size();
    }

    public String getFileLine() { return fileLine; }

    public void printFormattedBoard()
    {
        //print top row
        System.out.print(" ");
        for(int i = 0; i < board[0].length; i++)
        {
            System.out.print(board[0][i] + " ");
        }
        System.out.println();

        //print left and right sides
        for(int i = 0; i < board[0].length; i++)
        {
            System.out.print(board[1][i]);
            for(int j = 0; j < sideLength - 1; j++)
            {
                System.out.print("  ");
            }
            System.out.print(" ");
            System.out.print(board[2][i]);
            System.out.println();
        }

        //print bottom row
        System.out.print(" ");
        for(int i = 0; i < board[0].length; i++)
        {
            System.out.print(board[3][i] + " ");
        }
    }

    public char[] getLetters()
    {
        char[] theLetters = new char[sideLength * NUM_ROWS];
        int index = 0;
        for(int row = 0; row < NUM_ROWS; row++)
        {
            for(int col = 0; col < sideLength; col++)
            {
                theLetters[index] = board[row][col];
                index++;
            }
        }
        return theLetters;
    }

    public ArrayList<Character> getArrayListLetters()
    {
        ArrayList<Character> theLetters = new ArrayList<>();

        for(int row = 0; row < NUM_ROWS; row++)
        {
            for(int col = 0; col < sideLength; col++)
            {
                theLetters.add(board[row][col]);
            }
        }
        return theLetters;
    }

    public String[] getLettersAsString()
    {
        String[] theLetters = new String[sideLength * NUM_ROWS];
        int index = 0;
        for(int row = 0; row < NUM_ROWS; row++)
        {
            for(int col = 0; col < sideLength; col++)
            {
                theLetters[index] = String.valueOf(board[row][col]);
                index++;
            }
        }
        return theLetters;
    }


    // retrieves a list of next possible character moves
    public String[] getPossibleMovesString(char lastSelectedLetter){
        int notThisRow = getLetterRow(lastSelectedLetter);
        String[] list = new String[sideLength * (NUM_ROWS-1)];
        int index = 0;
        for(int row = 0; row < NUM_ROWS; row++)
        {
            if(row == notThisRow){
                continue;
            }
            for(int col = 0; col < sideLength; col++)
            {
                list[index] = String.valueOf(board[row][col]);
                index++;
            }
        }
        return list;

    }

    // retrieves a list of next possible character moves
    public char[] getPossibleMovesChar(char lastSelectedLetter){
        int notThisRow = getLetterRow(lastSelectedLetter);
        char[] list = new char[sideLength * (NUM_ROWS-1)];
        int index = 0;
        for(int row = 0; row < NUM_ROWS; row++)
        {
            if(row == notThisRow){
                continue;
            }
            for(int col = 0; col < sideLength; col++)
            {
                list[index] = board[row][col];
                index++;
            }
        }
        return list;

    }

    // retrieves a list of next possible character moves
    public ArrayList<Character> getPossibleMovesCharArrayList(char lastSelectedLetter){
        int notThisRow = getLetterRow(lastSelectedLetter);
        ArrayList<Character>  list = new ArrayList<>();

        for(int row = 0; row < NUM_ROWS; row++)
        {
            if(row == notThisRow){
                continue;
            }
            for(int col = 0; col < sideLength; col++)
            {
                list.add(board[row][col]);
            }
        }
        return list;
    }

    public char[][] getBoard()
    {
        return board;
    }

    public int getNumRows()
    {
        return NUM_ROWS;
    }

    public int getNumColums()
    {
        return sideLength;
    }

    public int getLetterRow(char letter)
    {
        for(int row = 0; row < NUM_ROWS; row++)
        {
            for(int col = 0; col < sideLength; col++)
            {
                if(letter == board[row][col])
                {
                    return row;
                }
            }
        }
        return -1;
    }

    public int getSideLength(){
        return sideLength;
    }

    public int getLetterCol(char letter)
    {
        for(int row = 0; row < NUM_ROWS; row++)
        {
            for(int col = 0; col < sideLength; col++)
            {
                if(letter == board[row][col])
                {
                    return col;
                }
            }
        }
        return -1;
    }

    public boolean isLetterInBoard(char letter)
    {
        for(int row = 0; row < NUM_ROWS; row++)
        {
            for(int col = 0; col < sideLength; col++)
            {
                if(letter == board[row][col])
                {
                    return true;
                }
            }
        }
        return false;
    }

    public void removeWordFromUnused(String word) {
        for (char letter : word.toLowerCase().toCharArray()) {
            removeFromUnused(letter);
        }
    }

    public void removeFromUnused(char letter)
    {
        if(unusedLetters.contains(letter))
        {
            Character c = letter;
            unusedLetters.remove(c);
        }
    }

    public void addToUnused(char letter)
    {
        if(!unusedLetters.contains(letter))
        {
            Character c = letter;
            unusedLetters.add(c);
        }
        else
        {
            System.err.println("Tried adding letter already in list...");
        }
    }

    /**
     * returns true if the letter has not been used
     * @param letter
     * @return
     */
    public boolean isInUnused(char letter)
    {
        return unusedLetters.contains(letter);
    }

    public int pointsForBeingUnused(char letter)
    {
        if(isInUnused(letter))
        {
            return 3;
        }
        return 0;
    }

    public int getUnusedLettersCount()
    {
        return unusedLetters.size();
    }

    public boolean hasWon()
    {
        if(unusedLetters.isEmpty())
            return true;
        return false;
    }

    public Board copyOfBoard(){
        return new Board(this);
    }

}
