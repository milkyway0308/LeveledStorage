package milkyway.LeveledStorage.Util;


import java.util.Map;

public class SimpleEntry<K, V> implements Map.Entry {
    public SimpleEntry(K key, V value) {
        k = key;
        v = value;
    }

    private K k;
    private V v;

    @Override
    public K getKey() {
        return k;
    }

    @Override
    public V getValue() {
        return v;
    }

    @Override
    public Object setValue(Object value) {
        v = (V) value;
        return v;
    }
}
