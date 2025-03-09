import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AStar {

    private final Set<String> wordBank = new HashSet<>();

    public static void main(String[] args) throws FileNotFoundException {
        System.out.println("Input the desired board size (2, 3, or 4): ");
        Scanner scn = new Scanner(System.in);
        int size = scn.nextInt();

        System.out.println("FileId: ");
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
        AStar astar = new AStar();
        System.out.println("\nAnswer: " + astar.solve(board));
        long end = System.currentTimeMillis();

        long time = end-start;
        System.out.println("Time elapsed: " + time + "ms");

    }

    public String solve (Board board){
        ArrayList<String> wordFiles = new ArrayList<>();
        wordFiles.add("words_easy.txt");
//        wordFiles.add("words2.txt");
//        wordFiles.add("words3.txt");
//        wordFiles.add("words4.txt");
//        wordFiles.add("words5.txt");

        Dictionary dict = new Dictionary(wordFiles);

        // Add all the possible words you can to the word bank
        for(String word : dict.getDictionary()){
            boolean addWord = true;

            // check to see if the first character is in the board
            ArrayList<Character> list = board.getArrayListLetters();
            if(!list.contains(word.charAt(0))){
                addWord = false;
            }

            // check to see if each next character is a possible move
            for (int i = 0; i < word.length()-1; i++) {
                if (!board.getPossibleMovesCharArrayList(word.charAt(i)).contains(word.charAt(i+1))){
                    addWord = false;
                }
            }
            if(addWord){
                wordBank.add(word);
            }
        }

        // debug

//        System.out.println("word bank");
//        for(String w : wordBank){
//            System.out.println(w);
//        }


        // Search //

        // A* requires a priority queue
        // Heuristic: found in comparable function in Node
        PriorityQueue<Node> nodeQueue = new PriorityQueue<>();
        int maxWords = 6;
        nodeQueue.add(new Node(null, board, 0, null));

        while(!nodeQueue.isEmpty()){

            // poll takes the best option available
            Node node = nodeQueue.poll();

            // found an answer, all letters on the board are used, return a string
            if(node.getBoard().getUnusedCount() == 0){

                // print the solution in the correct order
                ArrayList<String> soln = new ArrayList<>();
                while(node.predecessorNode != null){
                    soln.add(node.getWord());
                    node = node.predecessorNode;
                }

                Collections.reverse(soln);
                String result = String.join(" ", soln);
                return result.toUpperCase();
            }

            // if you find a node where you haven't written more than the max number of nodes,
            // add new nodes that start with the last letter of to the queue, as it could be a solution.
            if(node.numberOfWordsPlaced < maxWords){
                HashSet<String> newWords = new HashSet<>();

                // if this is the first node, we want to add all relevant words as nodes
                if(node.getWord() == null){
                    for (String w : wordBank) {
                        newWords.add(w);
                    }
                }
                // if we have a word in the node, we want the next word's first character to  match the first words last character
                //  ex:  candy-yesterday
                else {
                    for (String w : wordBank) {
                        if (node.getWord().charAt(node.getWord().length() - 1) == w.charAt(0)) {
                            newWords.add(w);
                        }
                    }
                }

                // now for each new word, add it as a node
                for(String w : newWords){

                    // create a new board for the new node
                    Board newBoard = node.getBoard().copyOfBoard();
                    newBoard.removeWordFromUnused(w.toUpperCase());

                    Node newWord = new Node(w, newBoard, node.numberOfWordsPlaced+1, node);
                    nodeQueue.add(newWord);
                }
            }

        }
        return "No answer found.";
    }
}
