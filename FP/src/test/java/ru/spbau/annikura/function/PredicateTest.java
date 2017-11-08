package ru.spbau.annikura.function;

import org.junit.Test;
import java.util.Random;

import static org.junit.Assert.*;

public class PredicateTest {
    @Test
    public void simpleOr() throws Exception {
        Predicate<Integer> isOdd = num -> num % 2 == 1;
        Predicate<Integer> squareLess = num -> num * num < 100;
        Predicate<Integer> orComp = isOdd.or(squareLess);
        assertEquals(true, orComp.apply(11));
        assertEquals(true, orComp.apply(3));
        assertEquals(true, orComp.apply(6));
        assertEquals(false, orComp.apply(12));
        assertEquals(false, orComp.apply(10));
    }

    @Test
    public void randomOr() throws Exception {
        Predicate<Integer> isOdd = num -> num % 2 == 1;
        Predicate<Integer> squareLess = num -> num * num < 100000;
        Predicate<Integer> orComp = isOdd.or(squareLess);
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int num = random.nextInt();
            assertEquals(isOdd.apply(num) || squareLess.apply(num), orComp.apply(num));
        }
    }

    @Test
    public void lazyOr() throws Exception {
        Predicate<Integer> isOdd = num -> num % 2 == 1;
        Predicate<Integer> squareLess = arg -> {
            assertNotEquals(1, arg % 2);
            return arg * arg < 1000000;
        };
        Predicate<Integer> orComp = isOdd.or(squareLess);
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int num = random.nextInt();
            assertEquals(isOdd.apply(num) || squareLess.apply(num), orComp.apply(num));
        }
    }


    @Test
    public void simpleAnd() throws Exception {
        Predicate<Integer> idOdd = num -> num % 2 == 1;
        Predicate<Integer> squareLess = num -> num * num < 100;
        Predicate<Integer> andComp = idOdd.and(squareLess);
        assertEquals(false, andComp.apply(11));
        assertEquals(true, andComp.apply(3));
        assertEquals(true, andComp.apply(9));
        assertEquals(false, andComp.apply(6));
        assertEquals(false, andComp.apply(12));
        assertEquals(false, andComp.apply(10));
    }


    @Test
    public void lazyAnd() throws Exception {
        Predicate<Integer> isOdd = num -> num % 2 == 1;
        Predicate<Integer> squareLess = arg -> {
            assertEquals(1, arg % 2);
            return arg * arg < 1000000;
        };
        Predicate<Integer> andComp = isOdd.and(squareLess);
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int num = random.nextInt();
            assertEquals(isOdd.apply(num) && squareLess.apply(num), andComp.apply(num));
        }
    }

    @Test
    public void randomAnd() throws Exception {
        Predicate<Integer> isOdd = num -> num % 2 == 1;
        Predicate<Integer> squareLess = num -> num * num < 100000;
        Predicate<Integer> andComp = isOdd.and(squareLess);
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int num = random.nextInt();
            assertEquals(isOdd.apply(num) && squareLess.apply(num), andComp.apply(num));
        }
    }

    @Test
    public void simpleNot() throws Exception {
        Predicate<Integer> isOdd = num -> num % 2 == 1;
        Predicate<Integer> isEven = isOdd.not();
        assertEquals(false, isEven.apply(7));
        assertEquals(false, isEven.apply(29));
        assertEquals(false, isEven.apply(24342131));
        assertEquals(true, isEven.apply(24342132));
        assertEquals(true, isEven.apply(0));
        assertEquals(true, isEven.apply(18));
    }

    @Test
    public void randomNot() throws Exception {
        Predicate<Integer> squareLess = num -> num * num < 100000;
        Predicate<Integer> squareMore = squareLess.not();
        Random random = new Random();
        for (int i = 0; i < 10000; i++) {
            int num = random.nextInt();
            assertEquals(!squareLess.apply(num) , squareMore.apply(num));
        }
    }

    @Test
    public void alwaysTrue() throws Exception {
        Random random = new Random();
        for (int i = 0; i < 1000; i++)
            assertEquals(true, Predicate.ALWAYS_TRUE.apply(random.nextInt()));
    }

    @Test
    public void alwaysFalse() throws Exception {
        Random random = new Random();
        for (int i = 0; i < 1000; i++)
            assertEquals(false, Predicate.ALWAYS_FALSE.apply(random.nextInt()));
    }


}