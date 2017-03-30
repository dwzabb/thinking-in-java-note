package ch21concurrency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static util.Print.print;

class Pair {
    int x, y;
    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Pair() {
        this(0, 0);
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public void incrementX() { x++; }

    public void incrementY() { y++; }

    public String toString() {
        return "x: " + x + ", y: " + y;
    }

    public class PairValueNotEqualException
    extends RuntimeException {
        public PairValueNotEqualException() {
            super("Pair value not equal: " + Pair.this);
        }
    }

    public void checkState() {
        if (x != y)
            throw new PairValueNotEqualException();
    }
}

abstract class PairManager {
    protected  Pair p = new Pair();
    AtomicInteger checkCounter = new AtomicInteger(0);
    private List<Pair> storage = Collections.synchronizedList(new ArrayList<Pair>());

    public synchronized Pair getPair() {
        return new Pair(p.x, p.y);
    }

    public void store(Pair p) {
        storage.add(p);
        try {
            TimeUnit.MILLISECONDS.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public abstract void increment();
}

class PairManager1 extends PairManager {
    @Override
    public synchronized void increment() {
        p.incrementX();
        p.incrementY();
        checkCounter.incrementAndGet();
        p.checkState();
        store(getPair());
    }
}

class PairManager2 extends PairManager {
    @Override
    public void increment() {
        Pair temp = null;
        synchronized (this) {
            p.incrementX();
            p.incrementY();
            checkCounter.incrementAndGet();
            p.checkState();
            temp = getPair();
        }
        store(temp);
    }
}

class PairManipulator implements Runnable{
    private PairManager pm;
    public PairManipulator(PairManager pm) {
        this.pm = pm;
    }

    @Override
    public void run() {
        while(true)
            pm.increment();
    }

    @Override
    public String toString() {
        return "Pair " + pm.getPair() +
                ", checkCounter = " + pm.checkCounter.get();
    }
}
public class CriticalSection {
    static void testTwoApproaches(PairManager pm1, PairManager pm2) {
        ExecutorService exec = Executors.newCachedThreadPool();
        PairManipulator manipulator1 = new PairManipulator(pm1);
        PairManipulator manipulator2 = new PairManipulator(pm2);

        exec.execute(manipulator1);
        exec.execute(manipulator2);

        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        print("manipulator1 " + manipulator1 +
              ", manipulator2 " + manipulator2);
        System.exit(0);
    }

    public static void main(String[] args) {
        PairManager
                pm1 = new PairManager1(),
                pm2 = new PairManager2();

        testTwoApproaches(pm1, pm2);
    }
}
