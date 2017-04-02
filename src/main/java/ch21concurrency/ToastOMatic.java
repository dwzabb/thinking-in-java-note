package ch21concurrency;

import java.util.Random;
import java.util.concurrent.*;

import static util.Print.print;

class Toast {
    public enum Status { DRY, BUTTERED, JAMMED }
    private Status status = Status.DRY;
    private final int id;
    public Toast(int idn) { id = idn; }
    public void butter() { status = Status.BUTTERED; }
    public void jam() { status = Status.JAMMED; }
    public Status getStatus() { return status; }
    public int getId() { return id; }
    public String toString() {
        return "Toast " + id + ": " + status;
    }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {}

class Toaster implements Runnable {
    private ToastQueue toastQueue;
    private int count = 0;
    private Random rand = new Random(47);
    public Toaster(ToastQueue tq) { toastQueue = tq; }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                TimeUnit.MILLISECONDS.sleep(
                        100 + rand.nextInt(500));
                // Make toast
                Toast t = new Toast(count++);
                print(t);
                // Insert into queue
                toastQueue.put(t);
            }
        } catch(InterruptedException e) {
            print("Toaster interrupted");
        }
        print("Toaster off");
    }
}

// Apply butter to toast:
class Butterer implements Runnable {
    private ToastQueue dryQueue, butteredQueue;
    public Butterer(ToastQueue dry, ToastQueue buttered) {
        dryQueue = dry;
        butteredQueue = buttered;
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // Blocks until next piece of toast is available:
                Toast t = dryQueue.take();
                t.butter();
                print(t);
                butteredQueue.put(t);
            }
        } catch(InterruptedException e) {
            print("Butterer interrupted");
        }
        print("Butterer off");
    }
}

// Apply jam to buttered toast:
class Jammer implements Runnable {
    private ToastQueue butteredQueue, finishedQueue;
    public Jammer(ToastQueue buttered, ToastQueue finished) {
        butteredQueue = buttered;
        finishedQueue = finished;
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // Blocks until next piece of toast is available:
                Toast t = butteredQueue.take();
                t.jam();
                print(t);
                finishedQueue.put(t);
            }
        } catch(InterruptedException e) {
            print("Jammer interrupted");
        }
        print("Jammer off");
    }
}

// Consume the toast:
class Eater implements Runnable {
    private ToastQueue finishedQueue;
    private int counter = 0;
    public Eater(ToastQueue finished) {
        finishedQueue = finished;
    }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                // Blocks until next piece of toast is available:
                Toast t = finishedQueue.take();
                // Verify that the toast is coming in order,
                // and that all pieces are getting jammed:
                if(t.getId() != counter++ ||
                        t.getStatus() != Toast.Status.JAMMED) {
                    print(">>>> Error: " + t);
                    System.exit(1);
                } else
                    print("Chomp! " + t);
            }
        } catch(InterruptedException e) {
            print("Eater interrupted");
        }
        print("Eater off");
    }
}

public class ToastOMatic {
    public static void main(String[] args) throws Exception {
        ToastQueue dryQueue = new ToastQueue(),
                butteredQueue = new ToastQueue(),
                finishedQueue = new ToastQueue();
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Toaster(dryQueue));
        exec.execute(new Butterer(dryQueue, butteredQueue));
        exec.execute(new Jammer(butteredQueue, finishedQueue));
        exec.execute(new Eater(finishedQueue));
        TimeUnit.SECONDS.sleep(3);
        exec.shutdownNow();
    }
}/* Output
Toast 0: DRY
Toast 0: BUTTERED
Toast 0: JAMMED
Chomp! Toast 0: JAMMED
Toast 1: DRY
Toast 1: BUTTERED
Toast 1: JAMMED
Chomp! Toast 1: JAMMED
Toast 2: DRY
Toast 2: BUTTERED
Toast 2: JAMMED
Chomp! Toast 2: JAMMED
Toast 3: DRY
Toast 3: BUTTERED
Toast 3: JAMMED
Chomp! Toast 3: JAMMED
Toast 4: DRY
Toast 4: BUTTERED
Toast 4: JAMMED
Chomp! Toast 4: JAMMED
Toast 5: DRY
Toast 5: BUTTERED
Toast 5: JAMMED
Chomp! Toast 5: JAMMED
Toast 6: DRY
Toast 6: BUTTERED
Toast 6: JAMMED
Chomp! Toast 6: JAMMED
Eater interrupted
Eater off
Jammer interrupted
Jammer off
Butterer interrupted
Butterer off
Toaster interrupted
Toaster off
*/