package ch21concurrency;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.*;

import static util.Print.print;

class SleepBlocked implements Runnable {
    private String className = getClass().getSimpleName() + " ";
    @Override
    public void run() {
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            print(className + "InterruptedException");
        }
        print(className + "exiting run()");
    }
}

class IOBlocked implements Runnable {
    private String className = getClass().getSimpleName() + " ";
    private InputStream is;

    public IOBlocked(InputStream in) {
        this.is = in;
    }

    @Override
    public void run() {
        print(className + "waiting for read");
        try {
            is.read();
        } catch (IOException e) {
            if (Thread.currentThread().isInterrupted())
                print(className + "interrupted");
            else
                throw new RuntimeException();
        }
        print(className + "exiting run()");
    }
}

class SynchronizedBlocked implements Runnable {
    private String className = getClass().getSimpleName() + " ";

    public synchronized void f() {
        while(true)
            Thread.yield();
    }

    public SynchronizedBlocked() {
        new Thread(() -> f()).start();;
    }

    @Override
    public void run() {
        print(className + "trying to call f()");
        f();
        print(className + "exiting run()");
    }
}
public class Interrupting {
    private static ExecutorService exec =
            Executors.newCachedThreadPool();

    static void test(Runnable r) throws InterruptedException {
        Future<?> f = exec.submit(r);
        TimeUnit.MILLISECONDS.sleep(100);
        print("Interrupting " + r.getClass().getSimpleName());
        f.cancel(true);
        print("Interrupt send to " + r.getClass().getSimpleName());
    }

    public static void main(String[] args) throws InterruptedException {
        test(new SleepBlocked());
        test(new IOBlocked(System.in));
        test(new SynchronizedBlocked());

        TimeUnit.MILLISECONDS.sleep(500);
        print("Aborting system");
        System.exit(0);
    }
}/* Output
Interrupting SleepBlocked
Interrupt send to SleepBlocked
SleepBlocked InterruptedException
SleepBlocked exiting run()
IOBlocked waiting for read
Interrupting IOBlocked
Interrupt send to IOBlocked
SynchronizedBlocked trying to call f()
Interrupting SynchronizedBlocked
Interrupt send to SynchronizedBlocked
Aborting system
*/
