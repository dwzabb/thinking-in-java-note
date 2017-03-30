package ch21concurrency;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Ex14Timer implements Runnable{
    private static int timers = 0;
    private static int tasks = 0;
    public void run() {
        try {
            while(timers < 2000) { // create 2000 Timers
                ++timers;
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    public void run() {
                        ++tasks;
                        if(timers % 100 == 0)
                            System.out.println(timers + " timers did "
                                    + tasks + " tasks");
                    }
                }, 0);
                try {
                    TimeUnit.MILLISECONDS.sleep(30); // time to do task
                } catch(InterruptedException e) {
                    System.out.println("Sleep interrupted");
                }
                t.cancel();
            }
        } finally {
            System.out.println("Done. " + timers + " timers completed "
                    + tasks + " tasks");
        }
    }
    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new Ex14Timer());
        exec.shutdown();
    }
} /* Output:
100 timers did 100 tasks
200 timers did 200 tasks
300 timers did 300 tasks
400 timers did 400 tasks
500 timers did 500 tasks
600 timers did 600 tasks
700 timers did 700 tasks
800 timers did 800 tasks
900 timers did 900 tasks
1000 timers did 1000 tasks
1100 timers did 1100 tasks
1200 timers did 1200 tasks
1300 timers did 1300 tasks
1400 timers did 1400 tasks
1500 timers did 1500 tasks
1600 timers did 1600 tasks
1700 timers did 1700 tasks
1800 timers did 1800 tasks
1900 timers did 1900 tasks
2000 timers did 2000 tasks
Done. 2000 timers completed 2000 tasks
*/