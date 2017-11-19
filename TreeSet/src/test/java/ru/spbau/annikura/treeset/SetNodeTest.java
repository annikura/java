package ru.spbau.annikura.treeset;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

public class SetNodeTest {
    TreeSetData<Integer> data = TreeSetData.genNewData();

    @Test
    public void creatingInstance() throws Exception {
        TreeSetData<Integer>.SetNode node = data.new SetNode(10);
        assertNotNull(node);
        assertEquals(node, node.down(10));
        assertNull(node.down(11));
        assertNull(node.down(0));
        assertNull(node.next());
        assertNull(node.prev());
    }

    @Test
    public void simpleLeftDown() throws Exception {
        TreeSetData<Integer>.SetNode leftNode = data.new SetNode(7);
        TreeSetData<Integer>.SetNode node = data.new SetNode(9);
        node.setLeftNode(leftNode);

        assertNull(node.down(11));
        assertEquals(leftNode, node.down(6));
        assertEquals(leftNode, node.down(8));
    }

    @Test
    public void simpleRightDown() throws Exception {
        TreeSetData<Integer>.SetNode node = data.new SetNode(9);
        TreeSetData<Integer>.SetNode rightNode = data.new SetNode(14);
        node.setRightNode(rightNode);

        assertNull(node.down(4));
        assertEquals(rightNode, node.down(19));
        assertEquals(rightNode, node.down(12));
    }


    @Test
    public void simpleDown() throws Exception {
        TreeSetData<Integer>.SetNode leftNode = data.new SetNode(-11);
        TreeSetData<Integer>.SetNode node = data.new SetNode(9);
        TreeSetData<Integer>.SetNode rightNode = data.new SetNode(104);
        node.setLeftNode(leftNode);
        node.setRightNode(rightNode);

        assertEquals(leftNode, node.down(-3));
        assertEquals(leftNode, node.down(0));
        assertEquals(rightNode, node.down(104));
        assertEquals(rightNode, node.down(1000));
    }

    @Test
    public void simplePrev() throws Exception {
        TreeSetData<Integer>.SetNode leftNode = data.new SetNode(7);
        TreeSetData<Integer>.SetNode node = data.new SetNode(9);
        node.setLeftNode(leftNode);

        assertEquals(leftNode, node.prev());
    }

    @Test
    public void simpleNext() throws Exception {
        TreeSetData<Integer>.SetNode node = data.new SetNode(9);
        TreeSetData<Integer>.SetNode rightNode = data.new SetNode(14);
        node.setRightNode(rightNode);

        assertEquals(rightNode, node.next());
    }

    @Test
    public void getValue() throws Exception {
        TreeSetData<Integer>.SetNode node = data.new SetNode(9);
        assertEquals(9, (int)node.getValue());
    }

    @Test
    public void creatingChainOfNodes() throws Exception {
        TreeSetData<Integer>.SetNode node = data.new SetNode(0);
        TreeSetData<Integer>.SetNode start = null, end = null;
        for (int i = 100; i > 0; i--) {
            TreeSetData<Integer>.SetNode leftNode = data.new SetNode(-i);
            TreeSetData<Integer>.SetNode rightNode = data.new SetNode(i);
            node.setLeftNode(leftNode);
            node.setRightNode(rightNode);

            if (i == 100) {
                start = leftNode;
                end = rightNode;
            }
        }
        for (int i = -100; i <= 100; i++) {
            assertEquals(i, (int)start.getValue());
            start = start.next();
        }
        for (int i = 100; i >= -100; i--) {
            assertEquals(i, (int)end.getValue());
            end = end.prev();
        }
    }

    @Test
    public void randomGetValue() throws Exception {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int num = random.nextInt();
            TreeSetData<Integer>.SetNode node = data.new SetNode(num);
            assertEquals(num, (int) node.getValue());
        }
    }

    @Test
    public void randomSetSonNode() throws Exception {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            int num1 = random.nextInt();
            int num2 = random.nextInt();
            TreeSetData<Integer>.SetNode node = data.new SetNode(num1);
            TreeSetData<Integer>.SetNode sonNode = data.new SetNode(num2);
            if (node.compareTo(num2) < 0) {
                node.setRightNode(sonNode);
                assertEquals(sonNode, node.next());
                assertNull(node.prev());
                assertEquals(sonNode, node.down(num2));
                assertEquals(sonNode, node.down(num2 + 5));
            } else {
                node.setLeftNode(sonNode);
                assertEquals(sonNode, node.prev());
                assertNull(node.next());
                assertEquals(sonNode, node.down(num2));
                assertEquals(sonNode, node.down(num2 - 7));

            }
        }
    }
}