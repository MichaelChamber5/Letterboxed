public class Node implements Comparable<Node>{
    String word;
    Board board;
    int numberOfWordsPlaced;
    Node predecessorNode;

    public Node(String word, Board board, int numberOfWordsPlaced, Node predecessorNode){
        this.word = word;
        this.board = board;
        this.numberOfWordsPlaced = numberOfWordsPlaced;
        this.predecessorNode = predecessorNode;

    }

    public Board getBoard(){
        return board;
    }

    public String getWord(){
        return word;
    }

    // compareTo is our heuristic
    // automatically sorted when inserted into the priority queue

    // prioritize words in the queue which have the least words placed, because we want to solve the puzzle in as little words as possible
    // and then prioritize the words/boards that have the least amount of unused letters
    // g: number of words
    // h: number of remaining unused letters
    @Override
    public int compareTo(Node other) {
        if(this.numberOfWordsPlaced == other.numberOfWordsPlaced) {
            return this.board.getUnusedCount() - other.board.getUnusedCount();
        }
        return this.numberOfWordsPlaced - other.numberOfWordsPlaced;
    }

}
