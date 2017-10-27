package ru.spbau.annikura.trie;

import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StreamCorruptedException;

public class TrieTest {

    @Test
    public void createInstance() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.size();
    }

    @Test
    public void addSimple() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("nabagherjlk");
    }

    @Test
    public void addEqual() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("abac");
        trie.add("abac");
        trie.add("abac");
    }

    @Test
    public void addSimpleDifferent() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("abac");
        trie.add("catastrophe");
        trie.contains("cat");
    }

    @Test
    public void add() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("abac");
        trie.add("aaa");
        trie.add("tabac");
        trie.add("ba");
        trie.add("ab");
        trie.contains("aba");
    }

    @Test
    public void addUnicodeSymbols() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        for (int i = 0; i < 256 * 10; i++) {
            trie.add(String.valueOf(i));
        }
        trie.add("all those strings should be ok");
    }

    @Test
    public void containsEmpty() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.contains("");
    }

    @Test
    public void containsSimple() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("habcdef");
        trie.contains("habcde");
        trie.contains("habcdef");
        trie.contains("habcdefff");
    }

    @Test
    public void containsMultipleAdd() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("aabcdef");
        trie.add("aabc");
        trie.add("a");
        trie.add("a");
        trie.add("bee");
        trie.contains("aabcdef");
        trie.contains("aabc");
        trie.contains("a");
        trie.contains("bee");
        trie.contains("aa");
        trie.contains("aab");
        trie.contains("be");
        trie.add("aa");
        trie.contains("aa");
    }

    @Test
    public void containsAfterRemoveSimple() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("str");
        trie.remove("str");
        trie.contains("str");
    }

    @Test
    public void containsAfterRemoveEquals() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("str");
        trie.add("str");
        trie.add("str");
        trie.remove("str");
        trie.contains("str");
        trie.add("str");
        trie.contains("str");
    }


    @Test
    public void removeNotExisting() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.remove("string");
        trie.contains("string");
    }

    @Test
    public void removeSimple() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("str");
        trie.add("str");
        trie.remove("str");
        trie.contains("str");
        trie.remove("str");
    }

    @Test
    public void remove() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("str");
        trie.add("string");
        trie.remove("string");
        trie.contains("string");
        trie.contains("str");
    }

    @Test
    public void sizeEmpty() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.size();
    }

    @Test
    public void sizeEquals() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("str");
        trie.add("str");
        trie.add("str");
        trie.size();
    }

    @Test
    public void sizeAfterRemoveWithEquas() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("str");
        trie.add("str");
        trie.remove("str");
        trie.size();
    }

    @Test
    public void sizeAfterRemoveSimple() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("str");
        trie.add("string");
        trie.remove("str");
        trie.size();
    }


    @Test
    public void sizeAfterRemoveEmpty() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.remove("str");
        trie.size();
    }

    @Test
    public void howManyStartsWithPrefixFullString() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("string");
        trie.howManyStartsWithPrefix("string");
    }


    @Test
    public void howManyStartsWithPrefixEquals() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("string");
        trie.add("str");
        trie.add("str");
        trie.add("spa");
        trie.howManyStartsWithPrefix("st");
        trie.howManyStartsWithPrefix("str");
    }

    @Test
    public void howManyStartsWithPrefixSimple() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("string");
        trie.add("str");
        trie.howManyStartsWithPrefix("st");
        trie.howManyStartsWithPrefix("str");
        trie.howManyStartsWithPrefix("stri");
    }

    @Test
    public void howManyStartsWithPrefixMultipleAdd() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("string");
        trie.add("xor");
        trie.add("tor");
        trie.add("torpeda");
        trie.add("storpeda");
        trie.add("xobot");
        trie.add("str");
        trie.howManyStartsWithPrefix("st");
        trie.howManyStartsWithPrefix("str");
        trie.howManyStartsWithPrefix("stri");
        trie.howManyStartsWithPrefix("xo");
        trie.howManyStartsWithPrefix("x");
        trie.howManyStartsWithPrefix("xob");
        trie.howManyStartsWithPrefix("xor");
        trie.howManyStartsWithPrefix("storpedia");
        trie.howManyStartsWithPrefix("");
    }


    @Test
    public void howManyStartsWithPrefixAfterFakeRemove() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("xor");
        trie.remove("x");
        trie.remove("xo");
        trie.remove("");
        trie.howManyStartsWithPrefix("xo");
        trie.howManyStartsWithPrefix("x");
        trie.howManyStartsWithPrefix("xob");
        trie.howManyStartsWithPrefix("xor");
        trie.howManyStartsWithPrefix("");
    }

    @Test
    public void howManyStartsWithPrefixAfterRemove() {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("string");
        trie.add("xor");
        trie.add("tor");
        trie.add("torpeda");
        trie.add("storpeda");
        trie.add("xobot");
        trie.add("str");
        trie.remove("str");
        trie.remove("xo");
        trie.remove("xo");
        trie.howManyStartsWithPrefix("st");
        trie.howManyStartsWithPrefix("str");
        trie.howManyStartsWithPrefix("stri");
        trie.howManyStartsWithPrefix("xo");
        trie.howManyStartsWithPrefix("x");
        trie.howManyStartsWithPrefix("xob");
        trie.howManyStartsWithPrefix("xor");
        trie.howManyStartsWithPrefix("storpedia");
        trie.howManyStartsWithPrefix("");
    }

    @Test
    public void serializationAndDeserialization() throws IOException, ClassNotFoundException {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        trie.add("string");
        trie.add("one more string");
        trie.add("yet one more string");
        trie.add("string starting with 'string'");
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        trie.serialize(out);
        ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
        trie.deserialize(in);
        trie.checkConsistency();
    }


    @Test(expected = StreamCorruptedException.class)
    public void deserializeEmpty() throws ClassNotFoundException, IOException {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        ByteArrayInputStream in = new ByteArrayInputStream(new byte[10]);
        trie.deserialize(in);
        trie.checkConsistency();
    }

    @Test(expected = StreamCorruptedException.class)
    public void deserializeInvalidTrie() throws ClassNotFoundException, IOException {
        TrieTestingWrapper trie = new TrieTestingWrapper();
        byte [] byteArray = {5, 5, 5, 10, 14};
        ByteArrayInputStream in = new ByteArrayInputStream(byteArray);
        trie.deserialize(in);
    }
}