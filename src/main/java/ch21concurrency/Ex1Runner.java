package ch21concurrency;

public class Ex1Runner implements Runnable {
    private static int runnerCount = 0;
    private final int runnerId = runnerCount++;

    public Ex1Runner() {
        System.out.println("Constructing Runner " + runnerId);
    }

    @Override
    public void run() {
        for (int i = 0;i < 3; i++) {
            System.out.println("Hi from Runner " + runnerId);
            Thread.yield();
        }
        System.out.println("Completing Runner " + runnerId);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            new Thread(new Ex1Runner()).start();
        }
    }
}
