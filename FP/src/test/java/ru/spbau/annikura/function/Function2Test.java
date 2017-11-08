package ru.spbau.annikura.function;

import org.junit.Test;
import java.util.Random;

import static org.junit.Assert.*;

public class Function2Test {
    @Test
    public void createInstanceFromLambda() {
        Function2<Integer, Integer, Boolean> eq = (num1, num2) -> num1.equals(num2);
    }

    @Test
    public void createInstanceFromClass() {
        Function2<Integer, Integer, Boolean> isOdd = new Function2<Integer, Integer, Boolean>() {
            @Override
            public Boolean apply(Integer arg1, Integer arg2) {
                return arg1.equals(arg2);
            }
        };
    }

    @Test
    public void simpleApply() {
        Function2<Integer, Integer, Integer> mul = (num1, num2) -> num1 * num2;
        assertEquals(35, (int)mul.apply(5, 7));
        assertEquals(200, (int)mul.apply(10, 20));
    }

    @Test
    public void funcWithDifferentResultType() {
        Function2<Integer, Integer, String> sumStr = (num1, num2) -> Integer.toString(num1 + num2);
        assertEquals("17", sumStr.apply(5, 12));
        assertEquals("4", sumStr.apply(-1, 5));
        assertEquals("1333", sumStr.apply(1000, 333));
    }

    @Test
    public void funcWithDifferentArgumentTypes() {
        Function2<Integer, String, String> sumStr = (num1, str) -> Integer.toString(num1) + str;
        assertEquals("512", sumStr.apply(5, "12"));
        assertEquals("-15", sumStr.apply(-1, "5"));
        assertEquals("1000333", sumStr.apply(1000, "333"));
    }


    @Test
    public void integerSimpleComposition() {
        Function2<Integer, Integer, Integer> sum = (num1, num2) -> num1 + num2;
        Function1<Integer, Integer> square = num -> num * num;
        Function2<Integer, Integer, Integer> composition = sum.compose(square);
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int num1 = random.nextInt(1000000);
            int num2 = random.nextInt(1000000);
            assertEquals((num1 + num2) * (num1 + num2), (int)composition.apply(num1, num2));
        }
    }


    @Test
    public void differentTypesComposition() {
        Function2<Integer, Boolean, String> concat = (num1, num2) -> Integer.toString(num1 + (num2 ? 1 : 0));
        Function1<String , Integer> toIntInc = num -> Integer.parseInt(num) + 1;
        Function2<Integer, Boolean, Integer> composition = concat.compose(toIntInc);
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int num1 = random.nextInt(1000000);
            int num2 = random.nextInt(1000000) % 2;
            assertEquals(num1 + num2 + 1, (int)composition.apply(num1, num2 == 1));
        }
    }


    @Test
    public void bind1() throws Exception {
        Function2<Integer, Integer, Integer> substrAndMul9 = (num1, num2) -> (num2 - num1) * 9;
        Function1<Integer, Integer> substr5Mul9 = substrAndMul9.bind1(-5);
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = random.nextInt(1000000);
            assertEquals((num + 5) * 9, (int)substr5Mul9.apply(num));
        }
    }


    @Test
    public void bind2() throws Exception {
        Function2<Integer, Integer, Integer> substrAndMul9 = (num1, num2) -> (num1 - num2) * 9;
        Function1<Integer, Integer> substr5Mul9 = substrAndMul9.bind2(-5);
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int num = random.nextInt(1000000);
            assertEquals((num + 5) * 9, (int)substr5Mul9.apply(num));
        }
    }

    @Test
    public void curry() throws Exception {
        Function2<Integer, Integer, Integer> substrAndMul9 = (num1, num2) -> (num1 - num2) * 9;
        Function1<Integer, Function1<Integer, Integer>> substrAndMul9Cur = substrAndMul9.curry();
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            int num1 = random.nextInt(1000000);
            int num2 = random.nextInt(1000000);
            assertEquals((int)substrAndMul9.apply(num1, num2), (int)substrAndMul9Cur.apply(num2).apply(num1));
        }
    }

}