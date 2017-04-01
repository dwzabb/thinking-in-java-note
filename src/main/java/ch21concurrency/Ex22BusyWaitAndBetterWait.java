package ch21concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

class FlagTask implements Runnable {
    private boolean flag = false;
    @Override
    public synchronized void run() {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            print("Sleep was interrupted in FlagTask");
        }
        print("Setting flag = true");
        flag = true;
    }

    public synchronized boolean getFlag() {
        return flag;
    }
}

class BusyWait implements Runnable {
    private FlagTask task;
    public BusyWait(FlagTask r) {
        this.task = r;
    }

    @Override
    public void run() {
        long start = System.nanoTime();
        while(!Thread.interrupted()) {
            if (!task.getFlag())
                continue;
            print("FlagTask flag set to true!");
            long end = System.nanoTime();
            print("Busy waited " + (end - start) + " nanoseconds");
            break;
        }
    }

}

class BetterWait implements Runnable {
    private FlagTask task;
    public BetterWait(FlagTask task) {
        this.task = task;
    }

    @Override
    public void run() {
        try {
            while(!task.getFlag()) {
                wait();
            }
            print("FlagTask flag set to true!");
        } catch (InterruptedException e) {
            print("Wait was interrupted");
        }
    }
}
public class Ex22BusyWaitAndBetterWait {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        FlagTask ft = new FlagTask();
        BusyWait busyWait = new BusyWait(ft);
        exec.execute(busyWait);
        exec.execute(ft);

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            print("Sleep was interrupted in main");
        }

        FlagTask ft2 = new FlagTask();
        BetterWait betterWait = new BetterWait(ft2);
        exec.execute(betterWait);
        exec.execute(ft2);

        print();
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            print("Sleep was interrupted in main");
        }

        exec.shutdown();
    }
}
