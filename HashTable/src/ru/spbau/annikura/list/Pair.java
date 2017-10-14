package ru.spbau.annikura.list;

/** A simple structure to store key and value together
 *
 * @param <KeyType>
 * @param <ValueType>
 */
public class Pair<KeyType, ValueType> {
  private KeyType key = null;
  private ValueType value = null;

  public Pair(KeyType key, ValueType value) {
    this.key = key;
    this.value = value;
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

  public void setValue(ValueType value) {
    this.value = value;
  }
}
