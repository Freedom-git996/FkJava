package structs.stack.demo;

import java.util.Arrays;

public class SequenceStack<T> {

    private int DEFAULT_SIZE = 10;
    private int capacity;
    private int capacityIncrement = 0;
    private Object[] elementData;
    private int size = 0;

    public SequenceStack() {
        capacity = DEFAULT_SIZE;
        elementData = new Object[capacity];
    }

    public SequenceStack(T element) {
        this();
        elementData[0] = element;
        size ++;
    }

    public SequenceStack(T element, int initSize) {
        this.capacity = initSize;
        elementData = new Object[capacity];
        elementData[0] = element;
        size ++;
    }

    public SequenceStack(T element, int initSize, int capacityIncrement) {
        this.capacity = initSize;
        this.capacityIncrement = capacityIncrement;
        elementData = new Object[capacity];
        elementData[0] = element;
        size ++;
    }

    public int length() {
        return size;
    }

    public void push(T element) {
        ensureCapacity(size + 1);
        elementData[size++] = element;
    }

    public T pop() {
        T oldVal = (T)elementData[size-1];
        elementData[--size] = null;
        return oldVal;
    }

    public T peek() {
        return (T)elementData[size-1];
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
            if(capacityIncrement > 0) {
                while(capacity < minCapacity) {
                    capacity += capacityIncrement;
                }
            }else {
                while(capacity < minCapacity) {
                    capacity <<= 1;
                }
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
            for(int i = size - 1; i >= 0; i --) {
                sb.append(elementData[i].toString()).append(",");
            }
            int len = sb.length();
            return sb.delete(len - 1, len).append("]").toString();
        }
    }

    public static void main(String[] args) {
        SequenceStack<String> stack = new SequenceStack<>();
        stack.push("aaa");
        stack.push("bbb");
        stack.push("ccc");
        stack.push("ddd");
        System.out.println(stack);
        System.out.println(stack.peek());
        System.out.println(stack);
        System.out.println(stack.pop());
        System.out.println(stack);
    }
}
