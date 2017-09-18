package tests.ru.spbau.annikura.list;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import ru.spbau.annikura.list.ListNode;

/**
 * Created by ashvet on 9/17/17.
 * @author Anna Shvetsova
 */
public class ListNodeTest {
  @Test
  public static void createNode() {
    ListNode<String> node = new ListNode<>("abc");
    assertEquals(null, node.next());
    assertEquals(null, node.previous());
    assertEquals("abc", node.getValue());
  }

  @Test
  public static void insertAfterSimple() {
    // Tests simple insertion.
    ListNode<String> node1 = new ListNode<>("abc");
    ListNode<String> node2 = new ListNode<>("def");
    assertEquals("def", node1.insertAfter(node2));
    assertSame(node2, node1.next());
    assertSame(node1, node2.previous());
    assertEquals(null, node1.previous());
    assertEquals(null, node2.next());
  }

  @Test
  public static void insertAfter() {
    // Tests double insertion
    ListNode<String> node1 = new ListNode<>("abc");
    ListNode<String> node2 = new ListNode<>("def");
    ListNode<String> node3 = new ListNode<>("ghj");
    node1.insertAfter(node2);
    node1.insertAfter(node3);
    assertSame(node3, node1.next());
    assertSame(node2, node3.next());
    assertSame(node3, node2.previous());
    assertSame(node1, node3.previous());
  }

  @Test
  public static void eraseAfter() {
    ListNode<String> node1 = new ListNode<>("abc");
    ListNode<String> node2 = new ListNode<>("def");
    ListNode<String> node3 = new ListNode<>("ghj");
    node1.insertAfter(node2);  // null-> node1 -> node2 -> null
    node1.insertAfter(node3);  // null-> node1 -> node3 -> node2 -> null
    assertEquals("ghj", node1.eraseAfter()); // null-> node1 -> node2 -> null

    assertSame(node2, node1.next());
    assertSame(node1, node2.previous());
    assertEquals(null, node1.previous());
    assertEquals(null, node2.next());

    assertEquals(null, node2.eraseAfter());  // Testing erasing after the end of the list
    assertSame(node2, node1.next());
    assertSame(node1, node2.previous());
    assertEquals(null, node1.previous());
    assertEquals(null, node2.next());

    node1.eraseAfter();  // null -> node1 -> null
    assertEquals(null, node1.previous());
    assertEquals(null, node1.next());
  }

}