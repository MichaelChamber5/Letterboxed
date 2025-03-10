A project by Michael Chambers and Josh Minnich

We did our project on letterboxed.
    Letterboxed is a puzzle by the NYT. It consists of a 3x3 square of letters.
    The player's goal is to use all the letters on the board to spell words.
    You cannot use letters outside the board, and you cannot use consecutive
    letters from the same side.
    The goal is not only to solve the board, but to get it in the minimum
    amount of words possible.

We implemented 3 main parts:
    A player run game - (to play run the [LetterboxedRunner] file)
    An A* algorithm solver - (run [AStarRunner])
    An IDA* algorithm solver - (run [IDAStarRunner])
        In both solvers you can either run some example problems or enter your own

To run a specific board:
    1. Run either [AStarRunner] or [IDAStarRunner]
    2. Choose option [2] - enter your own
    3. Enter the desired board size (probably 3)
    4. Enter your board in the format "XXX XXX XXX XXX"
        Letters should be capitalized
        Spaced delimit the sides of the square
    5. Choose a verbosity level
    6. The program will output the optimal solution!