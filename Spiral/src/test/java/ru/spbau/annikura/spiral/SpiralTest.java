package ru.spbau.annikura.spiral;


import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;


public class SpiralTest {
    @Test
    public void printUnspiralized() {
        int[][] matrix = {{5, 4, 3}, {6, 1, 2}, {7, 8, 9}};
        System.out.println("Start testing printUnspirialized:");
        Spiral.printUnspiralized(matrix);
        System.out.println("End testing printUnspirialized.");
    }

    @Test
    public void unspiralizeIntoListSimple1x1() {
        int[][] matrix = {{7}};
        int[] expected = {7};
        int[] actual = Spiral.unspiralizeIntoList(matrix);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void unspiralizeIntoListSimple3x3() {
        int[][] matrix = {{5, 4, 3}, {6, 1, 2}, {7, 8, 9}};
        int[] expected = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int[] actual = Spiral.unspiralizeIntoList(matrix);
        assertArrayEquals(expected, actual);
    }
    @Test
    public void unspiralizeIntoList3x3() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        int[] expected = {5, 6, 3, 2, 1, 4, 7, 8, 9};
        int[] actual = Spiral.unspiralizeIntoList(matrix);
        assertArrayEquals(expected, actual);
    }

    @Test
    public void sortMatrix1x3() {
        int[][] matrix = {{4, 3, 1}};
        int[][] sorted = Spiral.sortColumns(matrix);
        int[][] expected = {{1, 3, 4}};
        assertArrayEquals(expected, sorted);
    }

    @Test
    public void sortMatrix3x3() {
        int[][] matrix = {{4, 3, 1}, {1, 2, 3}, {4, 5, 6}};
        int[][] sorted = Spiral.sortColumns(matrix);
        int[][] expected = {{1, 3, 4}, {3, 2, 1}, {6, 5, 4}};
        assertArrayEquals(expected, sorted);
    }


    @Test
    public void sortMatrix1x0() {
        int[][] matrix = {{}};
        int[][] sorted = Spiral.sortColumns(matrix);
        int[][] expected = {{}};
        assertArrayEquals(expected, sorted);
    }
}
