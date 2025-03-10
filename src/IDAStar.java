import java.util.*;

public class IDAStar {

    private final Set<String> wordBank = new HashSet<>();

    // the recursion gets tricky, so i made an export variable to ensure i got the correct node
    public Node exportNode;

    // depthThreshold: how many words do i have in my solution?
    public double search(Node node, double depthThreshold, Board board, double heuristicVal, int verbosity) {
        double threshold = depthThreshold;
        double minNextThreshold = Integer.MAX_VALUE;

        // Generate new words based on the current node's word
        HashSet<String> newWords = generateWords(node);

        // Generate children nodes
        ArrayList<Node> children = getChildren(newWords, node, board, threshold);

        for(Node child: children){
            double result = depthFirstSearch(child, 1, threshold, board, heuristicVal);
            if (result == 0.0) {

                // Solution found, print the solution path
                ArrayList<String> soln = new ArrayList<>();
                while (exportNode.predecessorNode != null) {
                    soln.add(exportNode.getWord());
                    exportNode = exportNode.predecessorNode;
                }
                Collections.reverse(soln);
                System.out.println(String.join(" ", soln));
                return 0.0;  // Return 0 to indicate success
            }
            minNextThreshold = Math.min(minNextThreshold, result);
        }

        return minNextThreshold;
    }

    private double depthFirstSearch(Node node, int g, double depthThreshold, Board board, double hVal) {
        int min = Integer.MAX_VALUE;

        // HEURISTIC f
        // Should be part of Node ideally.
        // g: how many words have you created?
        // h: weighted number of unused letters
        double f = g + (node.unusedLetters.size()*hVal);


        // don't go down this branch if f is larger the current threshold
        if ( f > depthThreshold) {
            return f;
        }

        else if (node.getUnusedLetters().isEmpty()) {
            exportNode = node;
            return 0.0;
        }

        // we know that the node is under the depth threshold
        // now we search again recursively
        HashSet<String> newWords = generateWords(node);
        ArrayList<Node> children = getChildren(newWords, node, board, depthThreshold);

        double minNextThreshold = Integer.MAX_VALUE;

        for (Node child : children) {
            double result = depthFirstSearch(child, g + 1, depthThreshold, board, hVal);
            if (result == 0.0) {
                return 0.0;  // Solution found
            }
            minNextThreshold = Math.min(minNextThreshold, result);
        }

        return minNextThreshold;
    }

    public void createWordBank(Board board) {
        // Populate word bank with valid words from dictionary based on board constraints
        ArrayList<String> wordFiles = new ArrayList<>();
        wordFiles.add("words_easy.txt");
        Dictionary dict = new Dictionary(wordFiles);

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
    }

    public HashSet<String> generateWords(Node node){
        HashSet<String> newWords = new HashSet<>();
        if (node.getWord() == null) {
            for (String w : wordBank) {
                newWords.add(w);
            }
        } else {
            for (String w : wordBank) {
                if (node.getWord().charAt(node.getWord().length() - 1) == w.charAt(0)) {
                    newWords.add(w);
                }
            }
        }
        return newWords;
    }

    public ArrayList<Node> getChildren(HashSet<String> newWords, Node node, Board board, double g) {
        ArrayList<Node> children = new ArrayList<>();
        for (String word : newWords) {
            // Create a new list of unused letters
            ArrayList<Character> newUU = new ArrayList<>(node.getUnusedLetters());
            for (int i = 0; i < word.length(); i++) {
                if (newUU.contains(word.toLowerCase().charAt(i))) {
                    newUU.remove(Character.valueOf(word.toLowerCase().charAt(i)));
                }
            }

            // Create a new node representing this board state
            Node childNode = new Node(word, board, (int)g + 1, node, newUU);
            children.add(childNode);
        }
        return children;
    }
}
