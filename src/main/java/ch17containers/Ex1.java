package ch17containers;

import java.util.*;
import static util.Countries.DATA;

/**
 * Created by DengWenzhe on 3/19/17.
 */
public class Ex1 {
    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>();
        List<String> linkedList = new LinkedList<>();

        for (String[] country: DATA) {
            arrayList.add(country[0]);
            linkedList.add(country[1]);
        }
        Collections.sort(arrayList);
        Collections.sort(linkedList);

        System.out.print("Countries: ");
        System.out.println(arrayList);
        System.out.print("Capitals: ");
        System.out.println(linkedList);

        for (int i = 0 ; i < 3; i++) {
            Collections.shuffle(arrayList);
            Collections.shuffle(linkedList);

            System.out.print("Countries: ");
            System.out.println(arrayList);
            System.out.print("Capitals: ");
            System.out.println(linkedList);
        }
    }
}
