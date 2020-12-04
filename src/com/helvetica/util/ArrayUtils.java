package com.helvetica.util;

public interface ArrayUtils {

    static Integer[][] generate(final Integer dimension) {
        Integer[][] matrix = new Integer[dimension][dimension];
        populateMatrix(matrix);
        return matrix;
    }

    private static void populateMatrix(Integer[][] matrix) {
        for (int cols = 0; cols < matrix.length; cols++) {
            for (int rows = 0; rows < matrix.length; rows++) {
                matrix[cols][rows] = (int) (Math.random() * 200 - 100);
            }
        }
    }

    static Integer[] flattenArray(Integer[][] array, int height, int width) {
        Integer[] flatArray = new Integer[height * width];

        for (int i = 0; i < height; i++) {
            if (width >= 0) System.arraycopy(array[i], 0, flatArray, i * width, width);
        }
        return flatArray;
    }

    static void printFlattened(Integer[] array, int dimension) {
        System.out.println();
        for (int i = 0; i < array.length; i++) {
            System.out.print(" " + array[i]);
            if ((i + 1) % dimension == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }

}
