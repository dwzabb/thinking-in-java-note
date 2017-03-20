package main_for_test;

import java.util.*;

/**
 * Created by DengWenzhe on 3/9/17.
 */
public class MainForTest {

    public static void main(String[] args) {
        int[][] a = {
                {1, 2, 3},
                {4, 5, 6, 7}
        };

        System.out.println(Arrays.deepToString(a));

        Integer[][][] b = new Integer[2][3][];
        System.out.println(Arrays.deepToString(b));

        List<List<Integer>> nestedList = new ArrayList<>();
        List<Integer> list = Arrays.asList(1, 2, 3);
        nestedList.add(list);

        System.out.println(nestedList);

        nestedList.add(list.subList(0, 2));

        System.out.println(nestedList);
    }
}
