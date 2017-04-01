package ch21concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

class WaitTask implements Runnable {
    @Override
    // synchronized is necessary because call to wait() has to be inside
    // a synchronized method or block
    public synchronized void run() {
        try {
            wait();
        } catch (InterruptedException e) {
            print("WaitTask is interrupted");
            return;
        }
        print("WaitTask received notification");
    }
}

class NotifyTask implements Runnable {
    private Runnable task;
    public NotifyTask(Runnable task) {
        this.task = task;
    }

    @Override
    public void run() {
        try {
            TimeUnit.SECONDS.sleep(1);
            print("Notifying task " + task.getClass().getSimpleName());
            // synchronized is necessary because call to notifyAll() has to be inside
            // a synchronized method or block of the object
            synchronized (task) {
                //task.notifyAll();
                task.notify();
            }
        } catch (InterruptedException e) {
            print("NotifyTask is interrupted");
        }
    }
}
public class Ex21WaitAndNotify {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        WaitTask wt = new WaitTask();
        exec.execute(wt);
        exec.execute(new NotifyTask(wt));

        exec.shutdown();
    }
}
