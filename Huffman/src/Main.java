import java.util.ArrayList;
import java.util.PriorityQueue;

public class Main {

    public static void dfs(Node node, ArrayList<Integer> result){
        if(node.zero == null){
            System.out.println("Code for " + node.letter + ":");
            for (int i = 0; i < result.size(); ++i){
                System.out.print(result.get(i).intValue());
            }
            System.out.println();
            return;
        }
        result.add(0);
        dfs(node.zero, result);
        result.remove(result.size() - 1);
        result.add(1);
        dfs(node.one, result);
    }

    public static void huffman(String s){
        int[] a = new int[256];
        for (int i = 0; i < s.length(); ++i){
            a[s.charAt(i)] += 1;
        }
        PriorityQueue<Node> queue = new PriorityQueue<>();
        for (int i = 0; i < 256; ++i){
            if(a[i] > 0){
                queue.add(new Node(a[i], null, null, (char) i));
            }
        }
        while (queue.size()>1){
            Node node1 = queue.poll();
            Node node2 = queue.poll();
            Node node3 = new Node(node1.frequency + node2.frequency, node1, node2, '\0');
            queue.add(node3);
        }

        dfs(queue.peek(), new ArrayList<Integer>());
    }

    public static void main(String[] args) {
        huffman("abbcdeefffgh");
    }
}
