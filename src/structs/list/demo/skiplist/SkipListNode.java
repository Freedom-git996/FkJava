package structs.list.demo.skiplist;

public class SkipListNode<T> {

    public int key;

    public T value;

    public SkipListNode<T> up, down, left, right;

    public static final int HEAD_KEY =Integer.MAX_VALUE;

    public static final int TAIL_KEY = Integer.MIN_VALUE;

    public SkipListNode(int k, T v) {
        key = k;
        value = v;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if(obj != null && obj.getClass() == getClass()) {
            SkipListNode objNode = (SkipListNode)obj;
            if(getKey() == objNode.getKey() && getValue() == objNode.getValue()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "key-value: " + key + "-" + value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
