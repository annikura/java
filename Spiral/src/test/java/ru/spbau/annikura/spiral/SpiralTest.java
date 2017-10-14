package ru.spbau.annikura.spiral;


import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;


public class SpiralTest {
    @Test
    public static void printUnspiralized() {
        int[][] matrix = {{5, 4, 3}, {6, 1, 2}, {7, 8, 9}};
        Spiral.printUnspiralized(matrix);
    }

    @Test
    public static void unspiralizeIntoListSimple1x1() {
        int[][] matrix = {{7}};
        int[] expected = {7};
        int[] actual = Spiral.unspiralizeIntoList(matrix);
        assertArrayEquals(expected, actual);
    }
    @Test
    public static void unspiralizeIntoListSimple3x3() {
        int[][] matrix = {{5, 4, 3}, {6, 1, 2}, {7, 8, 9}};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] actual = Spiral.unspiralizeIntoList(matrix);
        assertArrayEquals(expected, actual);
    }
    @Test
    public static void unspiralizeIntoList3x3() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] expected = {5, 6, 3, 2, 1, 4, 7, 8, 9};
        int[] actual = Spiral.unspiralizeIntoList(matrix);
        assertArrayEquals(expected, actual);
    }

    @Test
    public static void sortMatrix1x3() {
        int[][] matrix = {{4, 3, 1}};
        int[][] sorted = Spiral.sortColumns(matrix);
        int[][] expected = {{1, 3, 4}};
        assertArrayEquals(expected, sorted);
    }

    @Test
    public static void sortMatrix3x3() {
        int[][] matrix = {{4, 3, 1}, {1, 2, 3}, {4, 5, 6}};
        int[][] sorted = Spiral.sortColumns(matrix);
        int[][] expected = {{1, 3, 4}, {3, 2, 1}, {6, 5, 4}};
        assertArrayEquals(expected, sorted);
    }


    @Test
    public static void sortMatrix1x0() {
        int[][] matrix = {{}};
        int[][] sorted = Spiral.sortColumns(matrix);
        int[][] expected = {{}};
        assertArrayEquals(expected, sorted);
    }
}
