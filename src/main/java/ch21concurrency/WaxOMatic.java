package ch21concurrency;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static util.Print.print;

class Car {
    private boolean waxed = false;

    public synchronized void waxed() {
        waxed = true;
        notifyAll();
    }

    public synchronized void buffed() {
        waxed = false;
        notifyAll();
    }

    public synchronized void waitForWaxing() throws InterruptedException {
        while(waxed == false) {
            wait();
        }
    }

    public synchronized void waitForBuffing() throws InterruptedException {
        while(waxed == true) {
            wait();
        }
    }
}

class WaxOn implements Runnable {
    private Car car;

    public WaxOn(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
//        try {
            while (!Thread.interrupted()) {
                try {
                    print("Wax On!");
                    TimeUnit.SECONDS.sleep(1);
                    car.waxed();
                    car.waitForBuffing();
                } catch (InterruptedException e) {
                    print("Received interruption");
                }
            }
//        } catch (InterruptedException e) {
//            print("Exiting via Interrupt");
//        }
        print("Ending Wax On task");
    }
}

class WaxOff implements Runnable {
    private Car car;
    public WaxOff(Car car) {
        this.car = car;
    }

    @Override
    public void run() {
        //try {
            while(!Thread.interrupted()) {
                try {
                    car.waitForWaxing();
                    print("Wax Off!");
                    TimeUnit.SECONDS.sleep(1);
                    car.buffed();
                } catch (InterruptedException e) {
                    print("Received interruption");
                }
            }
//        } catch (InterruptedException e) {
//            print("Exiting via interrupt");
//        }
        print("Ending Wax Off task");
    }
}
public class WaxOMatic {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        Car car = new Car();

        exec.execute(new WaxOff(car));
        exec.execute(new WaxOn(car));

        TimeUnit.SECONDS.sleep(5);
        exec.shutdownNow();// 如果任务ignore interruption, shutdownNow()无法停止线程
        //exec.shutdown(); // shutdown并不会关闭已经运行的线程，只会停止创建新线程
    }
}
