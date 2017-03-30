package ch21concurrency;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

class CircularSet {
    private int[] array;
    private int size;
    private int index = 0;

    public CircularSet(int size) {
        this.size = size;
        array = new int[size];

        Arrays.fill(array, -1);
    }

    public synchronized void add(int val) {
        array[index++] = val;
        index %= size;
    }

    public synchronized boolean contains(int val) {
        for (int i = 0; i < size; i++) {
            if (array[i] == val)
                return true;
        }

        return false;
    }
}
public class SerialNumberChecker {
    private static final int SIZE = 10;
    private static CircularSet serials = new CircularSet(1000);
    private static ExecutorService exec = Executors.newCachedThreadPool();

    static class SerialChecker implements Runnable {
        @Override
        public void run() {
            while (true) {
                int serialNumber = SerialNumberGenerator.nextSerialNumber();
                if (serials.contains(serialNumber)) {
                    print("Duplicate " + serialNumber);
                    System.exit(0);
                }
                serials.add(serialNumber);
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < SIZE; i++) {
            exec.execute(new SerialChecker());
        }

        if (args.length > 0) {
            TimeUnit.SECONDS.sleep(Integer.valueOf(args[0]));
            print("No duplicate found");
            System.exit(0);
        }
    }
}
