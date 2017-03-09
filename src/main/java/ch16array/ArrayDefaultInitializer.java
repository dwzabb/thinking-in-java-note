package ch16array;

import java.util.Arrays;

/**
 * Created by DengWenzhe on 3/9/17.
 */
public class ArrayDefaultInitializer {

    public static void main(String[] args) {
        int[] a = new int[3];
        System.out.println("a " + Arrays.toString(a));

        //int[] b = new int[]; compile error
        //System.out.println(Arrays.toString(b));

        int[][] b = new int[3][4];
        System.out.println("b " + Arrays.deepToString(b));

        int[][] c = new int[3][];
        System.out.println("c " + Arrays.deepToString(c));

        int[][][] d = new int[3][4][5];
        System.out.println("d " + Arrays.deepToString(d));

        int[][][] e = new int[3][4][];
        System.out.println("e " + Arrays.deepToString(e));
    }
}
