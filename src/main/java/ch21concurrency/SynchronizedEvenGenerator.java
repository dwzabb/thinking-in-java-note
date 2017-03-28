package ch21concurrency;

public class SynchronizedEvenGenerator extends IntGenerator {
    // volatile doesn't make any difference
    private /* volatile */ int currentEvenValue = 0;
    @Override
    public synchronized int next() {
        ++currentEvenValue;
        Thread.yield();
        ++currentEvenValue;

        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new SynchronizedEvenGenerator(), 10);
    }
}/* Output:
infinite loop....
*/
