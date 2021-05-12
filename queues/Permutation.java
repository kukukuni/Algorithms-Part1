import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> ranq = new RandomizedQueue<String>();
        String content;
        while (!StdIn.isEmpty()) {
            content = StdIn.readString();
            ranq.enqueue(content);
        }
        for (int i = 0; i < k; i++) {
            StdOut.println(ranq.dequeue());
        }
    }
}
