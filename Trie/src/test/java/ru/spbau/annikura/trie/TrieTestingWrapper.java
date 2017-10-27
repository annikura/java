package ru.spbau.annikura.trie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;


/**
 * Inner package class to test Trie.
 * Stores all the words it has ever been given in its own dictionary.
 * Provides an interface similar to the Trie's one, but doesn't return anything.
 * While calling those methods calls the same methods of the trie and checks correctness
 * by storing all the data in its own private storage and comparing th results of the calls.
 */
class TrieTestingWrapper {
    private HashSet<String> words = new HashSet<>();
    static private HashSet<String> dictionary = new HashSet<>();
    private Trie trie = new Trie();

    /**
     * For each word storing in dictionary checks whether it is stored by trie.
     * If there is a word which should not be in trie, but it is, falls with assertion.
     */
    public void checkConsistency() {
        for (String word : dictionary) {
            assertEquals(words.contains(word), trie.contains(word));
        }
    }

    /**
     * Adds string to the trie, checks consistency in the end.
     * @param str string that will be added to the trie
     */
    void add(String str) {
        assertEquals(!words.contains(str), trie.add(str));
        words.add(str);
        dictionary.add(str);
        assertEquals(words.size(), trie.size());
        checkConsistency();
    }

    /**
     * Asks trie whether string is there. Compares result to the one that should be.
     * @param str
     */
    void contains(String str) {
        dictionary.add(str);
        assertEquals(words.contains(str), trie.contains(str));
        assertEquals(words.size(), trie.size());
    }

    /**
     * Removes string from the trie. Checks consistency.
     * @param str
     */
    void remove(String str) {
        assertEquals(words.contains(str), trie.remove(str));
        words.remove(str);
        dictionary.add(str);
        assertEquals(words.size(), trie.size());
        checkConsistency();
    }

    /**
     * Counts the number of such strings, compares the answer with trie's result.
     * @param str
     */
    void howManyStartsWithPrefix(String str) {
        dictionary.add(str);
        int count = 0;
        for (String word : words) {
            if (word.length() >= str.length()) {
                boolean isPrefix = true;
                for (int i = 0; i < str.length(); i++) {
                    if (word.charAt(i) != str.charAt(i)) {
                        isPrefix = false;
                        break;
                    }
                }
                if (isPrefix) {
                    count++;
                }

            }
        }
        assertEquals(count, trie.howManyStartsWithPrefix(str));
    }

    /**
     * Checks trie and local storage sizes for equality. Falls with assertion if they don't match.
     */
    void size() {
        assertEquals(words.size(), trie.size());
    }

    /**
     * Serializes a stored trie.
     * @param out a stream where the stream will be serialized to
     * @throws IOException if IO fails.
     */
    void serialize(OutputStream out) throws IOException {
        trie.serialize(out);
    }

    /**
     * Deserializes a stored trie.
     * @param in a stream which the stream will be deserialized from.
     * @throws IOException if IO fails.
     * @throws ClassNotFoundException if input is not a valid Trie.
     */
    void deserialize(InputStream in) throws IOException, ClassNotFoundException {
        trie.deserialize(in);
    }
}
