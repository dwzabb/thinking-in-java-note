package ch17containers;

public class Groundhog2 extends Groundhog{

    public Groundhog2(int n) {
        super(n);
    }

    @Override
    public int hashCode() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Groundhog2 &&
                ((Groundhog2) o).number == this.number;
    }
}
