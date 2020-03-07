import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';
    final char STAR = '*';
    final char QUESTION = '?';
    final char PLUS = '+';

    TrieNode root = new TrieNode();
    ArrayList<Character> chars;
    boolean repeated;
    // TODO: Declare any instance variables you need here

    private class TrieNode {
        HashMap<Character, TrieNode> children = new HashMap<Character, TrieNode>();
        boolean isEnd;
        TrieNode(){
            isEnd = false;
        }
    }

    public Trie() {
    }

    // inserts string s into the Trie
    void insert(String s) {
        TrieNode current = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(current.children.get(c) == null){
//                System.out.println("creating new node");
                current.children.put(c, new TrieNode());
            }
            current = current.children.get(c);
        }
        current.isEnd = true;
    }

    // checks whether string s exists inside the Trie or not
    boolean contains(String s) {
        TrieNode current = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(current.children.get(c) == null){
                return false;
            }
            current = current.children.get(c);
        }
        return current.isEnd;
    }

    // Search for string with prefix matching the specified pattern sorted by lexicographical order.
    // Return results in the specified ArrayList.
    // Only return at most the first limit results.
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        chars = new ArrayList<Character>();
        prefixHelper(s, results, limit, 0, root);
    }
    void prefixHelper(String s, ArrayList<String> results, int limit, int matched, TrieNode node){
        if(results.size() >= limit){
            return; //we are done, no need to modify chars
        }
        if(matched == s.length()){
            if(node.isEnd){
                //explore subtree, I didn't do this part, may need to backtrack
            }
            return;
        }
        char c = s.charAt(matched);
        if(isLetterOrDigit(c)){
            if(node.children.get(c) != null){
                chars.add(c);
                patternHelper(s, results, limit, matched + 1, node.children.get(c));

                //backtrack
                if(chars.size() > 0){
                    chars.remove(chars.size()-1);
                }
            }
            return;
        } else if(c == '.'){
            for (HashMap.Entry<Character, TrieNode> pair: node.children.entrySet()) {
                char wc = pair.getKey();
//                System.out.format("prefix: %s, child: %s\n", getStringRepresentation(chars), wc);
                if(node.children.get(wc) != null) {
                    chars.add(wc);
                    patternHelper(s, results, limit, matched + 1, node.children.get(wc));

                    //backtrack
                    if (chars.size() > 0) {
                        chars.remove(chars.size() - 1);
                    }
                }
            }
            return;
        }
    }

    // Search for string matching the specified pattern.
    // Return results in the specified ArrayList.
    // Only return at most limit results.
    void patternSearch(String s, ArrayList<String> results, int limit) {
        StringBuilder builder = new StringBuilder(s.length());
        for(int i = 0; i < s.length(); i++)//modify + to c*
        {
            char c = s.charAt(i);
            builder.append(c);
            if(i+1 < s.length() && s.charAt(i+1) == '+'){
                builder.append(c);
                builder.append('*');
                i++;
            }
        }
        chars = new ArrayList<Character>();
//        System.out.println(builder);
        repeated = false;
        patternHelper(builder.toString(), results, limit, 0, root);
    }

    void patternHelper(String s, ArrayList<String> results, int limit, int matched, TrieNode node){
        if(results.size() >= limit){
            return; //no need to modify chars
        }
        if(matched == s.length()){
            if(node.isEnd) results.add(getStringRepresentation(chars));
//            chars.remove(chars.size()-1);
            return;
        }
        if(matched+1 < s.length() && (s.charAt(matched+1) == '?' || s.charAt(matched+1) == '*') && repeated == false) {
            //skip forward if ?
            patternHelper(s, results, limit, matched + 1, node);
        }
        repeated = false;
        char c = s.charAt(matched);
        if(isLetterOrDigit(c)){
            if(node.children.get(c) != null){
                chars.add(c);
                patternHelper(s, results, limit, matched + 1, node.children.get(c));

                //wildcards
                if(matched+1 < s.length() && s.charAt(matched+1) == '*') {
                    repeated = true;
                    patternHelper(s, results, limit, matched, node.children.get(c));
                }
                //backtrack
                if(chars.size() > 0){
                    chars.remove(chars.size()-1);
                }
            }
            return;
        } else if(c == '.'){
            for (HashMap.Entry<Character, TrieNode> pair: node.children.entrySet()) {
                char wc = pair.getKey();
//                System.out.format("prefix: %s, child: %s\n", getStringRepresentation(chars), wc);
                if(node.children.get(wc) != null) {
                    chars.add(wc);
                    patternHelper(s, results, limit, matched + 1, node.children.get(wc));

                    //wildcards
                    if(matched+1 < s.length() && s.charAt(matched+1) == '*') {
                        repeated = true;
                        patternHelper(s, results, limit, matched, node.children.get(wc));
                    }

                    //backtrack
                    if (chars.size() > 0) {
                        chars.remove(chars.size() - 1);
                    }
                }
            }
            return;
        } else{ //skip forward if +?*
            patternHelper(s, results, limit, matched + 1, node);
        }
    }
    String getStringRepresentation(ArrayList<Character> list){
        StringBuilder builder = new StringBuilder(list.size());
        for(Character ch: list)
        {
            builder.append(ch);
        }
        return builder.toString();
    }
    private static boolean isLetterOrDigit(char c) {
        return (c >= 'a' && c <= 'z') ||
                (c >= 'A' && c <= 'Z') ||
                (c >= '0' && c <= '9');
    }
    String[] prefixSearch(String s, int limit) {
        // Simplifies function call by initializing an empty array to store the results.
        // PLEASE DO NOT CHANGE the implementation for this function as it will be used
        // to run the test cases.
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }
    String[] patternSearch(String s, int limit) {
        // Simplifies function call by initializing an empty array to store the results.
        // PLEASE DO NOT CHANGE the implementation for this function as it will be used
        // to run the test cases.
        ArrayList<String> results = new ArrayList<String>();
        patternSearch(s, results, limit);
        return results.toArray(new String[0]);
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("a");
        t.insert("aa");
        t.insert("aaa");
        t.insert("aaaa");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");

//        System.out.println(t.contains("pik"));
//        System.out.println(t.contains("pike"));

//        String[] result1 = t.prefixSearch("pe", 10);
//        String[] result2 = t.patternSearch("pe.*", 10);
        String[] result3 = t.patternSearch("a*", 10);
        System.out.println(Arrays.toString(result3));
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily
    }
}
