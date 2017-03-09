package ch15generic;

import java.util.Iterator;

/**
 * Created by DengWenzhe on 3/5/17.
 */
public class IterableFibonacci implements Iterable<Integer>{
    int size;
    int[] cache;
    int position = 0;

    public IterableFibonacci(int size) {
        if (size <= 0) throw new IllegalArgumentException();

        this.size = size;
        cache = new int[size];

        cache[0] = 1;
        if (size > 1)
            cache[1] = 1;
    }

    private class FibonacciIterable implements Iterator<Integer> {

        @Override
        public boolean hasNext() {
            return position < size;
        }

        @Override
        public Integer next() {
            if (position < 2)
                return cache[position++];

            int result = cache[position - 2] + cache[position - 1];
            cache[position++] = result;

            return result;
        }
    }
    @Override
    public Iterator<Integer> iterator() {
        return new FibonacciIterable();
    }
}

class IterableFibonacciTester {
    public static void main(String[] args) {
        for (int i: new IterableFibonacci(5)) {
            System.out.print(i+", ");
        }
    }
}