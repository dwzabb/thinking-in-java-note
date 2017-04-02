package ch21concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class FixedDiningPhilosophers {
    public static void main(String[] args) {
        int size = 5;
        Chopstick[] chopsticks = new Chopstick[size];
        for (int i = 0; i < size; i++) {
            chopsticks[i] = new Chopstick();
        }

        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < size; i++) {
            if(i < size - 1)
                exec.execute(new Philosopher(chopsticks[i], chopsticks[(i + 1) % size], i));
            else
                // 让最后一位哲学家以相反的顺序拿筷子可防止死锁
                exec.execute(new Philosopher(chopsticks[(i + 1) % size], chopsticks[i], i));
        }

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        exec.shutdownNow();
    }
}/* Output
无死锁
*/
