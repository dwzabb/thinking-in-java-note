package ch21concurrency;

import java.util.concurrent.*;
import java.util.concurrent.locks.*;

import static util.Print.print;
import static util.Print.printnb;

class WaitPerson27 implements Runnable {
    private Ex27Restaurant restaurant;
    protected Lock lock = new ReentrantLock();
    protected Condition condition = lock.newCondition();
    public WaitPerson27(Ex27Restaurant r) { restaurant = r; }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                lock.lock();
                try {
                    while(restaurant.meal == null)
                        condition.await();
                } finally {
                    lock.unlock();
                }
                print("waitPerson got " + restaurant.meal);
                restaurant.chef.lock.lock();
                try {
                    restaurant.meal = null;
                    restaurant.chef.condition.signalAll();
                } finally {
                    restaurant.chef.lock.unlock();
                }
            }
        } catch(InterruptedException e) {
            print("WaitPerson27 interrupted");
        }
    }
}

class Chef27 implements Runnable {
    private Ex27Restaurant restaurant;
    private int count = 0;
    protected Lock lock = new ReentrantLock();
    protected Condition condition = lock.newCondition();
    public Chef27(Ex27Restaurant r) { restaurant = r; }
    public void run() {
        try {
            while(!Thread.interrupted()) {
                lock.lock();
                try {
                    while(restaurant.meal != null)
                        condition.await();
                } finally {
                    lock.unlock();
                }
                if(++count == 10) {
                    print("Out of food, closing");
                    restaurant.exec.shutdownNow();
                    return;
                }
                printnb("Order up! ");
                restaurant.waitPerson.lock.lock();
                try {
                    restaurant.meal = new Meal(count);
                    restaurant.waitPerson.condition.signalAll();
                } finally {
                    restaurant.waitPerson.lock.unlock();
                }
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch(InterruptedException e) {
            print("chef interrupted");
        }
    }
}

public class Ex27Restaurant {
    Meal meal;
    ExecutorService exec = Executors.newCachedThreadPool();
    WaitPerson27 waitPerson = new WaitPerson27(this);
    Chef27 chef = new Chef27(this);
    public Ex27Restaurant() {
        exec.execute(chef);
        exec.execute(waitPerson);
    }
    public static void main(String[] args) {
        new Ex27Restaurant();
    }
}

