package ch21concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import static util.Print.print;

class Count {
    private int count = 0;
    private ThreadLocalRandom rand = ThreadLocalRandom.current();

    public synchronized int value() { return count; }

    // Counting will fail if remove synchronized
    public synchronized int increment() {
        int temp = count;
        if (rand.nextBoolean())
            Thread.yield();
        return count = ++temp;
    }
}

class Entrance implements Runnable{
    private static Count count = new Count();
    private static volatile boolean cancel = false;
    private static List<Entrance> entranceList =
            new ArrayList<>();
    private final int id;
    private int number = 0;

    public Entrance(int id) {
        this.id = id;
        entranceList.add(this);
    }

    @Override
    public void run() {
        while(!cancel) {
            synchronized (this) {
                ++number;
            }
            print(this + ", total " + count.increment());
        }
    }

    @Override
    public String toString() {
        return "Entrance " + id + ": number " + getValue();
    }

    public synchronized int getValue() { return number; }

    public static int getTotalCount() {
        return count.value();
    }

    public static int sumEntrances() {
        int sum = 0;
        for (Entrance entrance: entranceList) {
            sum += entrance.getValue();
        }
        return sum;
    }

    public static void cancel() {
        cancel = true;
    }
}
public class OrnamentalGarden {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++)
            exec.execute(new Entrance(i));

        TimeUnit.SECONDS.sleep(3);
        Entrance.cancel();
        exec.shutdown();

        if (!exec.awaitTermination(3, TimeUnit.SECONDS))
            print("Some tasks are not completed");

        print("Total count " + Entrance.getTotalCount());
        print("Entrance sum " + Entrance.sumEntrances());
    }
}
