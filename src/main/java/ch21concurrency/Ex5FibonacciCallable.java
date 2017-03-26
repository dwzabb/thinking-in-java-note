package ch21concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Ex5FibonacciCallable implements Callable<ArrayList<Integer>>{
    private int[] fibonacciCache;
    private int size;
    public Ex5FibonacciCallable(int size) {
        if (size <= 0)
            throw new IllegalArgumentException();

        this.size = size;
        fibonacciCache = new int[size];
        fibonacciCache[0] = 1;
        if (size > 1)
            fibonacciCache[1] = 1;
    }

    private int get(int position) {
        if (position < 0)
            throw new IllegalArgumentException();
        if (position < 2)
            return fibonacciCache[position];

        if (fibonacciCache[position] != 0)
            return fibonacciCache[position];

        int first = get(position - 2);
        int second = get(position - 1);
        int result = first + second;
        fibonacciCache[position] = result;

        return result;
    }


    @Override
    public ArrayList<Integer> call() throws Exception {
        ArrayList<Integer> result = new ArrayList<>();
        get(size - 1);
        for (int i = 0; i < size; i++) {
            result.add(fibonacciCache[i]);
        }
        return result;
    }

    public static void main(String[] args) {
        ArrayList<Future<ArrayList<Integer>>> results = new ArrayList<>();
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            results.add(executor.submit(new Ex5FibonacciCallable(i)));
        }

        for (Future<ArrayList<Integer>> result: results) {
            try {
                System.out.println(result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } finally {
                executor.shutdown();
            }
        }
    }
}
