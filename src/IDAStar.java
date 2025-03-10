import javax.swing.*;
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
        if(verbosity > 1)
        {
            System.out.println();
            System.out.println("This is the children of our current node [" + node.word + "]");
            System.out.print("[");
            for(Node child: children)
            {
                System.out.print(child.word + ", ");
            }
            System.out.println("]");
        }

        for(Node child: children){
            double result = depthFirstSearch(child, 1, threshold, board, heuristicVal, verbosity);
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

    private double depthFirstSearch(Node node, int g, double depthThreshold, Board board, double hVal, int verbosity) {
        int min = Integer.MAX_VALUE;

        // HEURISTIC f
        // Should be part of Node ideally.
        // g: how many words have you created?
        // h: weighted number of unused letters
        double f = g + (node.unusedLetters.size()*hVal);
        if(verbosity > 0)
        {
            System.out.println();
            System.out.println("f value for [" + node.word + "] ------> g-[" + g + "] + h(unused letters)-[" + (node.unusedLetters.size()*hVal) + "] = f-[" + f + "]");
        }

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
        if(verbosity > 1)
        {
            System.out.println();
            System.out.println("Node: " + node.word + " is under the depth threshold. Expanding node...");
            System.out.println("These are the children of [" + node.word + "]: ");
            System.out.print("[");
            for(Node child: children)
            {
                System.out.print(child.word + ", ");
            }
            System.out.println("]");
        }

        double minNextThreshold = Integer.MAX_VALUE;

        for (Node child : children) {
            double result = depthFirstSearch(child, g + 1, depthThreshold, board, hVal, verbosity);
            if (result == 0.0) {
                return 0.0;  // Solution found
            }
            minNextThreshold = Math.min(minNextThreshold, result);
        }

        return minNextThreshold;
    }

    public void createWordBank(Board board, int verbosity) {
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
        if(verbosity > 1)
        {
            System.out.println();
            System.out.println("All dictionary words: ");
            System.out.println(wordBank);
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
