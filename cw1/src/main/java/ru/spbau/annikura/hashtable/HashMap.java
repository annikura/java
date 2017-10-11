package ru.spbau.annikura.hashtable;

import ru.spbau.annikura.list.*;
import ru.spbau.annikura.list.List;

import java.util.*;

/**
 * String hash map. Allows containing string values as the counterparts to the string keys.
 * Elements can be added to, found in or removed from the table.
 */
public class HashMap<K, V> extends AbstractMap<K, V> implements Iterable<Pair<K, V>> {

    List<Pair<K, V>> innerList = new List<>();

    /**
     * A list storing key-value pairs, can search through itself, add or erase elements by key.
     *
     * @param <KeyType>
     * @param <ValueType>
     */



    private KeyValueList<K, V>[] tableContent = new KeyValueList[16];
    private int tableSize = 0;

    public HashMap() {
        initList();
    }

    @Override
    public Iterator<Pair<K, V>> iterator() {
        return innerList.iterator();
    }

    private void initList() {
        for (int i = 0; i < tableContent.length; i++) {
            if (tableContent[i] == null) {
                tableContent[i] = new KeyValueList<>();
            }
        }
    }

    private int countIndex(K item) {
        if (item != null)
            return (item.hashCode() % tableContent.length + tableContent.length) % tableContent.length;
        return 0;
    }

    /** If newSize > 0, resizes the table to newSize size. If newSize equals 0, nothing will be done.
     *
     * @param newSize new size of the table
     */
    private void resizeTable(int newSize) {
        if (newSize == 0) {
            return;
        }
        KeyValueList<K, V>[] oldTable = tableContent;
        tableContent = new KeyValueList[newSize];
        initList();
        for (KeyValueList<K, V> list : oldTable) {
            for (Pair<K, V> pair : list) {
                tableContent[countIndex(pair.getKey())].addOrAssign(
                        pair.getKey(),
                        pair.getValue(), pair);
            }
        }
    }

    /**
     * @return number of keys in a current table
     */
    public int size() {
        return tableSize;
    }

    /**
     * @param key
     * @return true if such a key exists in the hash table.
     */
    public boolean contains(K key) {
        return tableContent[countIndex(key)].find(key) != null;
    }

    /**
     * @param key
     * @return value if such a key exists in the hash table, null otherwise.
     */
    @Override
    public V get(Object key) {
        if (contains((K)key)) {
            return tableContent[countIndex((K) key)].find((K) key).getValue();
        }
        return null;
    }

    /**
     * Puts value under the given key into the hash table.
     *
     * @param key
     * @param value
     * @return a previous value, if there was such a key in the table, null if not.
     */
    public V put(K key, V value) {
        if (tableContent[countIndex(key)].find(key) == null) {
            tableSize++;
        }
        if (0.75 * tableSize >= tableContent.length) {  // default load factor is 0.75
            resizeTable(tableContent.length * 2);
        }
        ListNode<Pair<K, V>> node = new ListNode<>();
        Pair<K, V> newPair = new Pair<K, V>(key, value, node);
        node.setValue(newPair);
        innerList.add(node);
        return tableContent[countIndex(key)].addOrAssign(key, value, newPair);
    }

    /**
     * Removes key-value pair with the given key from the hash table.
     *
     * @param key
     * @return the deleted value, if such a key existed. Otherwise returns null.
     */
    @Override
    public V remove(Object key) {
        if (tableContent[countIndex((K)key)].find((K) key) != null) {
            tableSize--;
        }
        return tableContent[countIndex((K)key)].remove((K)key);
    }

    /**
     * Clears the hash table.
     */
    public void clear() {
        tableContent = new KeyValueList[1];
        initList();
        tableSize = 0;
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return new Set<Entry<K, V>>() {
            @Override
            public int size() {
                return tableSize;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Entry<K, V>> iterator() {
                return innerList.iterator();
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] ts) {
                return null;
            }

            @Override
            public boolean add(Entry<K, V> kvEntry) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Entry<K, V>> collection) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> collection) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }
        }
    }
}