package ch17containers;

import util.MapEntry;
import java.util.*;

public class SimpleHashMap22<K,V> extends AbstractMap<K,V> {
    // Choose a prime number for the hash table
    // size, to achieve a uniform distribution:
    static final int SIZE = 997;
    // You can't have a physical array of generics,
    // but you can upcast to one:
    @SuppressWarnings("unchecked")
    LinkedList<MapEntry<K,V>>[] buckets =
            new LinkedList[SIZE];
    public V put(K key, V value) {
        V oldValue = null;
        int index = Math.abs(key.hashCode()) % SIZE;
        if(buckets[index] == null)
            buckets[index] = new LinkedList<MapEntry<K,V>>();
        LinkedList<MapEntry<K,V>> bucket = buckets[index];
        MapEntry<K,V> pair = new MapEntry<K,V>(key, value);
        boolean found = false;
        ListIterator<MapEntry<K,V>> it = bucket.listIterator();
        while(it.hasNext()) {
            MapEntry<K,V> iPair = it.next();
            System.out.print("报告！发现冲突! " + iPair);
            if(iPair.getKey().equals(key)) {
                oldValue = iPair.getValue();
                it.set(pair); // Replace old with new
                found = true;
                break;
            }
        }
        if(!found)
            buckets[index].add(pair);
        return oldValue;
    }
    public V get(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if(buckets[index] == null) return null;
        for(MapEntry<K,V> iPair : buckets[index])
            if(iPair.getKey().equals(key))
                return iPair.getValue();
        return null;
    }
    public Set<Entry<K,V>> entrySet() {
        Set<Map.Entry<K,V>> set= new HashSet<Map.Entry<K,V>>();
        for(LinkedList<MapEntry<K,V>> bucket : buckets) {
            if(bucket == null) continue;
            for(MapEntry<K,V> mpair : bucket)
                set.add(mpair);
        }
        return set;
    }

    @Override
    public void clear() {
        Arrays.fill(buckets, null);
    }

    @Override
    public V remove(Object key) {
        int index = Math.abs(key.hashCode()) % SIZE;
        if (buckets[index] == null)
            return null;

        ListIterator<MapEntry<K, V>> iter = buckets[index].listIterator();
        while (iter.hasNext()) {
            MapEntry<K, V> entry = iter.next();
            if (entry.getKey().equals(key)) {
                iter.remove();
                return entry.getValue();
            }
        }

        return null;
    }

    public static void main(String[] args) {
        SimpleHashMap22<String,String> map =
                new SimpleHashMap22<String,String>();
        map.put("Hello", "World");
        map.put("Thinking", "In Java");
        map.put("I like ", "Java");
        System.out.println("Map: " + map);

        map.remove("Thinking");
        System.out.println("After remove Thinking: " + map);

        map.clear();
        System.out.println("After clear: " + map);
    }
}