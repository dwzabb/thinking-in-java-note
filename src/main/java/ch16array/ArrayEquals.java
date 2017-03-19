package ch16array;

import java.util.Arrays;

/**
 * Created by DengWenzhe on 3/9/17.
 */
public class ArrayEquals {
    int val;

    public ArrayEquals(int val) {
        this.val = val;
    }
}

class ArrayEqualsOverride {
    int val;

    public ArrayEqualsOverride(int val) {
        this.val = val;
    }

    @Override
    public boolean equals(Object another) {
        if (another == null)
            return false;

        ArrayEqualsOverride other;
        if (another instanceof ArrayEqualsOverride)
            other = (ArrayEqualsOverride) another;
        else
            return false;

        return other.val == this.val;
    }
}

class ArrayEqualsTester {

    public static void main(String[] args) {
        ArrayEquals[] a = new ArrayEquals[] {
                new ArrayEquals(1),
                new ArrayEquals(2),
                new ArrayEquals(3)
        };

        ArrayEquals[] b = new ArrayEquals[] {
                new ArrayEquals(1),
                new ArrayEquals(2),
                new ArrayEquals(3)
        };

        System.out.println("a equals b " + Arrays.equals(a, b));

        ArrayEqualsOverride[] c = new ArrayEqualsOverride[] {
                new ArrayEqualsOverride(1),
                new ArrayEqualsOverride(2),
                new ArrayEqualsOverride(3)
        };

        ArrayEqualsOverride[] d = new ArrayEqualsOverride[] {
                new ArrayEqualsOverride(1),
                new ArrayEqualsOverride(2),
                new ArrayEqualsOverride(3)
        };

        System.out.println("c equals d " + Arrays.equals(c, d));
    }
}
