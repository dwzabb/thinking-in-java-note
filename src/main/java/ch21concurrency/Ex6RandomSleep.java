package ch21concurrency;

import java.util.concurrent.*;

public class Ex6RandomSleep implements Runnable{

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(1, 10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long duration = System.currentTimeMillis() - startTime;
        System.out.println("Slept " + duration / 1000 + " seconds");
    }

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            exec.execute(new Ex6RandomSleep());
        }
    }
}
