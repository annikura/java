package ru.spbau.annikura.list;

import java.util.Map;

/** A simple structure to store key and value together
 *
 * @param <KeyType>
 * @param <ValueType>
 */
public class Pair<KeyType, ValueType> implements Map.Entry<KeyType, ValueType>{
    private KeyType key = null;
    private ValueType value = null;
    private ListNode<Pair<KeyType, ValueType>> node;

    public Pair(KeyType key, ValueType value, ListNode<Pair<KeyType, ValueType>> node) {
        this.key = key;
        this.value = value;
        this.node = node;
    }

    public KeyType getKey() {
        return key;
    }

    public void setKey(KeyType key) {
        this.key = key;
    }

    public ValueType getValue() {
        return value;
    }

    public ValueType setValue(ValueType value) {
        this.value = value;
        return value;
    }

    public ValueType destruct() {
        node.previous().eraseAfter();
        return value;
    }
}