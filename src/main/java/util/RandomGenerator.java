package util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by DengWenzhe on 3/18/17.
 */
public class RandomGenerator {
    private static ThreadLocalRandom random = ThreadLocalRandom.current();

    private static char[] chars = ("abcdefghijklmnopqrstuvwxyz"
            + "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();

    public static class Boolean implements Generator<java.lang.Boolean> {
        public java.lang.Boolean next() {
            return random.nextBoolean();
        }
    }

    public static class Byte implements Generator<java.lang.Byte> {
        public java.lang.Byte next() {
            return (byte) random.nextInt();
        }
    }

    public static class Character implements Generator<java.lang.Character> {
        public java.lang.Character next() {
            return chars[random.nextInt(chars.length)];
        }
    }

    public static class String implements Generator<java.lang.String>{
        private int length = 7;

        public String() {}

        public String(int length) {
            this.length = length;
        }

        @Override
        public java.lang.String next() {
            char[] buf = new char[length];
            for (int i = 0; i < length; i++) {
                buf[i] = chars[random.nextInt(length)];
            }
            return new java.lang.String(buf);
        }
    }

    public static class Short implements Generator<java.lang.Short> {
        public java.lang.Short next() {
            return (short) random.nextInt();
        }
    }

    public static class Integer implements Generator<java.lang.Integer> {
        private int mod = 10000;

        public Integer() {
        }

        public Integer(int modulo) {
            mod = modulo;
        }

        public java.lang.Integer next() {
            return random.nextInt(mod);
        }
    }

    public static class Long implements Generator<java.lang.Long> {
        private int mod = 10000;

        public Long() {
        }

        public Long(int modulo) {
            mod = modulo;
        }

        public java.lang.Long next() {
            return new java.lang.Long(random.nextInt(mod));
        }
    }

    public static class Float implements Generator<java.lang.Float> {
        public java.lang.Float next() {
            return random.nextFloat();
        }
    }

    public static class Double implements Generator<java.lang.Double> {
        public java.lang.Double next() {
            return random.nextDouble();
        }
    }
}
