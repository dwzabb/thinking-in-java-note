package ch21concurrency;

import static util.Print.print;

class Sleeper extends Thread {
    private int duration;
    public Sleeper(String name, int duration) {
        super(name);
        this.duration = duration;
        start();
    }

    @Override
    public void run() {
        try {
            sleep(duration);
        } catch (InterruptedException e) {
            System.out.println(getName() + " was interrupted. " +
            "isInterrupted() " + isInterrupted());
            //e.printStackTrace();
            return;
        }
        System.out.println(getName() + " has awaked.");
    }
}

class Joiner extends Thread {
    Sleeper sleeper;
    public Joiner(String name, Sleeper sleeper) {
        super(name);
        this.sleeper = sleeper;
        start();
    }

    @Override
    public void run() {
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            print(getName() + "'s sleeper was interrupted ");
            e.printStackTrace();
        }
        print(getName() + " is completed.");
    }
}

public class Joining {
    public static void main(String[] args) {
        Sleeper s1 = new Sleeper("s1", 1500);
        Sleeper s2 = new Sleeper("s2", 1500);

        Joiner j1 = new Joiner("j1", s1);
        Joiner j2 = new Joiner("j2", s2);

        s2.interrupt();
    }
}
