import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        HashMap<Byte, Integer> test = new HashMap<Byte, Integer>();
        test.put((byte)'H', 100);
        test.put((byte)'e', 1);
        test.put((byte)'l', 3);
        test.put((byte)'o', 2);
        test.put((byte)'W', 10);
        test.put((byte)'r', 1);
        test.put((byte)'d', 1);
        test.put((byte)'!', 1);
        test.put((byte)' ', 1);

        WeightedTree tree = new WeightedTree(test);
        tree.printTree();
        tree.queryCode((byte)'d');
    }
}
