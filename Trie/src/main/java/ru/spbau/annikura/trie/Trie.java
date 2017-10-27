package ru.spbau.annikura.trie;

import ru.spbau.annikura.string.StringPointer;

import java.io.*;
import java.util.HashMap;

/**
 * Trie data structure. Stores unique strings in a compact format.
 * Can add or remove string from the storage. Also can check whether string is already in this storage.
 *
 * Time complexity for add, remove, howManyStartsWithPrefix and contains is O(|word|).
 */
public class Trie implements Serializable {
    /**
     * Since Trie is a tree, this class implements its node. Each node is
     * corresponding to the prefix state. From the node this prefix can be expanded to prefix + 'symbol'
     * if is there is any string starting with prefix + 'symbol' in the trie.
     *
     * Using those 'jumps' all the information about the stored words can be retrieved.
     * The interface in this implementation allows to add strings to trie, remove them,
     * check if the string is in trie or count the number of
     * strings starting with some prefix.
     */
    private class TrieNode implements Serializable {
        private int wordsCount = 0;
        private HashMap<Character, TrieNode> toNode = new HashMap<Character, TrieNode>();
        private boolean wordEnd = false;

        /**
         * Checks if the subtree of the node contains the suffix string starting from the given string pointer.
         * @param str
         * @return true if subtree contains string pointer suffix string, false if not.
         */
        boolean contains (StringPointer str) {
            if (str.pointsAtEnd()) {
                return wordEnd;
            }
            return toNode.containsKey(str.symbol())
                    && toNode.get(str.symbol()).contains(str.next());
        }

        /**
         * Adds a suffix string provided by string pointer to the subtree of the node.
         * The subtree must not to contain this string before adding.
         * @param str
         */
        void addWord(StringPointer str) {
            wordsCount++;
            if (str.pointsAtEnd()) {
                wordEnd = true;
                return;
            }
            if (!toNode.containsKey(str.symbol())) {
                TrieNode newNode = new TrieNode();
                toNode.put(str.symbol(), newNode);
                newNode.addWord(str.next());
            } else {
                toNode.get(str.symbol()).addWord(str.next());
            }
        }

        /**
         * Removes a suffix string provided by string pointer from the subtree of the node.
         * The subtree must contain this string before removing.
         * @param str
         */
        void removeWord(StringPointer str) {
            wordsCount--;
            if (str.pointsAtEnd()) {
                wordEnd = false;
                return;
            }
            toNode.get(str.symbol()).removeWord(str.next());
        }

        /**
         * Finds the number of the substrings starting from the node prefix which
         * start with the suffix string provided by the string pointer.
         * @param str
         * @return number of strings in the subtree starting with given prefix.
         */
        int howManyStartsWithPrefix(StringPointer str) {
            if (str.pointsAtEnd()) {
                return wordsCount;
            }
            if (!toNode.containsKey(str.symbol())) {
                return 0;
            }
            return toNode.get(str.symbol()).howManyStartsWithPrefix(str.next());
        }
    }

    private TrieNode root = new TrieNode();
    /**
     * Adds string to trie.
     * @param element string which will be added to trie.
     * @return true if 'element' did not exist in the trie right before insertion, false if not.
     */
    public boolean add(String element) {
        if (contains(element)) {
            return false;
        }
        root.addWord(new StringPointer(element));
        return true;
    }

    /**
     * Checks whether trie contains given string.
     * @param element element whose presence in this Trie is to be tested
     * @return true if trie contains given string, false if not.
     */
    public boolean contains(String element) {
        return root.contains(new StringPointer(element));
    }

    /**
     * Removes string from the trie if it previously was there.
     * @param element a string to be removed from the Trie.
     * @return true if 'element' existed in the trie right before removing, false if not.
     */
    public boolean remove(String element) {
        if (!contains(element)) {
            return false;
        }
        root.removeWord(new StringPointer(element));
        return true;
    }

    public int size() {
        return root.howManyStartsWithPrefix(new StringPointer(""));
    }

    /**
     * Counts the number of strings that start with a given prefix.
     * @param prefix specifies a string prefix
     * @return number of strings that start with a given prefix.
     */
    public int howManyStartsWithPrefix(String prefix) {
        return root.howManyStartsWithPrefix(new StringPointer(prefix));
    }


    public void serialize(OutputStream out) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(out);
        oos.writeObject(this);
        oos.flush();
        oos.close();
    }
    public void deserialize(InputStream in) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(in);
        this.root = ((Trie)ois.readObject()).root;
        ois.close();
    }
}
