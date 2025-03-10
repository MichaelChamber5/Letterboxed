import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class AStar {

    private final Set<String> wordBank = new HashSet<>();

    public String solve (Board board, int verbosity){
        ArrayList<String> wordFiles = new ArrayList<>();
        wordFiles.add("words_easy.txt");
//        wordFiles.add("words2.txt");
//        wordFiles.add("words3.txt");
//        wordFiles.add("words4.txt");
//        wordFiles.add("words5.txt");

        Dictionary dict = new Dictionary(wordFiles);

        // Add all the possible words you can to the word bank
        /*
        This loop adds to wordBank
        wordBank is basically all words in the dictionary that can actually be used
        in Letterboxed, we can only use letters on the board
        we also cannot use consecutive letters that are in the same row
         */
        for(String word : dict.getDictionary()){
            boolean addWord = true;

            if(verbosity > 1)
            {
                System.out.println();
                System.out.println("Checking whether to add the word: \"" + word + "\"");
            }
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

        if(verbosity > 0)
        {
            System.out.println();
            System.out.println();
            System.out.println("Word Bank:");
            for(String w : wordBank){
                System.out.println(w);
            }
        }


        // Search //

        // A* requires a priority queue
        // Heuristic: found in comparable function in Node
        PriorityQueue<Node> nodeQueue = new PriorityQueue<>();
        int maxWords = 6;
        nodeQueue.add(new Node(null, board, 0, null, board.getUnusedLetters()));

        while(!nodeQueue.isEmpty()){
            // poll takes the best option available
            Node node = nodeQueue.poll();
            if(verbosity > 0)
            {
                System.out.println("------ Checking node ------");
                node.printNicely();
            }

            // found an answer, all letters on the board are used, return a string
            if(node.unusedLetters.isEmpty()){

                // print the solution in the correct order
                ArrayList<String> soln = new ArrayList<>();
                while(node.predecessorNode != null){
                    soln.add(node.getWord());
                    node = node.predecessorNode;
                }

                //since we're backtracking through the nodes, we need to reverse the order of the array
                Collections.reverse(soln);
                String result = String.join("-", soln);
                return result.toUpperCase();
            }

            // if you find a node where you haven't written more than the max number of nodes,
            // add new nodes that start with the last letter of to the queue, as it could be a solution.
            if(node.numberOfWordsPlaced < maxWords){
                HashSet<String> newWords = new HashSet<>();

                // if this is the first node, we want to add all relevant words as nodes
                if(node.getWord() == null){
                    newWords.addAll(wordBank);
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

                if(verbosity > 1)
                {
                    System.out.println("\nList of possible new words: ");
                    System.out.println(newWords);
                }

                // now for each new word, add it as a node
                for(String w : newWords){

                    // remove from unused and pass that in
                    //newUU == new unused letter list
                    ArrayList<Character> newUU = new ArrayList<>(node.unusedLetters);
                    for(int i = 0; i < w.length(); i++)
                    {
                        if(newUU.contains(w.toLowerCase().charAt(i)))
                        {
                            newUU.remove(Character.valueOf(w.toLowerCase().charAt(i)));
                        }
                    }

                    Node newWord = new Node(w, board, node.numberOfWordsPlaced+1, node, newUU);
                    nodeQueue.add(newWord);
                }
            }

            //System.out.println("returning to top");
        }
        return "No answer found.";
    }
}
