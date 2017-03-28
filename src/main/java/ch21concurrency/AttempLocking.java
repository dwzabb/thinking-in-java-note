package ch21concurrency;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.Print.print;

public class AttempLocking {
    private Lock lock = new ReentrantLock();

    public void untimed() {
        boolean acquired = lock.tryLock();
        try {
            print("tryLock() " + acquired);
        } finally {
            if (acquired) {
                lock.unlock();
            }
        }
    }

    public void timed() {
        boolean acquired = false;
        try {
            acquired = lock.tryLock(2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            print("tryLock(2, TimeUnit.SECONDS) " + acquired);
        } finally {
            if (acquired)
                lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final AttempLocking al = new AttempLocking();
        al.untimed();
        al.timed();

        new Thread() {
            { setDaemon(true);} // If this is commented out, the thread can be created via lambda way
            public void run() {
                al.lock.lock(); // 为什么此处可以获取private域lock？因为内部类可以访问private成员？
                print("acquired");
            }
        }.start();
        Thread.yield(); // Give the new thread a chance to run
        Thread.sleep(200); // yield doesn't really work, have to sleep to let new thread
                                 // has a chance to get lock
        al.untimed();
        al.timed();
    }
}
