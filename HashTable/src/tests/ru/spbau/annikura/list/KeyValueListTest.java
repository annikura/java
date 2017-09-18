package tests.ru.spbau.annikura.list;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import ru.spbau.annikura.list.KeyValueList;
import ru.spbau.annikura.list.Entry;

/**
 * Created by ashvet on 9/17/17.
 * @author Anna Shvetsova
 */

public class KeyValueListTest {

  @Test
  public void createInstance() throws Exception {
    KeyValueList<String, String> list;
  }

  @Test
  public void find() throws Exception {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "b"));
    assertEquals(null, list.addOrAssign("c", "d"));
    assertEquals(null, list.addOrAssign("xxx", "yyy"));
    assertEquals(new Entry<>("a", "b").value, list.find("a").value);
    assertEquals(new Entry<>("a", "b").key, list.find("a").key);
    assertEquals(new Entry<>("xxx", "yyy").value, list.find("xxx").value);
    assertEquals(new Entry<>("xxx", "yyy").key, list.find("xxx").key);
    assertEquals(new Entry<>("c", "d").value, list.find("c").value);
    assertEquals(new Entry<>("c", "d").key, list.find("c").key);
    assertEquals(null , list.find("xx"));
  }

  @Test
  public void addOrAssign() throws Exception {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "bb"));
    assertEquals(null, list.addOrAssign("bb", "bbb"));
    assertEquals("bb", list.addOrAssign("a", "x"));
    assertEquals(new Entry<>("a", "x").value, list.find("a").value);
    assertEquals(new Entry<>("a", "x").key, list.find("a").key);
    assertEquals(new Entry<>("bb", "bbb").key, list.find("bb").key);
    assertEquals(new Entry<>("bb", "bbb").value, list.find("bb").value);
  }

  @Test
  public void remove() throws Exception {
    KeyValueList<String, String> list = new KeyValueList<>();
    assertEquals(null, list.addOrAssign("a", "bb"));
    assertEquals(null, list.addOrAssign("bb", "bbb"));
    assertEquals("bb", list.addOrAssign("a", "x"));
    assertEquals("x", list.remove("a"));
    assertEquals(null, list.addOrAssign("a", "tt"));
    assertEquals(new Entry<>("a", "tt").value, list.find("a").value);
    assertEquals(new Entry<>("a", "tt").key, list.find("a").key);
  }

}