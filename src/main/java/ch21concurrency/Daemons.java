package ch21concurrency;

import java.util.concurrent.TimeUnit;

class Daemon implements Runnable {
    Thread[] threads = new Thread[10];
    @Override
    public void run() {
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(new DaemonSpawn());
            threads[i].start();
            System.out.println("Daemon " + i + " started");
        }

        for (int i = 0; i < threads.length; i++) {
            System.out.println("Daemon " + i + " is daemon: " + threads[i].isDaemon());
        }
    }
}

class DaemonSpawn implements Runnable {

    @Override
    public void run() {
        while(true) {
            Thread.yield();
        }
    }
}
public class Daemons {
    public static void main(String[] args) throws InterruptedException {
        Thread t = new Thread(new Daemon());
        // All the sub threads will be daemon
        t.setDaemon(true);
        t.start();

        System.out.println("t.isDaemon() " + t.isDaemon());
        //TimeUnit.SECONDS.sleep(1);
        TimeUnit.NANOSECONDS.sleep(10);
    }
}/* Output
t.isDaemon() true
Daemon 0 started
Daemon 1 started
Daemon 2 started
Daemon 3 started
Daemon 4 started
Daemon 5 started
Daemon 6 started
Daemon 7 started
Daemon 8 started
Daemon 9 started
Daemon 0 is daemon: true
Daemon 1 is daemon: true
Daemon 2 is daemon: true
Daemon 3 is daemon: true
Daemon 4 is daemon: true
Daemon 5 is daemon: true
Daemon 6 is daemon: true
Daemon 7 is daemon: true
Daemon 8 is daemon: true
Daemon 9 is daemon: true

Process finished with exit code 0
*/
