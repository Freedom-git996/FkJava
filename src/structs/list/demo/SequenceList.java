package structs.list.demo;

import java.util.Arrays;

public class SequenceList<T> {

    private static final int DEFAULT_CAPACITY = 16;
    private int capacity;
    private Object[] elementData;
    private int size = 0;

    public SequenceList() {
        capacity = DEFAULT_CAPACITY;
        elementData = new Object[capacity];
    }

    public SequenceList(T element) {
        this();
        elementData[0] = element;
        size ++;
    }

    public SequenceList(T element, int initSize) {
        capacity = 1;
        while(capacity < initSize) {
            capacity <<= 1;
        }
        elementData = new Object[capacity];
        elementData[0] = element;
        size ++;
    }

    public int length() {
        return size;
    }

    public T get(int index) {
        if(index < 0 && index > size - 1) {
            throw new IndexOutOfBoundsException("线性表索引越界");
        }
        return (T)elementData[index];
    }

    public int locate(T element) {
        for(int i = 0; i < size; i ++) {
            if(elementData[i].equals(element)) {
                return i;
            }
        }
        return -1;
    }

    public void insert(T element, int index) {
        if(index < 0 && index > size - 1) {
            throw new IndexOutOfBoundsException("线性表索引越界");
        }
        ensureCapacity(size + 1);
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size ++;
    }

    public void add(T element) {
        insert(element, size);
    }

    public T delete(int index) {
        if(index < 0 && index > size - 1) {
            throw new IndexOutOfBoundsException("线性表索引越界");
        }
        T oldVal = (T) elementData[index];
        int numMoved = size - index - 1;
        if(numMoved > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        }
        elementData[--size] = null;
        return oldVal;
    }

    public T remove() {
        return delete(size - 1);
    }

    public boolean empty() {
        return size == 0;
    }

    public void clear() {
        Arrays.fill(elementData, null);
        size = 0;
    }

    private void ensureCapacity(int minCapacity) {
        if(minCapacity > capacity) {
            while(capacity < minCapacity) {
                capacity <<= 1;
            }
            elementData = Arrays.copyOf(elementData, capacity);
        }
    }

    @Override
    public String toString() {
        if(size == 0) {
            return "[]";
        }else {
            StringBuilder sb = new StringBuilder("[");
            for(int i = 0; i < size; i ++) {
                sb.append(elementData[i].toString()).append(",");
            }
            int len = sb.length();
            return sb.delete(len - 1, len).append("]").toString();
        }
    }

    public static void main(String[] args) {
        SequenceList<String> list = new SequenceList<>();
        list.add("aaa");
        list.add("bbb");
        list.add("ccc");
        list.insert("ddd", 1);
        System.out.println(list);
        list.delete(2);
        System.out.println(list);
        System.out.println(list.locate("ccc"));
    }
}