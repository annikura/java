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
  public void createNode() throws Exception {
    ListNode<String> node = new ListNode<>("abc");
    assertEquals(null, node.next());
    assertEquals(null, node.previous());
    assertEquals("abc", node.getValue());
  }

  @Test
  public void insertAfterSimple() throws Exception {
    ListNode<String> node1 = new ListNode<>("abc");
    ListNode<String> node2 = new ListNode<>("def");
    assertEquals("def", node1.insertAfter(node2));
    assertSame(node2, node1.next());
    assertSame(node1, node2.previous());
    assertEquals(null, node1.previous());
    assertEquals(null, node2.next());
  }

  @Test
  public void insertAfter() throws Exception {
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
  public void eraseAfter() throws Exception {
    ListNode<String> node1 = new ListNode<>("abc");
    ListNode<String> node2 = new ListNode<>("def");
    ListNode<String> node3 = new ListNode<>("ghj");
    node1.insertAfter(node2);
    node1.insertAfter(node3);
    assertEquals("ghj", node1.eraseAfter());
    assertSame(node2, node1.next());
    assertSame(node1, node2.previous());
    assertEquals(null, node1.previous());
    assertEquals(null, node2.next());
    assertEquals(null, node2.eraseAfter());
    assertSame(node2, node1.next());
    assertSame(node1, node2.previous());
    assertEquals(null, node1.previous());
    assertEquals(null, node2.next());
    node1.eraseAfter();
    assertEquals(null, node1.previous());
    assertEquals(null, node1.next());
  }

}