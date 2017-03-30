package ch21concurrency;

import static util.Print.print;

public class DualSync {
    private Object syncObj = new Object();
    private static final int LOOP_SIZE = 10;
    public synchronized void f() {
        for (int i = 0; i < LOOP_SIZE; i++) {
            print("f()");
            Thread.yield();
        }
    }

    public void g() {
        synchronized (syncObj) {
            for (int i = 0; i < LOOP_SIZE; i++) {
                print("g()");
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        DualSync ds = new DualSync();
        new Thread(() -> ds.f()).start();

        ds.g();
    }
}/* Output
g()
f()
f()
f()
g()
g()
f()
g()
g()
g()
g()
g()
g()
g()
f()
f()
f()
f()
f()
f()
*/