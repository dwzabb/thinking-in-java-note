package ch21concurrency;

import java.util.concurrent.*;

import static util.Print.print;
import static util.Print.printnb;

class Sender implements Runnable {
    private BlockingQueue<Character> queue;
    public Sender(BlockingQueue<Character> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                for(char c = 'A'; c <= 'Z'; c++) {
                    queue.put(c);
                    TimeUnit.MILLISECONDS.sleep(500);
                }
            }
        } catch (InterruptedException e) {
            print("Sender run() was interrupted");
        }
        print("Exiting Sender ()");
    }
}

class Receiver implements Runnable {
    private BlockingQueue<Character> queue;
    public Receiver(BlockingQueue<Character> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while(!Thread.interrupted()) {
                print("Read :" + queue.take());
            }
        } catch (InterruptedException e) {
            print("Interrupted BlockingQueue.take()");
        }
        print("Exiting Receiver run()");
    }
}
public class Ex30PipeIOUsingBlockingQueue {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        //BlockingQueue<Character> queue = new LinkedBlockingDeque<>();
        BlockingQueue<Character> queue = new ArrayBlockingQueue<Character>(3);
        exec.execute(new Sender(queue));
        TimeUnit.SECONDS.sleep(3);
        exec.execute(new Receiver(queue));

        TimeUnit.SECONDS.sleep(3);
        exec.shutdownNow();
    }
}
