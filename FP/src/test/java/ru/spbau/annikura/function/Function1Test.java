package ru.spbau.annikura.function;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class Function1Test {
    @Test
    public void createInstanceFromLambda() {
        Function1<Integer, Boolean> isOdd = num -> num % 2 == 1;
    }

    @Test
    public void createInstanceFromClass() {
        Function1<Integer, Boolean> isOdd = new Function1<Integer, Boolean>() {
            @Override
            public Boolean apply(Integer arg) {
                return arg % 2 == 1;
            }
        };
    }

    @Test
    public void simpleApply() {
        Function1<Integer, Boolean> isOdd = num -> num % 2 == 1;
        assertEquals(true, isOdd.apply(5));
        assertEquals(false, isOdd.apply(10));
    }

    @Test
    public void funcWithDifferentTypes() {
        Function1<Integer, String> squareStr = num -> Integer.toString(num * num);
        assertEquals("25", squareStr.apply(5));
        assertEquals("1", squareStr.apply(-1));
        assertEquals("1000000", squareStr.apply(1000));
    }

    @Test
    public void idComposition() {
        Function1<Integer, Integer> id = num -> num;
        Function1<Integer, Integer> mulBy7 = num -> num * 7;
        Function1<Integer, Integer> composition1 = id.compose(mulBy7);
        Function1<Integer, Integer> composition2 = mulBy7.compose(id);
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = random.nextInt(10000000);
            assertEquals(num * 7, (int)composition1.apply(num));
            assertEquals(num * 7, (int)composition2.apply(num));
        }
    }


    @Test
    public void integerSimpleComposition() {
        Function1<Integer, Integer> add5 = num -> num + 5;
        Function1<Integer, Integer> mulBy7 = num -> num * 9;
        Function1<Integer, Integer> composition = add5.compose(mulBy7);
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = random.nextInt(1000000);
            assertEquals((num + 5) * 9, (int)composition.apply(num));
        }
    }

    @Test
    public void differentTypesId() {
        Function1<Integer, String> toStr = num -> Integer.toString(num);
        Function1<String, Integer> toInt = Integer::parseInt;
        Function1<Integer, Integer> intComposition = toStr.compose(toInt);
        Function1<String, String> strComposition = toInt.compose(toStr);
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = random.nextInt(1000000);
            assertEquals(num, (int)intComposition.apply(num));
            assertEquals(Integer.toString(num), strComposition.apply(Integer.toString(num)));
        }
    }

}