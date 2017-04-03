package ch21concurrency;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class TestCyclicBarrier {
    private static final int THREAD_NUMBER = 5;
    private static final Random RANDOM = new Random();
    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(THREAD_NUMBER, new Runnable() {
            public void run() {
                System.out.println(Thread.currentThread().getId() + "：我宣布，所有小伙伴写入数据完毕");
            }
        });
        for (int i = 0; i < THREAD_NUMBER; i++) {
            Thread t = new Thread(new Worker(barrier));
            t.start();
        }
    }
    static class Worker implements Runnable {
        private CyclicBarrier barrier;
        public Worker(CyclicBarrier barrier) {
            this.barrier = barrier;
        }
        public void run() {
            int time = RANDOM.nextInt(1000);
            System.out.println(Thread.currentThread().getId() + "：我需要" + time + "毫秒时间写入数据.");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + "：写入数据完毕，等待其他小伙伴...");
            try {
                barrier.await(); // 等待所有线程都调用过此函数才能进行后续动作
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getId() + "：所有线程都写入数据完毕，继续干活...");
        }
    }
}
