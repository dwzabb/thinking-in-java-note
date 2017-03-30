package ch21concurrency;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static util.Print.print;

class LockTest1 { // all methods use same lock
    private Lock lock = new ReentrantLock();
    public void f1() {
        lock.lock();
        try {
            for(int i = 0; i < 5; i++) {
                print("f1()");
                Thread.yield();
            }
        } finally {
            lock.unlock();
        }
    }
    public void g1() {
        lock.lock();
        try {
            for(int i = 0; i < 5; i++) {
                print("g1()");
                Thread.yield();
            }
        } finally {
            lock.unlock();
        }
    }
    public void h1() {
        lock.lock();
        try {
            for(int i = 0; i < 5; i++) {
                print("h1()");
                Thread.yield();
            }
        } finally {
            lock.unlock();
        }
    }
}

class LockTest2 { // each method has a different lock
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();
    private Lock lock3 = new ReentrantLock();

    public void f2() {
        lock1.lock();
        try {
            for(int i = 0; i < 5; i++) {
                print("f2()");
                Thread.yield();
            }
        } finally {
            lock1.unlock();
        }
    }
    public void g2() {
        lock2.lock();
        try {
            for(int i = 0; i < 5; i++) {
                print("g2()");
                Thread.yield();
            }
        } finally {
            lock2.unlock();
        }
    }
    public void h2() {
        lock3.lock();
        try {
            for(int i = 0; i < 5; i++) {
                print("h2()");
                Thread.yield();
            }
        } finally {
            lock3.unlock();
        }
    }
}
public class Ex16LockTest {
    public static void main(String[] args) {
        LockTest1 st1 = new LockTest1();
        LockTest2 st2 = new LockTest2();

        new Thread(() -> st1.f1()).start();
        new Thread(() -> st1.g1()).start();
        new Thread(() -> st1.h1()).start();

        new Thread(() -> st2.f2()).start();
        new Thread(() -> st2.g2()).start();
        new Thread(() -> st2.h2()).start();
    }
}
