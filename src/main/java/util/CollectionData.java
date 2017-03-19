package util;

/**
 * Created by DengWenzhe on 3/18/17.
 */
import java.util.ArrayList;

public class CollectionData<T> extends ArrayList<T> {
    public CollectionData(Generator<T> gen, int quantity) {
        for (int i = 0; i < quantity; i++)
            add(gen.next());
    }

    // A generic convenience method:
    public static <T> CollectionData<T> list(Generator<T> gen, int quantity) {
        return new CollectionData<T>(gen, quantity);
    }
}

class CollectionDataTest {
    public static void main(String[] args) {
        ArrayList<String> list = new CollectionData(new RandomGenerator.String(9), 10);
        System.out.println(list);
    }
}