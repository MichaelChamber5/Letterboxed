import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class IDAStar {

    private final Set<String> wordBank = new HashSet<>();

    public boolean search(NodeAgain node, int depthThreshold) {
        int threshold = node.f();

        while (true) {
            int result = depthFirstSearch(node, 0, threshold);
            if (result == 0) {
                return true;
            }
            if (result == Integer.MAX_VALUE) {
                return false;
            }
            threshold = result;  // Update depth threshold to the minimum cost found
        }
    }

    private int depthFirstSearch(NodeAgain node, int g, int depthThreshold) {
        if (node.f() > depthThreshold) {
            return node.f();  // Prune this branch if f exceeds the current threshold
        }

        if (node.board.getUnusedCount() == 0) {

            // Solution found, print the solution path
            ArrayList<String> soln = new ArrayList<>();
            while(node.predecessorNode != null){
                soln.add(node.getWord());
                node = node.predecessorNode;
            }

            Collections.reverse(soln);
            String result = String.join(" ", soln);
            System.out.println(result);
            return 0;
        }


        // we didn't find thesolution, and so now we need to generate more words
        HashSet<String> newWords = new HashSet<>();

        // if this is the first node, we want to add all relevant words from the word bank as nodes
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

        int min = Integer.MAX_VALUE;

        for (String word : newWords) {

            // Create a new board and apply the word to it
            Board newBoard = node.board.copyOfBoard();
            newBoard.removeWordFromUnused(word.toUpperCase());

            // Create a new node representing this board state
            NodeAgain childNode = new NodeAgain(word, newBoard, g + 1, node);

            // Recursively call dfs for the new node
            int result = depthFirstSearch(childNode, g+1, depthThreshold);

            if (result == 0) {
                return 0;  // Solution found
            }

            if (result < min) {
                min = result;  // Update minimum f value
            }
        }

        return min;  // Return the minimum f value from all child nodes
    }


    public void createWordBank(Board board){
        ArrayList<String> wordFiles = new ArrayList<>();
        wordFiles.add("words_easy.txt");
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
    }
}
