package ch21concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExceptionThread implements Runnable {
    @Override
    public void run() {
        throw new RuntimeException();
    }

    public static void main(String[] args) {
        new Thread(new ExceptionThread()).start();

        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ExceptionThread());
    }
}/* Output
Exception in thread "Thread-0" java.lang.RuntimeException
	at ch21concurrency.ExceptionThread.run(ExceptionThread.java:9)
	at java.lang.Thread.run(Thread.java:745)
Exception in thread "pool-1-thread-1" java.lang.RuntimeException
	at ch21concurrency.ExceptionThread.run(ExceptionThread.java:9)
	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1142)
	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:617)
	at java.lang.Thread.run(Thread.java:745)
*/