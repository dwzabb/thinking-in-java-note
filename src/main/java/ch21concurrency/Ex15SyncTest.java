package ch21concurrency;

import static util.Print.print;

class SyncTest1 {
    private static final int LOOP_SIZE = 10;
    public void f1() {
        synchronized (this) {
            for (int i = 0; i < LOOP_SIZE; i++) {
                print("f1()");
                Thread.yield();
            }
        }
    }

    public void g1() {
        synchronized (this) {
            for (int i = 0; i < LOOP_SIZE; i++) {
                print("g1()");
                Thread.yield();
            }
        }
    }

    public void h1() {
        synchronized (this) {
            for (int i = 0; i < LOOP_SIZE; i++) {
                print("h1()");
                Thread.yield();
            }
        }
    }
}

class SyncTest2 {
    private static final int LOOP_SIZE = 10;
    private Object syncObj1 = new Object();
    private Object syncObj2 = new Object();
    private Object syncObj3 = new Object();

    public void f2() {
        synchronized (syncObj1) {
            for (int i = 0; i < LOOP_SIZE; i++) {
                print("f2()");
                Thread.yield();
            }
        }
    }

    public void g2() {
        synchronized (syncObj2) {
            for (int i = 0; i < LOOP_SIZE; i++) {
                print("g2()");
                Thread.yield();
            }
        }
    }

    public void h2() {
        synchronized (syncObj3) {
            for (int i = 0; i < LOOP_SIZE; i++) {
                print("h2()");
                Thread.yield();
            }
        }
    }
}
public class Ex15SyncTest {
    public static void main(String[] args) {
        SyncTest1 st1 = new SyncTest1();
        SyncTest2 st2 = new SyncTest2();

        new Thread(() -> st1.f1()).start();
        new Thread(() -> st1.g1()).start();
        new Thread(() -> st1.h1()).start();

        new Thread(() -> st2.f2()).start();
        new Thread(() -> st2.g2()).start();
        new Thread(() -> st2.h2()).start();
    }
}
