package ch21concurrency;

import java.util.concurrent.*;

import static util.Print.print;

class NonTask {
    public static void rest() {
        try {
            print("I'm gonna sleep");
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            print("Sleep interrupted");
        } finally {
            print("Good Bye!");
        }
    }
}

class Worker implements Runnable {
    @Override
    public void run() {
        NonTask.rest();
    }
}
public class Ex18Interruption {
    public static void main(String[] args) throws InterruptedException {
        // 3 ways to do it
        Thread t = new Thread(new Worker());
        t.start();
        TimeUnit.MILLISECONDS.sleep(100);
        t.interrupt();

        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Worker());
        TimeUnit.MILLISECONDS.sleep(100);
        exec.shutdownNow();

        ExecutorService exec2 = Executors.newCachedThreadPool();
        Future<?> f = exec2.submit(new Worker());
        TimeUnit.MILLISECONDS.sleep(100);
        f.cancel(true);
        //exec2.shutdown();
    }
}
