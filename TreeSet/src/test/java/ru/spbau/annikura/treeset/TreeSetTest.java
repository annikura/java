package ru.spbau.annikura.treeset;

import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.Random;

public class TreeSetTest {
    @Test
    public void createInstance() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.check();
    }

    @Test
    public void createInstanceWithComparator() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>((a, b) -> b - a);
        tree.check();
    }

    @Test
    public void simpleAdd() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(5);
        tree.check();
    }

    @Test
    public void bigAdd() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        for (int i = 0; i < 1000; i++) {
            tree.add(i);
        }
        tree.check();
    }

    @Test
    public void randomAdd() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            tree.add(random.nextInt());
        }
        tree.check();
    }

    @Test
    public void simpleFirst() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(5);
        tree.first();
        tree.add(0);
        tree.first();
        tree.check();
    }

    @Test(expected = NoSuchElementException.class)
    public void exceptionFirst() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.first();
        tree.check();
    }

    @Test
    public void bigFirst() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        for (int i = 0; i < 1000; i++) {
            tree.add(-i);
            tree.first();
        }
        tree.check();
    }

    @Test
    public void randomFirst() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            tree.add(random.nextInt());
            tree.first();
        }
        tree.check();
    }

    @Test(expected = NoSuchElementException.class)
    public void exceptionLast() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.last();
    }


    @Test
    public void simpleLast() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(5);
        tree.last();
        tree.add(0);
        tree.last();
        tree.check();
    }

    @Test
    public void bigLast() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        for (int i = 0; i < 1000; i++) {
            tree.add(-i);
            tree.last();
        }
        tree.check();
    }

    @Test
    public void randomLast() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            tree.add(random.nextInt());
            tree.last();
        }
        tree.check();
    }

    @Test
    public void emptyLower() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.lower(10);
        tree.check();
    }

    @Test
    public void oneElementLower() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(5);
        tree.lower(4);
        tree.lower(7);
        tree.lower(5);
        tree.check();
    }

    @Test
    public void simpleLower() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(10);
        tree.add(8);
        tree.add(2);

        tree.lower(4);
        tree.lower(11);
        tree.lower(10);
        tree.lower(2);
        tree.lower(0);

        tree.check();
    }

    @Test
    public void bigLower() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        for (int i = 0; i < 100; i++) {
            tree.add(i);
        }
        for (int i = 100; i >= 0; i--) {
            tree.lower(i);
        }
        tree.check();
    }


    @Test
    public void randomLower() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            tree.add(random.nextInt());
        }
        for (int i = 0; i < 4000; i++) {
            tree.lower(random.nextInt());
        }
        tree.check();
    }


    @Test
    public void emptyFloor() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.floor(10);
        tree.check();
    }

    @Test
    public void oneElementFloor() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(5);
        tree.floor(4);
        tree.floor(7);
        tree.floor(5);
        tree.check();
    }

    @Test
    public void simpleFloor() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(10);
        tree.add(8);
        tree.add(2);

        tree.floor(4);
        tree.floor(11);
        tree.floor(10);
        tree.floor(2);
        tree.floor(0);

        tree.check();
    }

    @Test
    public void bigFloor() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        for (int i = 0; i < 100; i++) {
            tree.add(i);
        }
        for (int i = 100; i >= 0; i--) {
            tree.floor(i);
        }
        tree.check();
    }


    @Test
    public void randomFloor() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            tree.add(random.nextInt());
        }
        for (int i = 0; i < 4000; i++) {
            tree.floor(random.nextInt());
        }
        tree.check();
    }


    @Test
    public void emptyCeiling() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.ceiling(10);
        tree.check();
    }

    @Test
    public void oneElementCeiling() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(5);
        tree.ceiling(4);
        tree.ceiling(7);
        tree.ceiling(5);
        tree.check();
    }

    @Test
    public void simpleCeiling() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(10);
        tree.add(8);
        tree.add(2);

        tree.ceiling(4);
        tree.ceiling(11);
        tree.ceiling(10);
        tree.ceiling(2);
        tree.ceiling(0);

        tree.check();
    }

    @Test
    public void bigCeiling() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        for (int i = 0; i < 100; i++) {
            tree.add(i);
        }
        for (int i = 100; i >= 0; i--) {
            tree.floor(i);
        }
        tree.check();
    }


    @Test
    public void randomCeiling() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            tree.add(random.nextInt());
        }
        for (int i = 0; i < 4000; i++) {
            tree.ceiling(random.nextInt());
        }
        tree.check();
    }


    @Test
    public void emptyHigher() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.higher(10);
        tree.check();
    }

    @Test
    public void oneElementHigher() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(5);
        tree.higher(4);
        tree.higher(7);
        tree.higher(5);
        tree.check();
    }

    @Test
    public void simpleHigher() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        tree.add(10);
        tree.add(8);
        tree.add(2);

        tree.higher(4);
        tree.higher(11);
        tree.higher(10);
        tree.higher(2);
        tree.higher(0);

        tree.check();
    }

    @Test
    public void bigHigher() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        for (int i = 0; i < 100; i++) {
            tree.add(i);
        }
        for (int i = 100; i >= 0; i--) {
            tree.higher(i);
        }
        tree.check();
    }


    @Test
    public void randomHigher() throws Exception {
        TreeSetWrapper<Integer> tree = new TreeSetWrapper<>();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            tree.add(random.nextInt());
        }
        for (int i = 0; i < 4000; i++) {
            tree.higher(random.nextInt());
        }
        tree.check();
    }
}