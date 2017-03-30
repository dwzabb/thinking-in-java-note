package ch21concurrency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicInteger;

import static util.Print.print;

public class AtomicIntegerTest implements Runnable{
    private AtomicInteger ai = new AtomicInteger(0);

    public int getValue() {
        return ai.get();
    }

    public void evenIncrement() {
        ai.addAndGet(2);
    }
    @Override
    public void run() {
        while (true) {
            evenIncrement();
        }
    }

    public static void main(String[] args) {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                print("Time out");
                System.exit(0);
            }
        }, 5000);

        AtomicIntegerTest ait = new AtomicIntegerTest();
        new Thread(ait).start();

        while(true) {
            int val = ait.getValue();
            if (val % 2 == 1) {
                print("Not even! " + val);
                System.exit(0);
            }
        }
    }
}
