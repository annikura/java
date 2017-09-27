package ru.spbau.annikura.spiral;

import java.util.Arrays;
import java.util.Comparator;

/**
 * This class provides several static methods for working with a matrix
 */
public class Spiral {
    /**
     * Given a square matrix of odd size prints a list of its elements in a 'spiral' order.
     * Spiral starts in the middle of the matrix and then turns right and up.
     * @param matrix square matrix of odd size
     * @throws IllegalArgumentException if matrix has invalid size: it is not a square
     * or the side of the matrix is even.
     */
    public static void PrintUnspiralized(int[][] matrix) throws IllegalArgumentException {
        int[] toPrint = UnspiralizeIntoList(matrix);
        for (int i = 0; i < toPrint.length; i++)
            System.out.println(toPrint[i]);
    }

    /**
     * Given a square matrix of odd size returns a list of its elements in a 'spiral' order.
     * Spiral starts in the middle of the matrix and then turns right and up.
     * @param matrix square matrix of odd size
     * @return a list of matrix elements
     * @throws IllegalArgumentException if matrix has invalid size: it is not a square
     * or the side of the matrix is even.
     */
    public static int[] UnspiralizeIntoList(int[][] matrix) throws IllegalArgumentException {
        int n = matrix.length;
        if (n % 2 == 0) {
            throw new IllegalArgumentException("Matrix should have an odd size.");
        }
        // Then matrix contains at least one row.
        if (n != matrix[0].length) {
            throw new IllegalArgumentException("Matrix dimensions must be equal.");
        }
        // Now we can be sure that the matrix is an odd-sized square.

        int middle = n / 2;
        Coordinate currentCoordinate = new Coordinate(middle, middle);
        Coordinate delta = new Coordinate(0, 1);  // First gives a "right" direction.
        boolean alreadyTurned = false;
        int currentSideSize = 1;
        int alreadyWalked = 0;

        int[] returnList = new int[n * n];
        for (int i = 0; i < n * n && currentCoordinate.inSquare(0, 0, n, n); i++) {
            returnList[i] = matrix[currentCoordinate.getX()][currentCoordinate.getY()];
            if (alreadyWalked == currentSideSize) {
                alreadyWalked = 0;
                if (alreadyTurned) {
                    currentSideSize++;
                }
                alreadyTurned = !alreadyTurned;
                delta.turn90DegLeft();
            }
            currentCoordinate.add(delta);
            alreadyWalked++;
        }
        return returnList;
    }

    public static int[][] sortColumns(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return matrix;
        }
        int n = matrix.length, m = matrix[0].length;
        // Now we have at least one element in at least one column.
        int[][] transposedMatrix = transposeMatrix(matrix);
        Arrays.sort(transposedMatrix, new Comparator<int[]>() {
            public int compare(int[] arr1, int[] arr2) {
                return arr1[0] - arr2[0];
            }
        });
        return transposeMatrix(transposedMatrix);
    }

    private static int[][] transposeMatrix(int[][] matrix) {
        int n = matrix.length, m = matrix[0].length;
        int [][] transposedMatrix = new int[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++)
                transposedMatrix[j][i] = matrix[i][j];
        }
        return transposedMatrix;
    }
}