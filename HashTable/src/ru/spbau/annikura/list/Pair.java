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

  public KeyType GetKey() {
    return key;
  }

  public void SetKey(KeyType key) {
    this.key = key;
  }

  public ValueType GetValue() {
    return value;
  }

  public void SetValue(ValueType value) {
    this.value = value;
  }
}
