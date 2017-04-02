package ch21concurrency;

import java.util.concurrent.*;

import static util.Print.print;

class LiftOffRunner implements Runnable {
    private BlockingQueue<LiftOff> rockets;
    public LiftOffRunner(BlockingQueue<LiftOff> queue) {
        this.rockets = queue;
    }

    public void add(LiftOff lo) {
        try {
            rockets.put(lo);
        } catch (InterruptedException e) {
            print("put was interrupted");
        }
    }

    @Override
    public void run() {
        print("Runner start running");
        try {
            while (!Thread.interrupted()) {
                LiftOff rocket = rockets.take();
                rocket.run();
            }
        } catch (InterruptedException e) {
            print("interruption during take()");
        }
        print("Exiting LiftOffRunner run()");
    }
}
public class TestingBlockingQueues {
    static void test(String msg, BlockingQueue<LiftOff> queue) throws InterruptedException {
        print(msg);
        LiftOffRunner runner = new LiftOffRunner(queue);
        Thread t = new Thread(runner);
        t.start();

        TimeUnit.SECONDS.sleep(2);
        for (int i = 0; i < 10; i++) {
            runner.add(new LiftOff(5));
        }

        TimeUnit.SECONDS.sleep(2);
        print("Interrupting " + msg);
        t.interrupt();
        print("Finish testing " + msg);
    }

    public static void main(String[] args) throws InterruptedException {
        test("LinkedBlockingQueue", // Unlimited size
                new LinkedBlockingQueue<LiftOff>());
        TimeUnit.SECONDS.sleep(2);
        test("ArrayBlockingQueue", // Fixed size
                new ArrayBlockingQueue<LiftOff>(3));
        TimeUnit.SECONDS.sleep(2);
        test("SynchronousQueue", // Size of 1
                new SynchronousQueue<LiftOff>());
    }
}
