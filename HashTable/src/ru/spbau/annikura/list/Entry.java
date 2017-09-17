/**
 * Created by annikura on 9/17/17.
 * @author Anna Shvetsova
 */

package ru.spbau.annikura.list;

/** A simple structure to store key and value together
 *
 * @param <KeyType>
 * @param <ValueType>
 */
public class Entry<KeyType, ValueType> {
  public KeyType key = null;
  public ValueType value = null;

  public Entry(KeyType key, ValueType value) {
    this.key = key;
    this.value = value;
  }
}
