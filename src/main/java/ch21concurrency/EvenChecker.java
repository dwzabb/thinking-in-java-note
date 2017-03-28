package ch21concurrency;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static util.Print.print;

public class EvenChecker implements Runnable {
    private IntGenerator generator;
    private final int id;

    public EvenChecker(IntGenerator generator, int id) {
        this.generator = generator;
        this.id = id;
    }

    @Override
    public void run() {
        while (!generator.isCancel()) {
            int val = generator.next();
            if (val % 2 == 1) {
                print(val + " is not even!");
                generator.cancel();
            }
        }
    }

    public static void test(IntGenerator generator, int threadCount) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < threadCount; i++) {
            exec.execute(new EvenChecker(generator, i));
        }
        exec.shutdown();
    }
}
