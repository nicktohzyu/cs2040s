import java.util.HashMap;

/**
 * A class to encapsulate the code tree
 */
public class WeightedTree extends BTree implements ITree{
    /**
     * Builds the tree
     * @param data
     * @return
     */
    WeightedTree(HashMap<Byte, Integer> data){
        buildTree(data);
    }

    // Build the tree by reading in from a file
    WeightedTree(ReadFileWrapper inFile){
        readTree(inFile);
    }

    /*************************************************
     *
     * These are the routines to implement.
     * They build the tree from an array of words,
     * and query the tree.
     *
     *************************************************/
    /**
     * Builds the tree
     * @param data
     * @return
     */
    private void buildTree(HashMap<Byte, Integer> data){
//        change hashmap to array
        Pair dataArray[] = new Pair[]
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            System.out.println(pair.getKey() + " = " + pair.getValue());
            it.remove(); // avoids a ConcurrentModificationException
        }
//        sort data
//        call tree building function
    }

    private void createNode(HashMap<Byte, Integer> data, int left, int right, )

    /**
     * This function takes a code and looks it up in the tree.
     * If the result is a leaf (at the proper depth), it returns
     * the data byte associated with that leaf.  Otherwise it returns null.
     * @param code
     * @param bits
     * @return
     */
    public Byte query(boolean[] code, int bits){ return null; }

    /**
     * Returns the codeword associated with the symbol key
     * or null if not in the tree.
     * @param key
     * @return
     */
    public boolean[] queryCode(byte key) { return null; }
}
