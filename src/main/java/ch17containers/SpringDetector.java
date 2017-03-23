package ch17containers;

import java.util.*;
import java.lang.reflect.Constructor;

public class SpringDetector {
    public static <T extends Groundhog>
    void detectSpring(Class<T> type) throws Exception {
        Constructor<T> constructor = type.getConstructor(int.class);
        Map<Groundhog, Prediction> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.put(constructor.newInstance(i), new Prediction());
        }

        System.out.println("Map " + map);

        Groundhog gh = constructor.newInstance(3);
        System.out.println("Looking up prediction for " + gh);
        if (map.containsKey(gh))
            System.out.println(map.get(gh));
        else
            System.out.println("Key not found " + gh);
    }

    public static void main(String[] args) throws Exception {
        detectSpring(Groundhog.class);
    }
} /* Output
Map {Groundhog #0=Early Spring!, Groundhog #5=Early Spring!, Groundhog #6=Six more weeks of Winter!, Groundhog #1=Six more weeks of Winter!, Groundhog #3=Early Spring!, Groundhog #7=Six more weeks of Winter!, Groundhog #4=Six more weeks of Winter!, Groundhog #8=Six more weeks of Winter!, Groundhog #9=Six more weeks of Winter!, Groundhog #2=Six more weeks of Winter!}
Looking up prediction for Groundhog #3
Key not found Groundhog #3
*/
