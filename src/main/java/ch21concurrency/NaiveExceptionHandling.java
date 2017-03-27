package ch21concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.print;

public class NaiveExceptionHandling {
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        try {
            exec.execute(new ExceptionThread());
        } catch (RuntimeException e) {
            print("Runtime Exception!");
            e.printStackTrace();
        }
    }
}
