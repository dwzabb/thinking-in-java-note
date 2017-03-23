package ch17containers;

import java.util.concurrent.ThreadLocalRandom;

public class Prediction {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();
    private boolean shadow = random.nextBoolean();

    @Override
    public String toString() {
        if (shadow)
            return "Six more weeks of Winter!";
        else
            return "Early Spring!";
    }
}
