package ch21concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import static util.Print.print;


class ExceptionThread2 implements Runnable {
    @Override
    public void run() {
        Thread t = Thread.currentThread();
        print("run() by " + t);
        print("eh = " + t.getUncaughtExceptionHandler());
        throw new RuntimeException();
    }
}

class MyUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        print(" caught " + e);
    }
}

class HandlerThreadFactory implements ThreadFactory {
    //private MyUncaughtExceptionHandler eh = new MyUncaughtExceptionHandler();
    @Override
    public Thread newThread(Runnable r) {
        print("Creating new thread");
        Thread t = new Thread(r);
        print("Created " + t);
        t.setUncaughtExceptionHandler(new MyUncaughtExceptionHandler());
        print(" setUncaughtExceptionHandler " + t.getUncaughtExceptionHandler()
              + " on " + t);
        return t;
    }
}
public class CaptureUncaughtException {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool(new HandlerThreadFactory());
        exec.execute(new ExceptionThread2());
    }
}/* Output doesn't match with the output on the BOOK! Two threads were created
Creating new thread
Created Thread[Thread-0,5,main]
 setUncaughtExceptionHandler ch21concurrency.MyUncaughtExceptionHandler@66d3c617 on Thread[Thread-0,5,main]
run() by Thread[Thread-0,5,main]
eh = ch21concurrency.MyUncaughtExceptionHandler@66d3c617
Creating new thread
Created Thread[Thread-1,5,main]
 setUncaughtExceptionHandler ch21concurrency.MyUncaughtExceptionHandler@782b1823 on Thread[Thread-1,5,main]
 caught java.lang.RuntimeException
 */

