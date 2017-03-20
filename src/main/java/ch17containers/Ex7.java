package ch17containers;

import java.util.*;
import util.Countries;

/**
 * Created by DengWenzhe on 3/19/17.
 */
public class Ex7 {
    public static void main(String[] args) {
        List<String> arrayList = new ArrayList<>(Countries.names(10));
        List<String> linkedList = new LinkedList<>(Countries.names(10));

        System.out.println("arrayList " + arrayList);
        System.out.println("linkedList " + linkedList);

        ListIterator<String> li1 = arrayList.listIterator(arrayList.size());
        ListIterator<String> li2 = linkedList.listIterator();

        int step = 0;
        while (li1.hasPrevious() && li2.hasNext()) {
            String str1 = li1.previous();
            String str2 = li2.next();

            if (step % 2 == 1) {
                li1.add(str2);
            }
            step++;
        }

        System.out.println("arrayList " + arrayList);
        System.out.println("linkedList " + linkedList);
    }
}
