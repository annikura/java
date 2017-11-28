package ru.spbau.annikura.treeset;

import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;

import static org.junit.Assert.*;

public class TreeSetDataTest {
    Comparator<Integer> cmp = new Comparator<Integer>() {
        @Override
        public int compare(Integer integer, Integer t1) {
            return t1 - integer;
        }
    };

    @Test
    public void genNewData() throws Exception {
        TreeSetData<Integer> data = TreeSetData.genNewData();
        assertEquals(data.getComparator(), Comparator.naturalOrder());
        assertNull(data.getRoot());
        assertNull(data.getRoot());
        assertEquals(0, data.size());
    }

    @Test
    public void genNewDataWithComparator() throws Exception {
        TreeSetData<Integer> data = TreeSetData.genNewData(cmp);
        assertEquals(data.getComparator(), cmp);
        assertNull(data.getRoot());
        assertNull(data.getRoot());
        assertEquals(0, data.size());
    }

    @Test
    public void setRoot() throws Exception {
        TreeSetData<Integer> data = TreeSetData.genNewData(cmp);
        data.setRoot(data.new SetNode(10));

        assertEquals(1, data.size());
        Iterator<Integer> it = data.straightIterator();
        assertEquals(10, (int)it.next());
        assertEquals(false, it.hasNext());

        it = data.descendingIterator();
        assertEquals(10, (int)it.next());
        assertEquals(false, it.hasNext());
    }

    @Test
    public void checkRoot() throws Exception {
        TreeSetData<Integer> data = TreeSetData.genNewData(cmp);
        TreeSetData<Integer>.SetNode node = data.new SetNode(-1);
        data.setRoot(node);

        assertNull(node.next());
        assertNull(node.prev());
    }

    @Test
    public void simpleNodesIteration() throws Exception {
        TreeSetData<Integer> data = TreeSetData.genNewData(cmp);
        data.setRoot(data.new SetNode(10));
        data.getRoot().setLeftNode(data.new SetNode(12));
        data.getRoot().setRightNode(data.new SetNode(9));

        assertEquals(3, data.size());
        Iterator<Integer> it = data.straightIterator();
        assertEquals(12, (int)it.next());
        assertEquals(10, (int)it.next());
        assertEquals(9, (int)it.next());
        assertEquals(false, it.hasNext());

        it = data.descendingIterator();
        assertEquals(9, (int)it.next());
        assertEquals(10, (int)it.next());
        assertEquals(12, (int)it.next());
        assertEquals(false, it.hasNext());
    }

    @Test
    public void sequentialLeftIteration() throws Exception {
        TreeSetData<Integer> data = TreeSetData.genNewData(cmp);
        TreeSetData<Integer>.SetNode node = data.new SetNode(-1);
        data.setRoot(node);

        for (int i = 0; i < 100; i++) {
            TreeSetData<Integer>.SetNode newNode = data.new SetNode(i);
            node.setLeftNode(newNode);
            node = newNode;
        }
        assertEquals(101, data.size());

        Iterator<Integer> it = data.straightIterator();
        for (int i = 99; i >= -1; i--) {
            assertEquals(i, (int)it.next());
        }
        assertEquals(false, it.hasNext());
    }

    @Test
    public void sequentialRightIteration() throws Exception {
        TreeSetData<Integer> data = TreeSetData.genNewData();
        TreeSetData<Integer>.SetNode node = data.new SetNode(-1);
        data.setRoot(node);

        for (int i = 0; i < 100; i++) {
            TreeSetData<Integer>.SetNode newNode = data.new SetNode(i);
            node.setRightNode(newNode);
            node = newNode;
        }
        assertEquals(101, data.size());

        node = data.getRoot();
        Iterator<Integer> it = data.straightIterator();
        for (int i = -1; i < 100; i++) {
            assertEquals(i, (int) it.next());
            if (i != 99) {
                assertEquals(i + 1, (int)node.down(i + 1).getValue());
            }
            node = node.down(i + 1);
        }
        assertEquals(false, it.hasNext());
    }

}