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
    private char[][] board;
    private final int NUM_ROWS = 4;
    ArrayList<Character> unusedLetters;

    /**
     *
     */
    public Board(int sideLength, String lineFromFile)
    {
        this.sideLength = sideLength;
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
                unusedLetters.add(lineFromFile.charAt(i));
                board[boardRow][boardCol] = lineFromFile.charAt(i);
                boardCol++;
            }
        }
    }

    public int getUnusedCount()
    {
        return unusedLetters.size();
    }

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

    public void removeFromUnused(char letter)
    {
        if(unusedLetters.contains(letter))
        {
            unusedLetters.remove(letter);
        }
        else
        {
            System.err.println("Tried removing letter that doesn't exist...");
        }
    }

    public void addToUnused(char letter)
    {
        if(!unusedLetters.contains(letter))
        {
            unusedLetters.add(letter);
        }
        else
        {
            System.err.println("Tried adding letter already in list...");
        }
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

}
