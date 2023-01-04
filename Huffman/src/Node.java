public class Node implements Comparable<Node>{
    int frequency;
    Node zero;
    Node one;
    char letter;

    Node(int frequency, Node zero, Node one, char letter){
        this.frequency = frequency;
        this.one = one;
        this.zero = zero;
        this.letter = letter;
    }

    @Override
    public int compareTo(Node n) {
        return frequency - n.frequency;
    }
}
