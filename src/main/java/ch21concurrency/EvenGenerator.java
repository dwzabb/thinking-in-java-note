package ch21concurrency;

public class EvenGenerator extends IntGenerator {
    private int currentEvenValue = 0;
    @Override
    public int next() {
        ++currentEvenValue; // Danger point!!
        ++currentEvenValue;
        return currentEvenValue;
    }

    public static void main(String[] args) {
        EvenChecker.test(new EvenGenerator(), 10);
    }
} /* Output
987 is not even!
991 is not even!
981 is not even!
989 is not even!
985 is not even!
983 is not even!
979 is not even!
*/
