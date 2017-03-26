package ch21concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

class TaskWithResult implements Callable<String> {
    private static int taskCount = 0;
    private final int taskId = taskCount++;

    @Override
    public String call() throws Exception {
        return "Result of task " + taskId;
    }
}
public class CallableDemo {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newCachedThreadPool();
        List<Future<String>> results = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            results.add(executor.submit(new TaskWithResult()));
        }

        for (Future<String> result: results) {
            try {
                System.out.println(result.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }
}
