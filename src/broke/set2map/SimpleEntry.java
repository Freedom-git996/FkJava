package broke.set2map;

import java.util.Map;

public class SimpleEntry<K, V> implements Map.Entry<K, V> {

    private final K k;
    private V v;

    public SimpleEntry(K k, V v) {
        this.k = k;
        this.v = v;
    }

    public SimpleEntry(Map.Entry<? extends K, ? extends V> entry) {
        this.k = entry.getKey();
        this.v = entry.getValue();
    }

    @Override
    public K getKey() {
        return k;
    }

    @Override
    public V getValue() {
        return v;
    }

    @Override
    public V setValue(V value) {
        V oldVal = this.v;
        this.v = value;
        return oldVal;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj != null && obj.getClass() == SimpleEntry.class) {
            SimpleEntry simpleEntryObj = (SimpleEntry)obj;
            if(!simpleEntryObj.getKey().equals(this.k)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.k == null ? 0 : this.k.hashCode();
    }

    @Override
    public String toString() {
        return "[key]: " + getKey() + " [value]: " + getValue();
    }
}
