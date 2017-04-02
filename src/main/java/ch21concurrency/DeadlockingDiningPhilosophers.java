package ch21concurrency;

import java.io.IOException;
import java.util.concurrent.*;

import static util.Print.print;

class Chopstick{
    private boolean taken = false;

    public synchronized void take() throws InterruptedException {
        while (taken)
            wait();

        taken = true;
    }

    public synchronized void drop() {
        taken = false;
        notifyAll();
    }
}

class Philosopher implements Runnable{
    private Chopstick left;
    private Chopstick right;
    private final int id;
    private ThreadLocalRandom random = ThreadLocalRandom.current();

    public Philosopher(Chopstick left,
                       Chopstick right,
                       int id) {
        this.left = left;
        this.right = right;
        this.id = id;
    }

    public void pause() throws InterruptedException {
        TimeUnit.MILLISECONDS.sleep(random.nextInt(300));
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                print(this + " is thinking");
                pause();

                print(this + " grabbing left");
                left.take();

                // 增加死锁的概率
                pause();

                print(this + " grabbing right");
                right.take();

                // Eat
                print(this + " is eating");
                pause();

                print(this + " dropping both chopsticks");
                left.drop();
                right.drop();
            }
        } catch (InterruptedException e) {
            print("Philosopher run() was interrupted");
        }
    }

    @Override
    public String toString() {
        return "Philosopher " + id;
    }
}
public class DeadlockingDiningPhilosophers {
    public static void main(String[] args) throws IOException {
        int size = 5;
        Chopstick[] chopsticks = new Chopstick[size];
        for (int i = 0; i < size; i++) {
            chopsticks[i] = new Chopstick();
        }

        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < size; i++) {
            exec.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1) % size], i));
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exec.shutdownNow();
    }
}/* Output
Philosopher 1 is thinking
Philosopher 0 is thinking
Philosopher 4 is thinking
Philosopher 2 is thinking
Philosopher 3 is thinking
Philosopher 1 grabbing left
Philosopher 3 grabbing left
Philosopher 4 grabbing left
Philosopher 0 grabbing left
Philosopher 2 grabbing left
Philosopher 1 grabbing right
Philosopher 4 grabbing right
Philosopher 3 grabbing right
Philosopher 0 grabbing right
Philosopher 2 grabbing right
Philosopher run() was interrupted
Philosopher run() was interrupted
Philosopher run() was interrupted
Philosopher run() was interrupted
Philosopher run() was interrupted
*/
