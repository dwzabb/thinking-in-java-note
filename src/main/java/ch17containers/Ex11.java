package ch17containers;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DengWenzhe on 3/19/17.
 */
public class Ex11 {
    public static void main(String[] args) {
        Queue<Item> heap = new PriorityQueue<>();
        for (int i = 0; i < 10; i++) {
            heap.offer(new Item());
        }

        while (!heap.isEmpty()) {
            System.out.print(heap.poll().val + ", ");
        }
    }
}

class Item implements Comparable<Item> {
    int val = ThreadLocalRandom.current().nextInt(100);

    @Override
    public int compareTo(Item o) {
        return Integer.compare(val, o.val);
    }
}
