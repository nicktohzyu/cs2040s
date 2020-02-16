import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to encapsulate the code tree
 */
public class WeightedTree extends BTree implements ITree {
    /**
     * Builds the tree
     *
     * @param data
     * @return
     */
    WeightedTree(HashMap<Byte, Integer> data) {
        buildTree(data);
    }

    // Build the tree by reading in from a file
    WeightedTree(ReadFileWrapper inFile) {
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
     *
     * @param data
     * @return
     */
    private void buildTree(HashMap<Byte, Integer> data) {
//        change hashmap to array
//        System.out.println(data);
        Pair[] dataArray = new Pair[data.size()];
        int index = 0;
        for (Map.Entry<Byte, Integer> mapEntry : data.entrySet()) {
            dataArray[index] = new Pair(mapEntry.getKey(), mapEntry.getValue());
            index++;
        }
        Arrays.sort(dataArray);
//        System.out.println(Arrays.toString(dataArray));
        int prefixSumWeights[] = new int[dataArray.length + 1]; //ith index is sum of all weights to the left exclusive
        prefixSumWeights[0] = 0;
        for (int i = 0; i < dataArray.length; i++) {
            prefixSumWeights[i + 1] = dataArray[i].weight + prefixSumWeights[i];
        }
//        System.out.println(Arrays.toString(prefixSumWeights));
        root = createNode(dataArray, prefixSumWeights, 0, dataArray.length - 1);
//        call tree building function
    }

    private TreeNode createNode(Pair[] dataArray, int[] prefixSumWeights, int leftIndex, int rightIndex) { //indexes inclusive
//        System.out.println("create node: " + leftIndex + " " + rightIndex);
        int size = rightIndex - leftIndex + 1;
        if (size == 1) {
            return new TreeNode(dataArray[leftIndex].data, dataArray[leftIndex].weight);
        } else if (size == 2) { //is this necessary?
            TreeNode node = new TreeNode(dataArray[leftIndex].data, prefixSumWeights[rightIndex + 1] - prefixSumWeights[leftIndex]);
            node.left = createNode(dataArray, prefixSumWeights, leftIndex, leftIndex);
            node.right = createNode(dataArray, prefixSumWeights, rightIndex, rightIndex);
            return node;
        }

//        binary search for midpoint of weight
//        left child has nodes leftIndex to middle - 1 inclusive
//        right child has nodes middle to rightIndex inclusive
        int low = leftIndex + 1; //left child will never be empty
        int high = rightIndex - 1; //
        while (low < high) {
            int middle = low + (high - low) / 2;
            if (prefixSumWeights[middle] - prefixSumWeights[leftIndex] < prefixSumWeights[rightIndex + 1] - prefixSumWeights[middle]) {
                low = middle + 1;
            } else {
                high = middle;
            }
        }
//        System.out.println("split at: " + (low - 1));
//        left child has nodes leftIndex to low - 1 inclusive
//        right child has nodes low to rightIndex inclusive
//        current node is low - 1
        TreeNode node = new TreeNode(dataArray[low - 1].data, prefixSumWeights[rightIndex + 1] - prefixSumWeights[leftIndex]);
        node.left = createNode(dataArray, prefixSumWeights, leftIndex, low - 1);
        node.right = createNode(dataArray, prefixSumWeights, low, rightIndex);
        return node;
    }

    /**
     * This function takes a code and looks it up in the tree.
     * If the result is a leaf (at the proper depth), it returns
     * the data byte associated with that leaf.  Otherwise it returns null.
     *
     * @param code
     * @param bits
     * @return
     */
    public Byte query(boolean[] code, int bits) {
        TreeNode node = root;
        return node.data; //check if is leaf
    }

    /**
     * Returns the codeword associated with the symbol key
     * or null if not in the tree.
     *
     * @param key
     * @return
     */
    public boolean[] queryCode(byte key) {
        TreeNode node = root;
        ArrayList<Boolean> arrlist = new ArrayList<Boolean>();
        while(node.left != null && node.right != null) { //is there a need to check both?
//            System.out.println("explore tree: " + node.data + " " + key);
            if(key > node.data){
                arrlist.add(true);
                node = node.right;
            } else{
                arrlist.add(false);
                node = node.left;
            }
        }
//        System.out.println(arrlist);
        if(node.data != key){
            return null;
        }
        boolean[] code = ArrayUtils.toPrimitive(arrlist.toArray(new Boolean[arrlist.size()]));
        //convert arrlist to list
        return code;
    }

}
