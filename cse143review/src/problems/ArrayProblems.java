package problems;

import datastructures.IntTree;

/**
 * See the spec on the website for example behavior.
 *
 * REMEMBER THE FOLLOWING RESTRICTIONS:
 * - Do not add any additional imports
 * - Do not create new `int[]` objects for `toString` or `rotateRight`
 */
public class ArrayProblems {

    /**
     * Returns a `String` representation of the input array.
     * Always starts with '[' and ends with ']'; elements are separated by ',' and a space.
     */
    public static String toString(int[] array) {
        String temp = "[";
        int n = array.length;
        if (n != 0) {
            for (int i = 0; i < n; i++) {
                temp += array[i];
                if (i != n - 1) {
                    temp += ", ";
                }
            }
        }
        temp += "]";
        return temp;
    }

    /**
     * Returns a new array containing the input array's elements in reversed order.
     * Does not modify the input array.
     */
    @SuppressWarnings("checkstyle:EmptyBlock")
    public static int[] reverse(int[] array) {
        int n = array.length;
        int[] temp = new int[n];
        for (int i = 0; i < n; i++) {
            temp[i] = array[n - 1 - i];
        }
        return temp;
    }

    /**
     * Rotates the values in the array to the right.
     */
    public static void rotateRight(int[] array) {
        int n = array.length;
        if (n != 0) {
            int temp = array[n - 1];
            for (int i = n - 1; i >= 1; i -= 1) {
                array[i] = array[i - 1];
            }
            array[0] = temp;
        }
    }
}
